package com.epam.dao.pool;

import com.epam.dao.exception.ConnectionException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.MissingResourceException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool implements ICloseConnectionPool {
    private static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private final static ReentrantLock singletonLocker = new ReentrantLock();

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> givenAwayConQueue;

    private ReentrantLock retrieveLocker = new ReentrantLock();
    private ReentrantLock putBackLocker = new ReentrantLock();

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;


    private ConnectionPool() throws ConnectionException {
        try {
            DBResourceManager dbResourceManager = DBResourceManager.getInstance();
            logger.info("System found database property file");
            this.driverName = dbResourceManager.getValue(DBParametr.DB_DRIVER);
            this.user = dbResourceManager.getValue(DBParametr.DB_USER);
            this.url = dbResourceManager.getValue(DBParametr.DB_URL);
            this.password = dbResourceManager.getValue(DBParametr.DB_PASSWORD);
            this.poolSize = Integer.parseInt(dbResourceManager.getValue((DBParametr.DB_POOL_SIZE)));
            Class.forName(driverName);
            logger.info("Database driver was loaded");
        } catch (NumberFormatException e) {
            logger.warn("No correct value in database property file");
            poolSize = 5;
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver load exception: " + driverName, e);
            throw new ConnectionException("Driver load exception: " + driverName, e);
        } catch (MissingResourceException e) {
            logger.fatal("Error of upload config: " + e);
            throw new ConnectionException("Error of upload config: " + e);
        }
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionQueue.add(getConnection());
        }
    }

    private Connection getConnection() throws ConnectionException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
            logger.info("Get connection from database");
        } catch (SQLException e) {
            logger.warn("Can't get connection from database", e);
            throw new ConnectionException("не возможно выдать соединение к БД", e);
        }
        return connection;
    }

    public Connection retrieve() throws ConnectionException {//извлечь
        Connection connection;
        retrieveLocker.lock();
        try {
            if (connectionQueue.size() == 0) {
                connection = getConnection();
            } else {
                try {
                    connection = connectionQueue.take();
                } catch (InterruptedException e) {
                    throw new ConnectionException("не возможно выдать соединение к БД", e);
                }

            }
            givenAwayConQueue.add(connection);
            return connection;
        }finally {
            retrieveLocker.unlock();
        }
    }

    private void putBack(Connection connection) throws NullPointerException {//вернуть
        if (connection != null) {
            putBackLocker.lock();
            try{
                givenAwayConQueue.remove(connection);
                connectionQueue.add(connection);
            } finally {
                putBackLocker.unlock();
            }

        } else {
            throw new NullPointerException("Connection is null");
        }
    }

    public static ConnectionPool getInstance() throws ConnectionException {//Высокая производительность
        if (!isInitialized.get()) {
            singletonLocker.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isInitialized.set(true);
                }
            } finally {
                singletonLocker.unlock();
            }
        }
        return instance;
    }

    public void putBackConnection(Connection con, Statement st, ResultSet resultSet) {
        try {
            this.putBack(con);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void putBackConnection(Connection con, Statement st) {
        this.putBackConnection(con, st, null);
    }

    @Override
    public void  releasePool() {
        while (!givenAwayConQueue.isEmpty()) {
            try {
                Connection connection = givenAwayConQueue.take();
                connection.close();
            } catch (InterruptedException | SQLException e) {
                logger.error("Can't close connection" + e);
            }
        }
        while (!connectionQueue.isEmpty()) {
            try {
                Connection connection = connectionQueue.take();
                connection.close();
            } catch (InterruptedException | SQLException e) {
                logger.error("Can't close connection" + e);
            }
        }
    }
}
