package com.epam.service.impl;

import com.epam.dao.OrderDao;
import com.epam.dao.PrescriptionDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Order;
import com.epam.entity.Prescription;
import com.epam.service.PrescriptionService;
import com.epam.service.exception.ServiceException;
import com.epam.service.utils.Validator;
import com.epam.exception.ValidatorException;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrescriptionServiceImpl implements PrescriptionService {

    private static Logger logger = Logger.getLogger(PrescriptionServiceImpl.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public Prescription getPrescriptionById(String id) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.getPrescriptionById()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        Prescription prescription;
        try {
            Validator.isNull(id);
            Validator.isEmptyString(id);
            int idPrescription = Integer.parseInt(id);
            prescription = prescriptionDao.getPrescriptionById(idPrescription);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        return prescription;
    }

    @Override
    public List<Prescription> getPrescriptionsByUserId(String idUser) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.getPrescriptionsByUserId()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        List<Prescription> prescriptions;
        try {
            Validator.isNull(idUser);
            Validator.isEmptyString(idUser);
            int userId = Integer.parseInt(idUser);
            prescriptions = prescriptionDao.getPrescriptionsByUserId(userId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("PrescriptionServiceImpl.getPrescriptionsByUserId() - success.");
        return prescriptions;
    }

    @Override
    public void addPrescription(String idDoctor, String idUser,
                                   String idMedicament, String dateOfCompletion, int idDosage, int number) throws ServiceException{
        logger.debug("PrescriptionServiceImpl.addPrescription");
        Prescription prescription = new Prescription();
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        try {
            Validator.isNull(idDoctor, idUser, idMedicament, dateOfCompletion);
            Validator.isEmptyString(idDoctor, idUser, idMedicament, dateOfCompletion);
            Validator.matchDate(dateOfCompletion);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateOfIssue = new java.util.Date();
            java.util.Date dateOfCompl = dateFormat.parse(dateOfCompletion);

            if (dateOfCompl.after(dateOfIssue)) {

                prescription.setDoctorId(Integer.parseInt(idDoctor));
                prescription.setUserId(Integer.parseInt(idUser));
                prescription.setIdMedicament(Integer.parseInt(idMedicament));
                prescription.setDateOfIssue(Date.valueOf(dateFormat.format(dateOfIssue)));
                prescription.setDateOfCompletion(Date.valueOf(dateFormat.format(dateOfCompl)));
                prescription.setIdDosage(idDosage);
                prescription.setNumber(number);
                prescriptionDao.addPrescription(prescription);
            }
            else{
                throw new ServiceException("Wrong date of completion");
            }
        } catch (ValidatorException | DaoException e) {
            logger.debug(e.getMessage());
            throw new ServiceException(e);
        } catch (NumberFormatException | ParseException e) {
            logger.debug(e.getMessage());
            throw new ServiceException("number format exception", e);
        }
        logger.debug("MedicamentServiceImpl.addMedicament - success");
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsDtoByUserId(String idUser) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.getPrescriptionsDtoByUserId()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        List<PrescriptionDto> prescriptionsDto;
        try {
            Validator.isNull(idUser);
            Validator.isEmptyString(idUser);
            int userId = Integer.parseInt(idUser);
            prescriptionsDto = prescriptionDao.getPrescriptionsDtoByUserId(userId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("PrescriptionServiceImpl.getPrescriptionsDtoByUserId() - success.");
        return prescriptionsDto;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsByUserIdAndMedId(String idUser, String idMedicament) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.getPrescriptionsDtoByUserIdAndMedId()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        List<PrescriptionDto> prescriptionsDto;
        try {
            Validator.isNull(idUser, idMedicament);
            Validator.isEmptyString(idUser, idMedicament);
            int userId = Integer.parseInt(idUser);
            int medicamentId = Integer.parseInt(idMedicament);
            prescriptionsDto = prescriptionDao.getPrescriptionsDtoByUserIdAndMedId(userId, medicamentId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("PrescriptionServiceImpl.getPrescriptionsDtoByUserId() - success.");
        return prescriptionsDto;
    }

    @Override
    public boolean setPrescriptionInvalidByOrderId(String idOrder) throws ServiceException{
        logger.debug("PrescriptionServiceImpl.setPrescriptionInvalidByOrderId()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        try {
            Validator.isNull(idOrder);
            Validator.isEmptyString(idOrder);
            int orderId = Integer.parseInt(idOrder);
            return prescriptionDao.setPrescriptionInvalidByOrderId(orderId);
        } catch (DaoException | ValidatorException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isExpiredPrescription(String idPrescription) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.isExpiredPrescription()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        try {
            int prescriptionId = Integer.parseInt(idPrescription);
            return prescriptionDao.isExpiredPrescription(prescriptionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
    }

    @Override
    public void extendPrescription(String idRequest, String idDoctor) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.extendPrescription()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        try {
            int requestId = Integer.parseInt(idRequest);
            int doctorId = Integer.parseInt(idDoctor);
            if(prescriptionDao.getDoctorIdByRequestId(requestId) == doctorId) {
                prescriptionDao.extendPrescriptionFromRequest(requestId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
    }

    @Override
    public boolean isPrescriptionValid(String idOrder) throws ServiceException {
        logger.debug("PrescriptionServiceImpl.isPrescriptionValid()");
        PrescriptionDao prescriptionDao = daoFactory.getIPrescriptionDao();
        OrderDao orderDao = daoFactory.getIOrderDao();
        try {
            int orderId = Integer.parseInt(idOrder);
            Order order = orderDao.getOrderById(orderId);
            int idPrescription = order.getIdPrescription();
            if(idPrescription != 0) {
                Prescription prescription = prescriptionDao.getPrescriptionById(idPrescription);
                return prescription.isValid();
            } else{
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
    }
}