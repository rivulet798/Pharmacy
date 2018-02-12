package com.epam.service.impl;

import com.epam.dao.MedicamentDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.exception.ServiceException;
import com.epam.service.utils.Validator;
import com.epam.exception.ValidatorException;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
 import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MedicamentServiceImpl implements MedicamentService {

    private static Logger logger = Logger.getLogger(MedicamentServiceImpl.class);
    private static final int RECIPE_NEED = 1;
    private static final int RECIPE_NOT_NEED = 0;
    private DaoFactory daoFactory = DaoFactory.getInstance();


    @Override
    public List<Medicament> getAllMedicaments() throws ServiceException {
        List<Medicament> medicaments;
        try {
            logger.debug("MedicamentServiceImpl.getAllMedicaments()");
            MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
            medicaments = medicamentDao.getAllMedicaments();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getAllMedicaments() - success");
        return medicaments;
    }

    @Override
    public List<Medicament> getMedicamentsWithPrescription() throws ServiceException {
        List<Medicament> medicaments;
        try {
            logger.debug("MedicamentServiceImpl.getAllMedicamentsWithPrescription()");
            MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
            medicaments = medicamentDao.getMedicamentsByPrescription(RECIPE_NEED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getAllMedicamentsWithPrescription() - success");
        return medicaments;
    }


    @Override
    public List<Medicament> getAscSortedByPriceMedicaments() throws ServiceException {
        logger.debug("MedicamentServiceImpl.getAscSortedByPriceMedicaments()");
        List<Medicament> medicaments;
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            medicaments = medicamentDao.getAscSortedByPriceMedicaments();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getAscSortedByPriceMedicaments() - success.");
        return medicaments;
    }

    @Override
    public List<Medicament> getDescSortedByPriceMedicaments() throws ServiceException {
        logger.debug("MedicamentServiceImpl.getDescSortedByPriceMedicaments()");
        List<Medicament> medicaments;
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            medicaments = medicamentDao.getDescSortedByPriceMedicaments();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getDescSortedByPriceMedicaments() - success.");
        return medicaments;
    }

    @Override
    public Medicament getMedicamentById(String id) throws ServiceException{
        logger.debug("MedicamentServiceImpl.getMedicamentById()");
        Medicament medicament;
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            Validator.isNull(id);
            Validator.isEmptyString(id);
            int idMedicament = Integer.parseInt(id);
            medicament = medicamentDao.getMedicamentById(idMedicament);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("MedicamentServiceImpl.getMedicamentById() - success.");
        return medicament;
    }

    @Override
    public List<Medicament> getMedicamentsByProducer(String producer) throws ServiceException{
        logger.debug("MedicamentServiceImpl.getMedicamentsByProducer()");
        List<Medicament> medicaments;
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            Validator.isNull(producer);
            Validator.isEmptyString(producer);
            medicaments = medicamentDao.getMedicamentsByProducer(producer);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getMedicamentsByProducer() - success.");
        return medicaments;
    }

    @Override
    public int addMedicament(String name, String producer,
                              String price, String prescription,
                              Part part, String webInfPath, String availability,
                              String modeOfApplication, String contraindications,
                              String sideEffects) throws ServiceException {

        logger.debug("MedicamentServiceImpl.addMedicament");
        Medicament medicament = new Medicament();
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        int idMedicament;
        try {
            Validator.isNull(name, producer, price, prescription, webInfPath, availability, modeOfApplication, contraindications, sideEffects);
            Validator.isEmptyString(name, producer, price, prescription, webInfPath, availability, modeOfApplication, contraindications, sideEffects);
            Validator.matchProperName(name, producer);
            medicament.setName(name);
            medicament.setProducer(producer);
            BigDecimal priceDecimal = new BigDecimal(price);
            medicament.setPrice(priceDecimal);
            medicament.setPrescription(Boolean.parseBoolean(prescription));
            medicament.setAvailability(Boolean.parseBoolean(availability));
            medicament.setModeOfApplication(modeOfApplication);
            medicament.setContraindications(contraindications);
            medicament.setSideEffects(sideEffects);
            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!imageName.isEmpty()) {
                medicament.setImage(imageName);
            }
            idMedicament = medicamentDao.addMedicament(medicament);
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, webInfPath);
            }
        } catch (ValidatorException | DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("number format exception", e);
        }
        logger.debug("MedicamentServiceImpl.addMedicament - success");
        return idMedicament;
    }

    @Override
    public List<Medicament> getMedicamentByName(String name) throws ServiceException {
        logger.debug("MedicamentServiceImpl.getMedicamentByName()");
        List<Medicament> medicaments;
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            Validator.isNull(name);
            Validator.matchLength(name);
            medicaments = medicamentDao.getMedicamentsByName(name);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        }
        logger.debug("MedicamentServiceImpl.getMedicamentByName() - success.");
        return medicaments;
    }

    @Override
    public void editMedicament(String idMedicament, String name, String producer,
                               String price, String prescription, Part part,
                               String webInfPath, String availability,
                               String modeOfApplication, String contraindications,
                               String sideEffects) throws ServiceException {
        logger.debug("MedicamentServiceImpl.editMedicament");
        Medicament medicament = new Medicament();
        MedicamentDao medicamentDao = daoFactory.getIMedicamentDao();
        try {
            Validator.isNull(idMedicament, name, producer, price, prescription, webInfPath, availability, modeOfApplication, contraindications, sideEffects);
            Validator.isEmptyString(idMedicament, name, producer, price, prescription, webInfPath, availability, modeOfApplication, contraindications, sideEffects);
            Validator.matchProperName(name, producer);
            medicament.setId(Integer.parseInt(idMedicament));
            medicament.setName(name);
            medicament.setProducer(producer);
            Float priceF = Float.parseFloat(price);
            BigDecimal priceDecimal = BigDecimal.valueOf(priceF);
            medicament.setPrice(priceDecimal);
            medicament.setPrescription(Boolean.parseBoolean(prescription));
            medicament.setAvailability(Boolean.parseBoolean(availability));
            medicament.setModeOfApplication(modeOfApplication);
            medicament.setContraindications(contraindications);
            medicament.setSideEffects(sideEffects);
            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!imageName.isEmpty()) {
                medicament.setImage(imageName);
            }
            medicamentDao.editMedicament(medicament);
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            if (!fileName.isEmpty()) {
                uploadImage(part, fileName, webInfPath);
            }
        } catch (ValidatorException | DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("number format exception", e);
        }
        logger.debug("MedicamentServiceImpl.editMedicament - success");
    }

    private void uploadImage(Part filePart, String fileName, String webInfPath) throws ServiceException {
        try {

            File dir = new File(webInfPath + "images" + File.separator + "medicaments");
            if (!dir.exists()) {
                Path path = Paths.get(webInfPath);
                Files.createDirectories(path);
            }
            File file = new File(dir, fileName);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("error with upload of image", e);
        }
    }

}
