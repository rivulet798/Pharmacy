package com.epam.dao.pool;

import org.junit.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPoolTest {

    private String url;
    private String user;
    private String password;

    private ResourceBundle bundle = ResourceBundle.getBundle("dao/db_test");
    private ResourceBundle wrongBundle = ResourceBundle.getBundle("dao/db_wrong_data");


    @Test(expected = SQLException.class)
    public void shouldThrowExceptionWhenCanNotGetConnectionFromDB() throws SQLException{
        this.url = wrongBundle.getString(DBParametr.DB_URL);
        this.user = wrongBundle.getString(DBParametr.DB_USER);
        this.password = wrongBundle.getString(DBParametr.DB_PASSWORD);
        DriverManager.getConnection(url, user, password);
    }

    @Test
    public void shouldNotThrowExceptionWhenCanGetConnectionFromDB() throws SQLException{
        this.url = bundle.getString(DBParametr.DB_URL);
        this.user = bundle.getString(DBParametr.DB_USER);
        this.password = bundle.getString(DBParametr.DB_PASSWORD);
        DriverManager.getConnection(url, user, password);
    }


}
