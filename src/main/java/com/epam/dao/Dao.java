//package com.epam.dao;
//
//import com.epam.dao.exception.DaoException;
//
//import java.io.Serializable;
//import java.sql.SQLException;
//import java.util.List;
//
//public interface Dao <T,PK extends Serializable>{
//
//        T getById(PK id) throws SQLException, DaoException;
//        List<T> getAll() throws SQLException, DaoException;
//        void add(T object) throws SQLException, DaoException;
//        void update(T object) throws SQLException, DaoException;
//        void delete(Integer id) throws SQLException, DaoException;
//
//}
//
