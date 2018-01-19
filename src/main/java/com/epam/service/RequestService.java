package com.epam.service;

import com.epam.dto.RequestForRenewalDto;
import com.epam.entity.RequestForRenewal;
import com.epam.service.exception.ServiceException;

import java.util.List;

public interface RequestService {
    List<RequestForRenewal> getRequestsByDoctorId(String idUser) throws ServiceException;

    void addRequest(String idPrescription, int newRequest) throws ServiceException;

    List<RequestForRenewalDto> getRequestsDtoByDoctorId(String idDoctor) throws ServiceException;
}
