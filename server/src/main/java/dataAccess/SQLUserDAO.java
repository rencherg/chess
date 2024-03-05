package dataAccess;

import model.UserData;

import java.sql.*;

public class SQLUserDAO implements UserDAO {

//    Connection myConnection;
//    PreparedStatement myPreparedStatement;
//    ResultSet resultSet = null;

    public SQLUserDAO() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
    }

    public UserData getUser(String username) throws SQLException {

//        UserData foundData = null;
//
//        Connection myConnection = DatabaseManager.getConnection();
//
//        String sqlQuery = "SELECT * FROM user_data WHERE username = ?;";
//        ResultSet resultSet = DatabaseManager.executeQueryWithParams(myConnection, sqlQuery, username);
//
//        while (resultSet.next()) {
//            foundData = new UserData(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
//        }
//
//        if(resultSet != null){
//            resultSet.close();
//        }
//
//        myConnection.close();
//
//        return foundData;

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

            // Step 5: Process the result
            while (resultSet.next()) {
                foundData = new UserData(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
            }

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

    //Checks if username and password are correct
    public UserData checkUserData(String username, String password) throws SQLException {

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

            // Step 5: Process the result
            while (resultSet.next()) {
                foundData = new UserData(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("email"));
            }

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

    public UserData createUser(UserData user) throws SQLException {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        boolean wasSuccesful;

        UserData foundData = null;

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

            myPreparedStatement.close();
            myConnection.close();
        }

        return user;
    }
}
