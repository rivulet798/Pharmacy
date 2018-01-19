package com.epam.dao;

import com.epam.dao.exception.DaoException;
import com.epam.entity.User;

import java.util.List;

public interface IUserDao {
    boolean addUser() throws DaoException;
    boolean deleteUser();
    boolean createUser();
    boolean signUp(User user) throws DaoException;
    User signIn(String login, String password) throws DaoException;
    User getUserById(int id) throws DaoException;
    List<User> getAllUsersByIdRole(int idRole) throws DaoException;


}
