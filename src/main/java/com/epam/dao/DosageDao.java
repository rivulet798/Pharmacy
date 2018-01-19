package com.epam.dao;

import com.epam.dao.exception.ConnectionException;
import com.epam.dao.exception.DaoException;
import com.epam.dao.pool.ConnectionPool;
import com.epam.entity.Dosage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DosageDao implements IDosageDao {
    private static Logger logger = Logger.getLogger(DosageDao.class);

    public static String GET_DOSAGES_BY_MEDICAMENT_ID = "SELECT * FROM pharmacy.dosage WHERE idMedicament=?;";

    private ConnectionPool connectionPool;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Dosage dosage;
    private List<Dosage> dosages;

    @Override
    public List<Dosage> getDosagesByMedicamentId(int idMedicament) throws DaoException {
        logger.debug("DosageDao.getDosagesByMedicamentId()");
        try {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.retrieve();

            statement = null;
            statement = connection.prepareStatement(GET_DOSAGES_BY_MEDICAMENT_ID);
            statement.setInt(1,idMedicament);

            resultSet = null;
            resultSet = statement.executeQuery();
            dosage = null;
            dosages = new ArrayList<Dosage>();
            while (resultSet.next()) {
                dosage = load(resultSet);
                dosages.add(dosage);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
                logger.error(e.getMessage());
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
                throw new DaoException(e);
            }
            throw new DaoException("Error of query to database(getDosagesByMedicamentId)", e);
        } catch (ConnectionException e){
            throw new DaoException("Error with connection with database"+e);
        } finally {
            if (connectionPool != null) {
                connectionPool.putBackConnection(connection, statement, resultSet);
            }
        }
        logger.debug("DosageDao.getDosagesByMedicamentId() - success");
        return dosages;
    }

    private Dosage load(ResultSet resultSet) throws DaoException{
        Dosage dosage = new Dosage();
        try {
            dosage.setId(resultSet.getInt("id"));
            dosage.setIdMedicament(resultSet.getInt("idMedicament"));
            dosage.setDosage(resultSet.getInt("dosage"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        }
        return dosage;
    }
}
