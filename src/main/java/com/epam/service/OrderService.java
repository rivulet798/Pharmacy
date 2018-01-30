package com.epam.service;

import com.epam.dto.OrderDto;
import com.epam.entity.Order;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;

import java.util.List;

public interface OrderService {
    int addToCartMedWithPrescription(String idUser, String idMedicament,
                   String number, String dosage, String idPrescription) throws ServiceException,ServiceLogicException;

    int addToCartMedWithoutPrescription(String idUser, String idMedicament,
                                     String number, String dosage) throws ServiceException,ServiceLogicException;

    boolean changeOrderStatus(String idOrder, int idStatus) throws ServiceException;

    List<OrderDto> getOrdersDtoByUserIdAndStatus(String idUser, int statusInCart) throws ServiceException;
}
