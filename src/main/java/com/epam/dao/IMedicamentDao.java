package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.entity.Medicament;

import java.util.List;

public interface IMedicamentDao {
    int addMedicament(Medicament medicament) throws DaoException;
    boolean deleteMedicament();

    Medicament getMedicamentById(int id) throws DaoException;
    List<Medicament> getAllMedicaments() throws DaoException;
    List<Medicament> getAscSortedByPriceMedicaments() throws DaoException;
    List<Medicament> getDescSortedByPriceMedicaments() throws DaoException;
    List<Medicament> getMedicamentsByProducer(String producer) throws DaoException;
    List<Medicament> getMedicamentsByName(String name) throws DaoException;
    boolean editMedicament(Medicament medicament) throws DaoException;

    List<Medicament> getMedicamentsByPrescription(int prescriptionStatus) throws DaoException;
}
