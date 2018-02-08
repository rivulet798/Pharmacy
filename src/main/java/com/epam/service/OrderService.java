package com.epam.service;

import com.epam.dto.OrderDto;
import com.epam.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
    int addToCartMedWithPrescription(String idUser, String idMedicament,
                   String number, String dosage, String idPrescription) throws ServiceException;

    int addToCartMedWithoutPrescription(String idUser, String idMedicament,
                                     String number, String dosage) throws ServiceException;

    boolean changeOrderStatus(String idOrder, String idUser, int idStatus) throws ServiceException;

    List<OrderDto> getOrdersDtoByUserIdAndStatus(String idUser, int statusInCart) throws ServiceException;
}
