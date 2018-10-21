package myapp.service.util.logger.impl;

import myapp.service.util.logger.MyAppLogger;

import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by renzogcorzo on 10/21/2018.
 */

public class DatabaseLogger implements MyAppLogger {
    private String category;
    private HashMap<String, String> dbParams;
    private Connection connection = null;
    private PreparedStatement stmt = null;

    public DatabaseLogger(String category, HashMap<String, String> dbParams) {
        this.dbParams = dbParams;
        this.category = category;
    }

    @Override
    public void info(String messageText) {
        try {
            initDbConnection();
            stmt.setString(1, messageText);
            stmt.setInt(2, 1);
            processTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void warning(String messageText) {
        try {
            initDbConnection();
            stmt.setString(1, messageText);
            stmt.setInt(2, 2);
            processTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void error(String messageText) {
        try {
            initDbConnection();
            stmt.setString(1, messageText);
            stmt.setInt(2, 3);
            processTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                closeDbConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initDbConnection() throws SQLException {
        connection = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));

        connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
                + ":" + dbParams.get("portNumber") + "/", connectionProps);

        connection.setAutoCommit(false);

        stmt = connection.prepareStatement("insert into Log_Values(?,?");
    }

    private void closeDbConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private void processTransaction() throws SQLException {
        stmt.executeUpdate();
        connection.commit();
    }
}
