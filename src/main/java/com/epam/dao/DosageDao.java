package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.entity.Dosage;

import java.util.List;

public interface DosageDao {
    List<Dosage> getDosagesByMedicamentId(int idMedicament) throws DaoException;
    boolean addDosage(int idMedicament, int dosage) throws DaoException;
}
