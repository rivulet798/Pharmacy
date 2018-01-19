package com.epam.service;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.dao.pool.ICloseConnectionPool;
import com.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

public class CloseDBServiceImpl implements CloseDB {
    private static Logger logger = Logger.getLogger(CloseDBServiceImpl.class);
    public void closeConnections()throws ServiceException {
        logger.debug("Service.closeConnection()");

        try {
            ICloseConnectionPool pool = ConnectionPool.getInstance();
            pool.releasePool();
        }catch (ConnectionException e){
            throw new ServiceException(e);
        }

        logger.debug("Service.closeConnection() - success");
    }
}
