package com.epam.service.impl;

import com.epam.dao.RequestDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.dto.RequestForRenewalDto;
import com.epam.entity.RequestForRenewal;
import com.epam.service.RequestService;
import com.epam.service.exception.ServiceException;
import com.epam.service.utils.Validator;
import com.epam.exception.ValidatorException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RequestServiceImpl implements RequestService {

    private static Logger logger = Logger.getLogger(RequestServiceImpl.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public List<RequestForRenewal> getRequestsByDoctorId(String idUser) throws ServiceException {
        logger.debug("RequestServiceImpl.getRequestsByDoctorId()");
        RequestDao requestDao = daoFactory.getIRequestDao();
        List<RequestForRenewal> requestsForRenewal;
        try {
            Validator.isNull(idUser);
            Validator.isEmptyString(idUser);
            int userId = Integer.parseInt(idUser);
            requestsForRenewal = requestDao.getRequestsByDoctorId(userId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("RequestServiceImpl.getRequestsByDoctorId() - success.");
        return requestsForRenewal;
    }

    @Override
    public void addRequest(String idPrescription, int newRequest) throws ServiceException{
        logger.debug("RequestServiceImpl.addRequest()");
        RequestDao requestDao = daoFactory.getIRequestDao();
        try {
            int prescriptionId = Integer.parseInt(idPrescription);
            requestDao.addRequest(prescriptionId, newRequest);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("RequestServiceImpl.addRequest() - success.");
    }

    @Override
    public List<RequestForRenewalDto> getRequestsDtoByDoctorId(String idDoctor) throws ServiceException {
        logger.debug("RequestServiceImpl.getRequestsDtoByDoctorId()");
        RequestDao requestDao = daoFactory.getIRequestDao();
        List<RequestForRenewalDto> requestForRenewalList;
        try {
            Validator.isNull(idDoctor);
            Validator.isEmptyString(idDoctor);
            int doctorId = Integer.parseInt(idDoctor);
            requestForRenewalList = requestDao.getRequestsDtoByDoctorId(doctorId);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("RequestServiceImpl.getRequestsDtoByDoctorId() - success.");
        return requestForRenewalList;
    }

    @Override
    public boolean checkExistOfRequest(String idPrescription) throws ServiceException {
        logger.debug("RequestServiceImpl.checkExistOfRequest()");
        RequestDao requestDao = daoFactory.getIRequestDao();
        try {
            int prescriptionId = Integer.parseInt(idPrescription);
            return requestDao.checkExistOfRequest(prescriptionId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
    }

    @Override
    public void changeRequestStatus(String idRequest, int status) throws ServiceException {
        logger.debug("RequestServiceImpl.changeRequestStatus()");
        RequestDao requestDao = daoFactory.getIRequestDao();
        try {
            int requestId = Integer.parseInt(idRequest);
            requestDao.changeRequestStatus(requestId, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
    }

}
