package com.epam.service;

import com.epam.dao.IDosageDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.entity.Dosage;
import com.epam.service.exception.ServiceException;
import com.epam.service.utils.Validator;
import com.epam.service.utils.exception.ValidatorException;
import org.apache.log4j.Logger;

import java.util.List;

public class DosageServiceImpl implements DosageService {

    private static Logger logger = Logger.getLogger(DosageServiceImpl.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public List<Dosage> getDosagesByMedicamentId(String idMedicament) throws ServiceException {
        logger.debug("DosageServiceImpl.getDosagesByMedicamentId()");
        IDosageDao dosageDao = daoFactory.getIDosageDao();
        List<Dosage> dosages = null;
        try {
            Validator.isNull(idMedicament);
            Validator.isEmptyString(idMedicament);
            int medicamentId = Integer.parseInt(idMedicament);
            dosages = dosageDao.getDosagesByMedicamentId(medicamentId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("DosageServiceImpl.getDosagesByMedicamentId() - success.");
        return dosages;
    }
}
