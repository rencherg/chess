package dataAccess;

import model.UserData;

import java.sql.*;

public class SQLUserDAO implements UserDAO {

    public UserData getUser(String username) throws SQLException {

        if(DatabaseManager.rowCount("user_data") == 0){
            return null;
        }

        UserData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM user_data WHERE username = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, username);
            resultSet = myPreparedStatement.executeQuery();

            resultSet.next();
            foundData = new UserData(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                resultSet.close();
            }
            myPreparedStatement.close();
            myConnection.close();

            return foundData;
        }
    }

    //for internal package use
    static String getUserID(String username) throws SQLException {

        if(DatabaseManager.rowCount("user_data") == 0){
            return null;
        }

        UserData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM user_data WHERE username = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, username);
            resultSet = myPreparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("id");
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                resultSet.close();
            }
            myPreparedStatement.close();
            myConnection.close();

            return null;
        }
    }

    //Checks if username and password are correct
    public UserData checkUserData(String username, String password) throws SQLException {

        if(DatabaseManager.rowCount("user_data") == 0){
            return null;
        }

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;
        UserData foundData = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM user_data WHERE username = ? and password = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, username);
            myPreparedStatement.setString(2, password);
            resultSet = myPreparedStatement.executeQuery();

            while (resultSet.next()) {
                foundData = new UserData(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                resultSet.close();
            }
            if(myPreparedStatement!= null){
                myPreparedStatement.close();
            }
            if(myConnection != null){
                myConnection.close();
            }

            return foundData;
        }
    }

    public UserData createUser(UserData user) throws SQLException {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        boolean wasSuccesful = false;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "INSERT INTO user_data\n" +
                    "(\n" +
                    "    username,\n" +
                    "    password,\n" +
                    "    email\n" +
                    ")\n" +
                    "VALUES\n" +
                    "(?, ?, ?);";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, user.getUsername());
            myPreparedStatement.setString(2, user.getPassword());
            myPreparedStatement.setString(3, user.getEmail());
            wasSuccesful = myPreparedStatement.execute();

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{

            if(myPreparedStatement!= null){
                myPreparedStatement.close();
            }
            if(myConnection != null){
                myConnection.close();
            }

        }

        if(wasSuccesful){
            return user;
        }else{
            return null;
        }
    }
}
