package com.epam.dao;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.entity.Medicament;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDao implements IMedicamentDao {
    private static Logger logger = Logger.getLogger(MedicamentDao.class);

    private static final String GET_ALL_MEDICAMENTS = "SELECT * FROM pharmacy.medicament;";
    private static final String GET_SORTED_BY_PRICE_MEDICAMENTS_ASC = "SELECT * FROM pharmacy.medicament ORDER BY price ASC;";
    private static final String GET_SORTED_BY_PRICE_MEDICAMENTS_DESC = "SELECT * FROM pharmacy.medicament ORDER BY price DESC;";
    private static final String GET_MEDICAMENT_BY_ID = "SELECT * FROM pharmacy.medicament WHERE idMedicament=?;";
    private static final String ADD_MEDICAMENT = "INSERT INTO medicament (name,producer,price,prescription,image,availability) VALUES(?,?,?,?,?,?);";
    private static final String GET_MEDICAMENTS_BY_PRODUCER = "SELECT * FROM pharmacy.medicament WHERE producer=?;";
    private static final String GET_MEDICAMENT_BY_NAME = "SELECT * FROM pharmacy.medicament WHERE name LIKE ?;";
    private static final String EDIT_MEDICAMENT = "UPDATE pharmacy.medicament m SET m.name=?, m.producer=?, m.price=?, m.prescription=?, m.image=?, m.availability=? WHERE m.idMedicament=?;";
    private static final String GET_MEDICAMENTS_BY_PRESCRIPTION = "SELECT * FROM pharmacy.medicament WHERE prescription=?;";

    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Medicament medicament;
    private List<Medicament> medicaments;

    public MedicamentDao(){
    }

    @Override
    public int addMedicament(Medicament medicament) throws DaoException{
        logger.debug("MedicamentDao.addMedicament()");
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            String generatedColumns[] = { "idMedicament" };
            statement = connection.prepareStatement(ADD_MEDICAMENT, generatedColumns);
            statement.setString(1,medicament.getName());
            statement.setString(2,medicament.getProducer());
            statement.setFloat(3,medicament.getPrice());
            statement.setBoolean(4,medicament.isPrescription());
            statement.setString(5,medicament.getImage());
            statement.setBoolean(6,medicament.isAvailability());
            if(statement.executeUpdate()!=0){
                logger.debug("MedicamentDao.addMedicament()-success");
                resultSet = statement.getGeneratedKeys();
                resultSet.next();
                int idMedicament = resultSet.getInt(1);
                return idMedicament;
            }else{
                logger.debug("MedicamentDao.addMedicament()-failed");
                return 0;
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(addMedicament)", e);
        }catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public boolean deleteMedicament() {
        return false;
    }


    @Override
    public Medicament getMedicamentById(int id) throws DaoException{
        logger.debug("MedicamentDao.getMedicamentById()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_MEDICAMENT_BY_ID);
            statement.setInt(1,id);
            resultSet = null;
            resultSet = statement.executeQuery();
            if (resultSet.first()) {
                medicament = load(resultSet);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getMedicamentById)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getMedicamentById() - success"+medicament);
        return medicament;
    }

    @Override
    public List<Medicament> getAllMedicaments() throws DaoException{
        logger.debug("MedicamentDao.getAllMedicaments()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_ALL_MEDICAMENTS);
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getAllMedicaments)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getAllMedicaments()-success");
        return medicaments;
    }

    @Override
    public List<Medicament> getAscSortedByPriceMedicaments() throws DaoException{

        logger.debug("MedicamentDao.getAscSortedByPriceMedicaments()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_SORTED_BY_PRICE_MEDICAMENTS_ASC);
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getAscSortedByPriceMedicaments)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getAscSortedByPriceMedicaments()-success");
        return medicaments;

    }

    @Override
    public List<Medicament> getDescSortedByPriceMedicaments() throws DaoException{

        logger.debug("MedicamentDao.getDescSortedByPriceMedicaments()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_SORTED_BY_PRICE_MEDICAMENTS_DESC);
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getDescSortedByPriceMedicaments)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getDescSortedByPriceMedicaments()-success");
        return medicaments;

    }
    @Override
    public List<Medicament> getMedicamentsByProducer(String producer) throws DaoException {
        logger.debug("MedicamentDao.getMedicamentsByProducer()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_MEDICAMENTS_BY_PRODUCER);
            statement.setString(1,producer);
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(e.getMessage());
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getMedicamentByProducer)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getMedicamentsByProducer() - success");
        return medicaments;
    }

    @Override
    public List<Medicament> getMedicamentsByName(String name) throws DaoException {
        logger.debug("MedicamentDao.getMedicamentsByName()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_MEDICAMENT_BY_NAME);
            statement.setString(1,"%"+name+"%");
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getMedicamentByName)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getMedicamentsByName() - success");
        return medicaments;
    }

    @Override
    public boolean editMedicament(Medicament medicament) throws DaoException {
        logger.debug("MedicamentDao.editMedicament()");
        try{
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(EDIT_MEDICAMENT);
            statement.setString(1,medicament.getName());
            statement.setString(2,medicament.getProducer());
            statement.setFloat(3,medicament.getPrice());
            statement.setBoolean(4,medicament.isPrescription());
            statement.setString(5,medicament.getImage());
            statement.setBoolean(6,medicament.isAvailability());
            statement.setInt(7,medicament.getId());
            logger.info(statement);
            if(statement.executeUpdate()!=0){
                logger.debug("MedicamentDao.editMedicament()-success");
                return true;
            }else{
                logger.debug("MedicamentDao.editMedicament()-failed");
                return false;
            }
        }catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(editMedicament)", e);
        }catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        }finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
    }

    @Override
    public List<Medicament> getMedicamentsByPrescription(int prescriptionStatus) throws DaoException{
        logger.debug("MedicamentDao.getMedicamentsByPrescription()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();
            statement = null;
            statement = connection.prepareStatement(GET_MEDICAMENTS_BY_PRESCRIPTION);
            statement.setInt(1,prescriptionStatus);
            resultSet = null;
            resultSet = statement.executeQuery();
            medicament = null;
            medicaments = new ArrayList<Medicament>();
            while (resultSet.next()) {
                medicament = load(resultSet);
                medicaments.add(medicament);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(e.getMessage());
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getMedicamentByPrescription)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("MedicamentDao.getMedicamentsByPrescription() - success");
        return medicaments;
    }

    private Medicament load(ResultSet resultSet) throws DaoException{
        Medicament medicament = new Medicament();
        try {
            medicament.setId(resultSet.getInt("idMedicament"));
            medicament.setProducer(resultSet.getString("producer"));
            medicament.setPrescription(resultSet.getBoolean("prescription"));
            medicament.setPrice(resultSet.getFloat("price"));
            medicament.setName(resultSet.getString("name"));
            medicament.setImage(resultSet.getString("image"));
            medicament.setAvailability(resultSet.getBoolean("availability"));
            medicament.setModeOfApplication(resultSet.getString("modeOfApplication"));
            medicament.setContraindications(resultSet.getString("contraindications"));
            medicament.setSideEffects(resultSet.getString("sideEffects"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return medicament;
    }

}
