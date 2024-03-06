package dataAccess;

import model.UserData;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static final String databaseName;
    private static final String user;
    private static final String password;
    private static final String connectionUrl;

    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                databaseName = props.getProperty("db.name");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                connectionUrl = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    static {
        try {
            DatabaseManager.createDatabase();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Unable to create the database. " + ex.getMessage());
        }
    }

    static {
        try {
            DatabaseManager.createTables();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException("Unable to create the tables. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    public static void createDatabase() throws DataAccessException {
        try {
            var statement1 = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            var statement2 = "USE " + databaseName;
            var conn = DriverManager.getConnection(connectionUrl, user, password);
            try (var preparedStatement = conn.prepareStatement(statement1)) {
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = conn.prepareStatement(statement2)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Creates the required tables if it does not already exist.
     */
    public static void createTables() throws DataAccessException {
        try {
            var statementUse = "USE " + databaseName;
            var userTableStatement = "CREATE TABLE IF NOT EXISTS user_data (id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255))";
            var authTableStatement = "CREATE TABLE IF NOT EXISTS auth_data (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, username VARCHAR(255), token VARCHAR(255))";
            var gameTableStatement = "CREATE TABLE IF NOT EXISTS game_data (id INT AUTO_INCREMENT PRIMARY KEY, black_id INT, white_id INT, game_name VARCHAR(255), game_data VARCHAR(255))";
            var conn = DriverManager.getConnection(connectionUrl, user, password);
            try (var preparedStatement = conn.prepareStatement(statementUse)) {
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = conn.prepareStatement(userTableStatement)) {
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = conn.prepareStatement(authTableStatement)) {
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = conn.prepareStatement(gameTableStatement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    public static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(connectionUrl, user, password);
            conn.setCatalog(databaseName);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}