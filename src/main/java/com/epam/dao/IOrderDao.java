package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.dto.OrderDto;
import com.epam.entity.Order;

import java.util.List;

public interface IOrderDao {
    Order getOrderById(int id) throws DaoException;
    int addOrder(Order order) throws DaoException;
    boolean changeOrderStatus(int idOrder, int idStatus) throws DaoException;
    List<Order> getOrdersByUserId(int userId) throws DaoException;
    List<OrderDto> getOrdersDtoByUserIdAndStatus(int userId, int status) throws DaoException;
}
