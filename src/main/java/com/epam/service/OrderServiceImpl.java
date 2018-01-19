package com.epam.service;

import com.epam.dao.IOrderDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.dto.OrderDto;
import com.epam.entity.Order;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
import com.epam.service.utils.Constants;
import com.epam.service.utils.Validator;
import com.epam.service.utils.exception.ValidatorException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static Logger logger = Logger.getLogger(OrderServiceImpl.class);
    private DaoFactory daoFactory = DaoFactory.getInstance();


    @Override
    public int addToCart(String idUser, String idMedicament,
                            String number, String idDosage) throws ServiceException, ServiceLogicException {
        logger.debug("OrderServiceImpl.addToCart");
        IOrderDao orderDao = daoFactory.getIOrderDao();
        Order order = new Order();
        try {
            Validator.isNull(idUser, idMedicament, number, idDosage);
            Validator.isEmptyString(idUser, idMedicament, number, idDosage);
            Validator.matchNumber(number, idDosage);

            order.setIdUser(Integer.parseInt(idUser));
            order.setIdMedicament(Integer.parseInt(idMedicament));
            order.setIdOrderStatus(Constants.STATUS_IN_CART);
            order.setNumber(Integer.parseInt(number));
            order.setIdDosage(Integer.parseInt(idDosage));
            int id = orderDao.addOrder(order);
            if(id>0)
                return id;
        } catch (ValidatorException | DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("number format exception", e);
        }
        return 0;
    }

    @Override
    public boolean changeOrderStatus(String idOrder, int idStatus) throws ServiceException {
        logger.debug("OrderServiceImpl.changeOrderStatus()");
        IOrderDao orderDao = daoFactory.getIOrderDao();
        try {
            Validator.isNull(idOrder);
            Validator.isEmptyString(idOrder);
            int orderId = Integer.parseInt(idOrder);
            Order order = orderDao.getOrderById(orderId);
            if(order.getIdOrderStatus() < idStatus){
                orderDao.changeOrderStatus(orderId, idStatus);
            }else{
                return false;
            }
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("OrderServiceImpl.getOrdersDtoByUserId() - success.");
        return true;
    }

    @Override
    public List<OrderDto> getOrdersDtoByUserIdAndStatus(String idUser, int status) throws ServiceException {
        logger.debug("OrderServiceImpl.getOrdersDtoByUserIdAndStatus()");
        IOrderDao orderDao = daoFactory.getIOrderDao();
        List<OrderDto> orders;
        try {
            Validator.isNull(idUser);
            Validator.isEmptyString(idUser);
            int userId = Integer.parseInt(idUser);
            orders = orderDao.getOrdersDtoByUserIdAndStatus(userId, status);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e){
            throw new ServiceException("number format exception"+e);
        }
        logger.debug("OrderServiceImpl.getOrdersDtoByUserIdAndStatus() - success.");
        return orders;
    }

}
