package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.entity.Dosage;

import java.util.List;

public interface IDosageDao {
    List<Dosage> getDosagesByMedicamentId(int idMedicament) throws DaoException;
}
