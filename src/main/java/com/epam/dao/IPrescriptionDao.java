package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Prescription;

import java.util.List;

public interface IPrescriptionDao {
    boolean addPrescription(Prescription prescription) throws DaoException;
    boolean createPrescription();
    Prescription getPrescriptionById(int id) throws DaoException;
    List<Prescription> getPrescriptionsByUserId(int userId) throws DaoException;
    boolean changeStatus(Prescription prescription);
    List<PrescriptionDto> getPrescriptionsDtoByUserId(int userId) throws DaoException;
    List<PrescriptionDto> getPrescriptionsDtoByUserIdAndMedId(int userId, int idMedicament) throws  DaoException;
    boolean setPrescriptionInvalid(int idPrescription)throws DaoException;
}
