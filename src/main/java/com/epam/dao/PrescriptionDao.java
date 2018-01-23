package com.epam.dao;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Prescription;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDao implements IPrescriptionDao {
    private static Logger logger = Logger.getLogger(PrescriptionDao.class);

    private static final String SET_PRESCRIPTION_INVALID = "UPDATE pharmacy.prescription SET valid=0 WHERE idPrescription=?;";
    private static String GET_PRESCRIPTION_BY_ID = "SELECT * FROM pharmacy.prescription WHERE idPrescription=?;";
    private static String ADD_PRESCRIPTION = "INSERT INTO prescription (idDoctor,idUser,idMedicament,dateOfIssue,dateOfCompletion,idDosage,number) VALUES(?,?,?,?,?,?,?);";
    private static String GET_PRESCRIPTIONS_BY_USER_ID = "SELECT * FROM pharmacy.prescription WHERE prescription.valid=1 AND prescription.idUser=?;";
    private static String GET_PRESCRIPTIONS_DTO_BY_USER_ID = "SELECT idPrescription, dateOfIssue, dateOfCompletion, number, dosage, a.name, a.surname, m.name AS medicamentName FROM prescription p JOIN medicament m ON p.idMedicament = m.idMedicament JOIN account a ON p.idDoctor = a.idUser JOIN dosage d ON p.idDosage = d.id WHERE p.idUser=? AND p.valid=1;";
    private  static String GET_PRESCRIPTIONS_BY_USER_ID_AND_MED_ID = "SELECT d.id as idDosage, dosage, number, valid, p.idPrescription FROM prescription p JOIN dosage d ON p.idDosage = d.id WHERE p.idUser=? AND p.idMedicament=? AND p.valid=1;";

    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Prescription prescription;
    private List<Prescription> prescriptions;
    private PrescriptionDto prescriptionDto;
    private List<PrescriptionDto> prescriptionDtoList;

    @Override
    public boolean addPrescription(Prescription prescription) throws DaoException {

        logger.debug("PrescriptionDao.addPrescription()");
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(ADD_PRESCRIPTION);
            statement.setInt(1,prescription.getIdDoctor());
            statement.setInt(2,prescription.getIdUser());
            statement.setInt(3,prescription.getIdMedicament());
            statement.setDate(4,prescription.getDateOfIssue());
            statement.setDate(5,prescription.getDateOfCompletion());
            statement.setInt(6, prescription.getIdDosage());
            statement.setInt(7, prescription.getNumber());

            if(statement.executeUpdate()!=0){
                logger.debug("PrescriptionDao.addPrescription()-success");
                return true;
            }else{
                logger.debug("PrescriptionDao.addPrescription()-failed");
                return false;
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addPrescription)", e);
        }catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public boolean createPrescription() {
        return false;
    }

    @Override
    public Prescription getPrescriptionById(int id) throws DaoException{
        logger.debug("PrescriptionDao.getPrescriptionById()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_PRESCRIPTION_BY_ID);
            statement.setInt(1,id);
            resultSet = null;
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
               prescription = load(resultSet);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getPrescriptionById)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("PrescriptionDao.getPrescriptionById() - success "+prescription);
        return prescription;
    }
    @Override
    public List<Prescription> getPrescriptionsByUserId(int userId) throws DaoException {
        logger.debug("PrescriptionDao.getPrescriptionsByUserId()");
        try {
            prescriptionDtoList = new ArrayList<>();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_PRESCRIPTIONS_BY_USER_ID);
            statement.setInt(1,userId);
            resultSet = null;
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                prescription = load(resultSet);
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getPrescriptionsByUserId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("PrescriptionDao.getPrescriptionsByUserId() - success ");
        return prescriptions;
    }

    @Override
    public boolean changeStatus(Prescription prescription) {
        return false;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsDtoByUserId(int userId) throws DaoException {
        logger.debug("PrescriptionDao.getPrescriptionsDtoByUserId()");
        try {
            prescriptionDtoList = new ArrayList<>();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_PRESCRIPTIONS_DTO_BY_USER_ID);
            statement.setInt(1,userId);
            resultSet = null;
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                prescriptionDto = loadDto(resultSet);
                prescriptionDtoList.add(prescriptionDto);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getPrescriptionsByUserId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("PrescriptionDao.getPrescriptionsByUserId() - success ");
        return prescriptionDtoList;
    }

    @Override
    public List<PrescriptionDto> getPrescriptionsDtoByUserIdAndMedId(int userId, int idMedicament) throws DaoException {
        logger.debug("PrescriptionDao.getPrescriptionsByUserIdAndMedId()");
        try {
            prescriptionDtoList = new ArrayList<>();
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_PRESCRIPTIONS_BY_USER_ID_AND_MED_ID);
            statement.setInt(1,userId);
            statement.setInt(2,idMedicament);
            resultSet = null;
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                prescriptionDto = new PrescriptionDto();
                prescriptionDto.setIdPrescription(resultSet.getInt("idPrescription"));
                prescriptionDto.setNumber(resultSet.getInt("number"));
                prescriptionDto.setDosage(resultSet.getInt("dosage"));
                prescriptionDto.setIdDosage(resultSet.getInt("idDosage"));
                prescriptionDto.setValid(resultSet.getBoolean("valid"));
                System.out.println(prescriptionDto.getIdPrescription());
                prescriptionDtoList.add(prescriptionDto);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getPrescriptionsByUserIdAndMediId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("PrescriptionDao.getPrescriptionsByUserIdAndMedId() - success ");
        return prescriptionDtoList;
    }

    @Override
    public boolean setPrescriptionInvalid(int idPrescription) throws DaoException {
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(SET_PRESCRIPTION_INVALID);
            statement.setInt(1,idPrescription);
            resultSet = null;
            if(statement.executeUpdate()!=0) {
                return true;
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return false;
    }

    private Prescription load(ResultSet resultSet) throws DaoException{
        Prescription prescription = new Prescription();
        try {
            prescription.setId(resultSet.getInt("idPrescription"));
            prescription.setDoctorId(resultSet.getInt("idDoctor"));
            prescription.setUserId(resultSet.getInt("idUser"));
            prescription.setIdMedicament(resultSet.getInt("idMedicament"));
            prescription.setDateOfIssue(resultSet.getDate("dateOfIssue"));
            prescription.setDateOfCompletion(resultSet.getDate("dateOfCompletion"));
            prescription.setValid(resultSet.getBoolean("valid"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return prescription;
    }

    private PrescriptionDto loadDto(ResultSet resultSet) throws DaoException{
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        try {
            prescriptionDto.setIdPrescription(resultSet.getInt("idPrescription"));
            prescriptionDto.setDoctorName(resultSet.getString("name"));
            prescriptionDto.setDoctorSurname(resultSet.getString("surname"));
            prescriptionDto.setDateOfIssue(resultSet.getDate("dateOfIssue"));
            prescriptionDto.setDateOfCompletion(resultSet.getDate("dateOfCompletion"));
            prescriptionDto.setNumber(resultSet.getInt("number"));
            prescriptionDto.setDosage(resultSet.getInt("dosage"));
            prescriptionDto.setMedicamentName(resultSet.getString("medicamentName"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return prescriptionDto;
    }
}
