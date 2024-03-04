package dataAccess;

import dataAccess.TempDB;
import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class SQLUserDAO {

    Connection myConnection;
    Statement myStatement;
    ResultSet resultSet = null;

    public SQLUserDAO() throws DataAccessException, SQLException {
        DatabaseManager.createDatabase();
    }

    public UserData getUser(String username) throws SQLException {

        UserData foundData = null;

        try {

            myConnection = DatabaseManager.getConnection();
            myStatement = myConnection.createStatement();

            String sqlQuery = "SELECT * FROM user_data WHERE username = \'" + username + "\';";
            ResultSet resultSet = myStatement.executeQuery(sqlQuery);

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
            myStatement.close();
            myConnection.close();

            return foundData;
        }
    }

    //Checks if username and password are correct
    public UserData checkUserData(String username, String password) throws SQLException {

        UserData foundData = null;

        try {

            myConnection = DatabaseManager.getConnection();
            myStatement = myConnection.createStatement();

            String sqlQuery = "SELECT * FROM user_data WHERE username = \'" + username + "\' and password = \'" + password + "\';";
            ResultSet resultSet = myStatement.executeQuery(sqlQuery);

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
            myStatement.close();
            myConnection.close();

            return foundData;
        }
    }

    public UserData createUser(UserData user){

        TempDB.userSet.add(user);

        return user;
    }
}
