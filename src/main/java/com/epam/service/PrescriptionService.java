package com.epam.service;

import com.epam.dto.PrescriptionDto;
import com.epam.entity.Prescription;
import com.epam.service.exception.ServiceException;

import java.util.List;

public interface PrescriptionService {

    Prescription getPrescriptionById(String id) throws ServiceException;
    List<Prescription> getPrescriptionsByUserId(String idUser) throws ServiceException;
    void addPrescription(String idDoctor, String idUser,
                            String idMedicament, String dateOfCompletion, int dosage, int number) throws ServiceException;
    List<PrescriptionDto> getPrescriptionsDtoByUserId(String idUser) throws ServiceException;
    List<PrescriptionDto> getPrescriptionsByUserIdAndMedId(String idUser, String idMedicament) throws  ServiceException;
    boolean setPrescriptionInvalidByOrderId(String idOrder) throws ServiceException;
    boolean isExpiredPrescription(String idPrescription) throws ServiceException;
    void extendPrescription(String idRequest, String idDoctor) throws ServiceException;
    boolean isPrescriptionValid(String idOrder) throws ServiceException;
}
