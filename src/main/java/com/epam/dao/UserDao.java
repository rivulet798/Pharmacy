package com.epam.dao;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {
    private static Logger logger = Logger.getLogger(UserDao.class);

    public static String GET_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM pharmacy.account WHERE login=? AND password=?;";
    public static String GET_USER_BY_ID = "SELECT * FROM pharmacy.account WHERE id=?;";
    public static String SIGN_UP_USER = "INSERT INTO account (idRole,login,password,name,surname,address,email) VALUES(?,?,?,?,?,?,?);";
    public static String GET_ALL_USERS_BY_ID_ROLE = "SELECT * FROM pharmacy.account WHERE idRole=?;";
    public static String GET_USER_BY_LOGIN = "SELECT * FROM pharmacy.account WHERE login=?;";
    public static String GET_USER_BY_EMAIL = "SELECT * FROM pharmacy.account WHERE email=?;";



    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private User user;
    private List<User> users;

    @Override
    public boolean addUser() throws DaoException{
        logger.debug("UserDao.addUser()");
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(SIGN_UP_USER);
            statement.setInt(1,user.getIdRole());
            statement.setString(2,user.getLogin());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getName());
            statement.setString(5,user.getSurname());
            statement.setString(6,user.getAddress());
            if(statement.executeUpdate()!=0){
                logger.debug("UserDao.addUser()-success");
                return true;
            }else{
                logger.debug("UserDao.addUser()-failed");
                return false;
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addUser)", e);
        }catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public boolean deleteUser() {
        return false;
    }

    @Override
    public boolean createUser() {
        return false;
    }

    @Override
    public boolean signUp(User user) throws DaoException {
        logger.debug("UserDao.signUp()");
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(SIGN_UP_USER);
            statement.setInt(1,user.getIdRole());
            statement.setString(2,user.getLogin());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getName());
            statement.setString(5,user.getSurname());
            statement.setString(6,user.getAddress());
            statement.setString(7,user.getEmail());
            if(statement.executeUpdate()!=0){
                logger.debug("UserDao.signUp() - success");
                return true;
            }else{
                logger.debug("UserDao.signUp() - failed");
                return false;
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(signUp)", e);
        }catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public User signIn(String login, String password) throws DaoException{
        logger.debug("UserDao.signIn()");
        try {
            statement = null;
            resultSet = null;
            user = null;

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD);
            statement.setString(1,login);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                user = load(resultSet);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(signIn)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("UserDao.signIn() - success");
        return user;
    }

    @Override
    public User getUserById(int id) throws DaoException{
        logger.debug("UserDao.getUserById()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setInt(1,id);
            resultSet = null;
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                user = load(resultSet);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getUserById)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("UserDao.getUserById() - success");
        return user;
    }

    @Override
    public List<User> getAllUsersByIdRole(int idRole) throws DaoException{
        logger.debug("UserDao.getAllUsers()");
        try {
            statement = null;
            resultSet = null;
            user = null;
            users = new ArrayList<>();

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(GET_ALL_USERS_BY_ID_ROLE);
            statement.setInt(1,idRole);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = load(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getAllUsers)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("UserDao.getAllUsers() - success");
        return users;
    }

    public boolean isLoginUnique(String login) throws DaoException{
        logger.debug("UserDao.isLoginUnique(login)");
        try {
            statement = null;
            resultSet = null;
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            return !(resultSet.next());

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(isLoginUnique)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    public boolean isEmailUnique(String email) throws DaoException{
        logger.debug("UserDao.isEmailUnique(email)");
        try {
            statement = null;
            resultSet = null;
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = connection.prepareStatement(GET_USER_BY_EMAIL);
            statement.setString(1,email);
            resultSet = statement.executeQuery();
            return !(resultSet.next());

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(isEmailUnique)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    private User load(ResultSet resultSet) throws DaoException{
        User user = new User();
        try {
            user.setId(resultSet.getInt("idUser"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setAddress(resultSet.getString("address"));
            user.setIdRole(resultSet.getInt("idRole"));
            user.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }
}
