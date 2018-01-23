package com.epam.service;

import com.epam.entity.Dosage;
import com.epam.service.exception.ServiceException;

import java.util.List;

public interface DosageService {
    List<Dosage> getDosagesByMedicamentId(String idMedicament) throws ServiceException;
    void addDosage(int idMedicament, String... dosages) throws ServiceException;

}
