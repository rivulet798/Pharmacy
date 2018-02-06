package com.epam.command.impl.common;

import com.epam.command.CloseDBCommand;
import com.epam.service.impl.CloseDBServiceImpl;
import com.epam.service.CloseDB;
import com.epam.service.exception.ServiceException;
import org.apache.log4j.Logger;

public class CloseDBCommandImpl implements CloseDBCommand {
    private static Logger logger = Logger.getLogger(CloseDBCommandImpl.class);

    @Override
    public void closeDB()  {
        try {
            CloseDB closeDB = new CloseDBServiceImpl();
            closeDB.closeConnections();
        } catch (ServiceException e) {
            logger.error(e);
        }
    }
}
