package com.epam.service;

import com.epam.entity.User;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;

import java.util.List;

public interface UserService {

    void updateLocale(String userLogin, String locale) throws ServiceException;

    User getUserByLogin(String userLogin) throws ServiceException;

    boolean signUp(String idRole, String login, String password,
                   String name, String surname, String address,
                   String email) throws ServiceException, ServiceLogicException;

    User signIn(String userLogin, String userPassword) throws ServiceException;

    List<User> getAllUsersByRoleId(String roleId) throws ServiceException, ServiceLogicException;


}
