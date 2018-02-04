package com.epam.dao;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.dto.OrderDto;
import com.epam.entity.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IOrderDao {
    private static Logger logger = Logger.getLogger(OrderDao.class);

    private static final String GET_ORDER_BY_ID = "SELECT * FROM pharmacy.order WHERE idOrder=?;";
    private static final String ADD_ORDER_WITH_PRESCRIPTION = "INSERT INTO pharmacy.order (idUser,idMedicament,idOrderStatus,number,idDosage,idPrescription) VALUES(?,?,?,?,?,?);";
    private static final String ADD_ORDER_WITHOUT_PRESCRIPTION = "INSERT INTO pharmacy.order (idUser,idMedicament,idOrderStatus,number,idDosage) VALUES(?,?,?,?,?);";
    private static final String CHANGE_ORDER_STATUS = "UPDATE pharmacy.order SET idOrderStatus=? WHERE idOrder=?;";
    private static final String GET_ORDERS_BY_USER_ID = "SELECT * FROM pharmacy.order WHERE idUser=?;";
    private static final String GET_ORDERS_DTO_BY_USER_ID_AND_STATUS = "SELECT idOrder, m.name AS medicamentName, m.producer, m.price*number AS price, idOrderStatus, number,idDosage,idPrescription FROM pharmacy.order JOIN medicament m ON pharmacy.order.idMedicament = m.idMedicament WHERE idUser=? AND idOrderStatus=?;";

    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Order order;
    private List<Order> orders;
    private OrderDto orderDto;
    private List<OrderDto> orderDtoList;

    @Override
    public int addOrderWithPrescription(Order order) throws DaoException {
        logger.debug("OrderDao.addOrderWithPrescription()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            String generatedColumns[] = { "idOrder" };
            statement = connection.prepareStatement(ADD_ORDER_WITH_PRESCRIPTION,  generatedColumns);
            statement.setInt(1, order.getIdUser());
            statement.setInt(2, order.getIdMedicament());
            statement.setInt(3, order.getIdOrderStatus());
            statement.setInt(4, order.getNumber());
            statement.setInt(5, order.getIdDosage());
            statement.setInt(6, order.getIdPrescription());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
               int idOrder =(int)resultSet.getLong(1);
               return idOrder;
            }
            return 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addOrder)", e);
        } catch (ConnectionException e) {
            throw new DaoException("Error with connection with database" + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public int addOrderWithoutPrescription(Order order) throws DaoException {
        logger.debug("OrderDao.addOrderWithPrescription()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            String generatedColumns[] = { "idOrder" };
            statement = connection.prepareStatement(ADD_ORDER_WITHOUT_PRESCRIPTION,  generatedColumns);
            statement.setInt(1, order.getIdUser());
            statement.setInt(2, order.getIdMedicament());
            statement.setInt(3, order.getIdOrderStatus());
            statement.setInt(4, order.getNumber());
            statement.setInt(5, order.getIdDosage());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                int idOrder =(int)resultSet.getLong(1);
                return idOrder;
            }
            return 0;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addOrder)", e);
        } catch (ConnectionException e) {
            throw new DaoException("Error with connection with database" + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public Order getOrderById(int id) throws DaoException {
        logger.debug("OrderDao.getOrderById()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = null;
            statement = connection.prepareStatement(GET_ORDER_BY_ID);
            statement.setInt(1,id);

            resultSet = null;
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                order = load(resultSet);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getMedicamentById)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("OrderDao.getOrderById() - success"+order);
        return order;
    }


    @Override
    public List<Order> getOrdersByUserId(int userId) throws DaoException {
        logger.debug("OrderDao.getOrdersByUserId()");
        try {
            orders = new ArrayList<>();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_ORDERS_BY_USER_ID);
            statement.setInt(1, userId);
            resultSet = null;
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = load(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getOrdersByUserId)", e);
        } catch (ConnectionException e) {
            throw new DaoException("Error with connection with database" + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("OrderDao.getOrdersByUserId() - success ");
        return orders;
    }

    @Override
    public List<OrderDto> getOrdersDtoByUserIdAndStatus(int userId, int status) throws DaoException {
        logger.debug("OrderDao.getOrdersDtoByUserIdAndStatus()");
        try {
            statement = null;
            resultSet = null;
            orderDtoList = new ArrayList<>();

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(GET_ORDERS_DTO_BY_USER_ID_AND_STATUS);
            statement.setInt(1, userId);
            statement.setInt(2,status);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderDto = loadDto(resultSet);
                orderDtoList.add(orderDto);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getOrdersDtoByUserIdAndStatus)", e);
        } catch (ConnectionException e) {
            throw new DaoException("Error with connection with database" + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("OrderDao.getOrdersDtoByUserIdAndStatus() - success ");
        return orderDtoList;
    }

    @Override
    public boolean changeOrderStatus(int idOrder, int idStatus) throws DaoException {
        logger.debug("OrderDao.changeOrderStatus()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(CHANGE_ORDER_STATUS);
            statement.setInt(1, idStatus);
            statement.setInt(2, idOrder);
            if (statement.executeUpdate() != 0) {
                logger.debug("OrderDao.changeOrderStatus()-success");
                return true;
            } else {
                logger.debug("OrderDao.changeOrderStatus()-failed");
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(changeOrderStatus)", e);
        } catch (ConnectionException e) {
            throw new DaoException("Error with connection with database" + e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    private Order load(ResultSet resultSet) throws DaoException{
        Order order = new Order();
        try {
            order.setId(resultSet.getInt("idOrder"));
            order.setIdUser(resultSet.getInt("idUser"));
            order.setIdMedicament(resultSet.getInt("idMedicament"));
            order.setIdOrderStatus(resultSet.getInt("idOrderStatus"));
            order.setNumber(resultSet.getInt("number"));
            order.setIdDosage(resultSet.getInt("idDosage"));
            order.setIdPrescription(resultSet.getInt("idPrescription"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return order;
    }

    private OrderDto loadDto(ResultSet resultSet) throws DaoException{
        OrderDto orderDto = new OrderDto();
        try {
            orderDto.setIdOrder(resultSet.getInt("idOrder"));
            orderDto.setMedicamentName(resultSet.getString("medicamentName"));
            orderDto.setProducer(resultSet.getString("producer"));
            orderDto.setPrice(resultSet.getFloat("price"));
            orderDto.setOrderStatus(resultSet.getInt("idOrderStatus"));
            orderDto.setNumber(resultSet.getInt("number"));
            orderDto.setDosage(resultSet.getInt("idDosage"));
            orderDto.setIdPrescription(resultSet.getInt("idPrescription"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return orderDto;
    }
}
