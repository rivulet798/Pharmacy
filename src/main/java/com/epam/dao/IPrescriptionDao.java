package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Prescription;

import java.util.List;

public interface IPrescriptionDao {
    boolean addPrescription(Prescription prescription) throws DaoException;
    Prescription getPrescriptionById(int id) throws DaoException;
    List<Prescription> getPrescriptionsByUserId(int userId) throws DaoException;
    List<PrescriptionDto> getPrescriptionsDtoByUserId(int userId) throws DaoException;
    List<PrescriptionDto> getPrescriptionsDtoByUserIdAndMedId(int userId, int idMedicament) throws  DaoException;
    boolean setPrescriptionInvalidByOrderId(int idOrder)throws DaoException;
    boolean isExpiredPrescription(int prescriptionId) throws DaoException;
    int getDoctorIdByRequestId(int requestId) throws DaoException;
    boolean extendPrescriptionFromRequest(int requestId) throws DaoException;
}
