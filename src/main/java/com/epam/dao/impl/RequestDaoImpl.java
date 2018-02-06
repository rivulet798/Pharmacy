package com.epam.dao.impl;

import com.epam.dao.RequestDao;
import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.dto.RequestForRenewalDto;
import com.epam.entity.RequestForRenewal;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {
    private static Logger logger = Logger.getLogger(RequestDaoImpl.class);

    private static final String GET_REQUESTS_BY_DOCTOR_ID = "SELECT * FROM pharmacy.request_for_renewal WHERE idPrescription IN (SELECT idPrescription FROM pharmacy.prescription WHERE idDoctor=?)";
    private static final String ADD_REQUEST = "INSERT INTO pharmacy.request_for_renewal (idPrescription,idRequestStatus) VALUES(?,?);";
    private static final String GET_REQUESTS_DTO_BY_DOCTOR_ID = "SELECT req.id, req.idPrescription, dateOfIssue, dateOfCompletion, number, d.dosage, a.name, a.surname, m.name AS medicamentName FROM request_for_renewal req JOIN prescription p ON req.idPrescription = p.idPrescription JOIN medicament m ON p.idMedicament = m.idMedicament JOIN account a ON p.idUser = a.idUser JOIN dosage d ON p.idDosage = d.id WHERE p.idDoctor=? AND req.idRequestStatus=1;";
    private static final String CHECK_EXIST_OF_REQUEST = "SELECT * FROM pharmacy.request_for_renewal WHERE pharmacy.request_for_renewal.idPrescription=? AND pharmacy.request_for_renewal.idRequestStatus=1;";
    private static final String CHANGE_REQUEST_STATUS = "UPDATE pharmacy.request_for_renewal SET idRequestStatus=? WHERE id=?;";

    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private RequestForRenewal requestForRenewal;
    private List<RequestForRenewal> requestsForRenewal;
    private RequestForRenewalDto requestDto;
    private List<RequestForRenewalDto> requestDtoList;

    @Override
    public List<RequestForRenewal> getRequestsByDoctorId(int userId) throws DaoException {
        logger.debug("RequestDaoImpl.getRequestsByDoctorId()");
        try {
            statement = null;
            resultSet = null;

            requestsForRenewal = new ArrayList<>();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(GET_REQUESTS_BY_DOCTOR_ID);
            statement.setInt(1,userId);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                requestForRenewal = load(resultSet);
                requestsForRenewal.add(requestForRenewal);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getRequestsByDoctorId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("RequestDaoImpl.getRequestsByDoctorId() - success ");
        return requestsForRenewal;
    }

    @Override
    public boolean addRequest(int prescriptionId, int newRequest) throws DaoException {
        logger.debug("RequestDaoImpl.addRequest()");
        try {
            statement = null;

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(ADD_REQUEST);
            statement.setInt(1,prescriptionId);
            statement.setInt(2,newRequest);
            if (statement.executeUpdate() != 0) {
                logger.debug("RequestDaoImpl.addRequest()-success");
                return true;
            } else {
                logger.debug("RequestDaoImpl.addRequest()-failed");
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addRequest)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public List<RequestForRenewalDto> getRequestsDtoByDoctorId(int idDoctor) throws DaoException {
        logger.debug("RequestDaoImpl.getRequestsDtoByDoctorId()");
        try {
            statement = null;
            resultSet = null;
            requestDtoList = new ArrayList<>();

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(GET_REQUESTS_DTO_BY_DOCTOR_ID);
            statement.setInt(1,idDoctor);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                requestDto = loadDto(resultSet);
                requestDtoList.add(requestDto);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getRequestsDtoByDoctorId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("RequestDaoImpl.getRequestsDtoByDoctorId() - success ");
        return requestDtoList;
    }

    @Override
    public boolean checkExistOfRequest(int prescriptionId) throws DaoException {
        logger.debug("RequestDaoImpl.checkExistOfRequest()");
        try {
            statement = null;
            resultSet = null;

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(CHECK_EXIST_OF_REQUEST);
            statement.setInt(1,prescriptionId);

            resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(checkExistOfRequest)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public boolean changeRequestStatus(int requestId, int status) throws DaoException {
        logger.debug("RequestDaoImpl.changeRequestStatus()");
        try {
            statement = null;

            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = connection.prepareStatement(CHANGE_REQUEST_STATUS);
            statement.setInt(1,status);
            statement.setInt(2,requestId);

            if (statement.executeUpdate() != 0) {
                logger.debug("RequestDaoImpl.changeRequestStatus()-success");
                return true;
            } else {
                logger.debug("RequestDaoImpl.changeRequestStatus()-failed");
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(changeRequestStatus)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    private RequestForRenewal load(ResultSet resultSet) throws DaoException{
        RequestForRenewal requestForRenewal = new RequestForRenewal();
        try {
            requestForRenewal.setId(resultSet.getInt("id"));
            requestForRenewal.setIdPrescription(resultSet.getInt("idPrescription"));
            requestForRenewal.setComplete(resultSet.getInt("complete"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return requestForRenewal;
    }

    private RequestForRenewalDto loadDto(ResultSet resultSet) throws DaoException{
        RequestForRenewalDto requestDto = new RequestForRenewalDto();
        try {
            requestDto.setIdRequest(resultSet.getInt("id"));
            requestDto.setIdPrescription(resultSet.getInt("idPrescription"));
            requestDto.setUserName(resultSet.getString("name"));
            requestDto.setUserSurname(resultSet.getString("surname"));
            requestDto.setMedicamentName(resultSet.getString("medicamentName"));
            requestDto.setNumber(resultSet.getInt("number"));
            requestDto.setDosage(resultSet.getInt("dosage"));
            requestDto.setDateOfIssue(resultSet.getDate("dateOfIssue"));
            requestDto.setDateOfCompletion(resultSet.getDate("dateOfCompletion"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return requestDto;
    }
}
