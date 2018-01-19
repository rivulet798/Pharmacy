package com.epam.service;

import com.epam.dao.IUserDao;
import com.epam.dao.exception.DaoException;
import com.epam.dao.factory.DaoFactory;
import com.epam.entity.User;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
import com.epam.service.utils.Hasher;
import com.epam.service.utils.Validator;
import com.epam.service.utils.exception.ValidatorException;
import org.apache.log4j.Logger;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public void updateLocale(String userLogin, String locale) throws ServiceException {

    }

    @Override
    public User getUserByLogin(String userLogin) throws ServiceException {
        return null;
    }

    @Override
    public boolean signUp(String idRole, String login, String password, String name, String surname, String address, String email) throws ServiceException, ServiceLogicException {
        logger.debug("UserServiceImpl.signUp()");
        IUserDao iUserDao = daoFactory.getIUserDao();

        User user = new User();
        IUserDao userDao = daoFactory.getIUserDao();
        try {
            Validator.isNull(name, idRole, login, password, name, surname, address, email);
            Validator.isEmptyString(name, idRole, login, password, name, surname, address, email);
            Validator.matchProperName(name, surname);
            Validator.matchLogin(login);
            Validator.matchPassword(password);
            Validator.matchEmail(email);
            int role = Integer.parseInt(idRole);
            user.setIdRole(role);
            user.setLogin(login);
            password = Hasher.hashBySha1(password);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            user.setAddress(address);
            user.setEmail(email);
            return (iUserDao.signUp(user));
        } catch (ValidatorException | DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("number format exception", e);
        }
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        logger.debug("UserServiceImpl.signIn()");
        User user = null;
        IUserDao iUserDao = daoFactory.getIUserDao();
        try {
            Validator.isNull(login, password);
            Validator.isEmptyString(login, password);
            password = Hasher.hashBySha1(password);
            user = iUserDao.signIn(login, password);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        }
        logger.debug("UserServiceImpl.signIn() - success. ");
        return user;
    }

    @Override
    public List<User> getAllUsersByRoleId(String roleId) throws ServiceException, ServiceLogicException {
        List<User> users = null;
        try {
            logger.debug("UserServiceImpl.getAllUsersByRoleId()");
            logger.info("/////"+roleId);
            Validator.isNull(roleId);
            Validator.isEmptyString(roleId);
            int id = Integer.parseInt(roleId);
            IUserDao userDao = daoFactory.getIUserDao();
            users = userDao.getAllUsersByIdRole(id);
        } catch (DaoException | ValidatorException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            throw new ServiceException("number format exception" + e);
        }
        logger.debug("UserServiceImpl.getAllUsersByRoleId() - success");
        return users;
    }


}
