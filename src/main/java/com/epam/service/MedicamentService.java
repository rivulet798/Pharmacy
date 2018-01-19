package com.epam.service;

import com.epam.entity.Medicament;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;

import javax.servlet.http.Part;
import java.util.List;

public interface MedicamentService {
    List<Medicament> getAllMedicaments()throws ServiceException;
    List<Medicament> getMedicamentsWithPrescription() throws ServiceException;
    List<Medicament> getAscSortedByPriceMedicaments() throws ServiceException;
    List<Medicament> getDescSortedByPriceMedicaments() throws ServiceException;
    Medicament getMedicamentById(String id) throws ServiceException;
    List<Medicament> getMedicamentsByProducer(String producer) throws ServiceException;
    void addMedicament(String name, String producer, String price,
                       String prescroption, Part part,
                       String image, String availability) throws ServiceException,ServiceLogicException;
    List<Medicament> getMedicamentByName(String name) throws ServiceException;
    void editMedicament(String idMedicament, String name, String producer, String price,
                       String prescroption, Part part,
                       String image, String availability) throws ServiceException,ServiceLogicException;

}
