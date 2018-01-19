//package com.epam.dao;
//
//import com.epam.dao.exception.DaoException;
//import com.epam.dao.pool.ConnectionPool;
//import com.epam.entity.Id;
//
//import java.io.Serializable;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//public abstract class AbstractDao <T extends Id<PK>,PK extends Serializable> implements Dao<T,PK>{
//
//    @Override
//    public T getById(PK id) throws DaoException {
//        List<T> list;
//        String sql = getSelectQuery();
//        sql += " WHERE id = ?";
//        ConnectionPool connectionPool = null;
//        PreparedStatement preparedStatement = null;
//        Connection connection = null;
//        try{
//            connectionPool = ConnectionPool.getInstance();
//            connection = connectionPool.retrieve();
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, (Integer) key);
//            ResultSet rs = preparedStatement.executeQuery();
//            list = parseResultSet(rs);
//            if (list == null || list.size() == 0) {
//                return null;
//            }
//            if (list.size() > 1) {
//                throw new DaoException("Received more than one record.");
//            }
//            return list.iterator().next();
//        } catch (SQLException e) {
//            throw new DaoException("Error occurred when tried get record by Primary Key "+e);
//        } finally {
//            if (connectionPool != null) {
//                connectionPool.putBackConnection(connection, preparedStatement);
//            }
//        }
//
//    }
//
//
//}
