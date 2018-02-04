package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.dto.RequestForRenewalDto;
import com.epam.entity.RequestForRenewal;

import java.util.List;

public interface IRequestDao {
    List<RequestForRenewal> getRequestsByDoctorId(int userId) throws DaoException;

    boolean addRequest(int prescriptionId, int newRequest) throws DaoException;
    List<RequestForRenewalDto> getRequestsDtoByDoctorId(int idDoctor) throws DaoException;
    boolean checkExistOfRequest(int prescriptionId) throws DaoException;
    boolean changeRequestStatus(int requestId, int status) throws DaoException;

}
