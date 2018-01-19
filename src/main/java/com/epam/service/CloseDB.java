package com.epam.service;

import com.epam.service.exception.ServiceException;

public interface CloseDB {

    void closeConnections() throws ServiceException;
}
