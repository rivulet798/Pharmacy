package com.epam.service;

import com.epam.dto.RequestForRenewalDto;
import com.epam.entity.RequestForRenewal;
import com.epam.service.exception.ServiceException;

import java.util.List;

public interface RequestService {
    List<RequestForRenewal> getRequestsByDoctorId(String idUser) throws ServiceException;
    void addRequest(String idPrescription, String idUser, int newRequest) throws ServiceException;
    List<RequestForRenewalDto> getRequestsDtoByDoctorId(String idDoctor) throws ServiceException;
    boolean checkExistOfRequest(String idPrescription) throws ServiceException;
    void changeRequestStatus(String idRequest, int status) throws ServiceException;
}
