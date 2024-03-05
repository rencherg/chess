package dataAccess;

import model.AuthData;
import model.UserData;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO{

    public AuthData createAuth(String username) throws SQLException {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        String userID = SQLUserDAO.getUserID(username);
        String token = this.getUniqueToken();
        AuthData authData = null;

        if(userID == null){
            return null;
        }

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "INSERT INTO auth_data\n" +
                    "(\n" +
                    "    username,\n" +
                    "    token,\n" +
                    "    user_id\n" +
                    ")\n" +
                    "VALUES\n" +
                    "(?, ?, ?);";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, username);
            myPreparedStatement.setString(2, token);
            myPreparedStatement.setString(3, userID);
            if(myPreparedStatement.execute()){
                authData = new AuthData(token, username);
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{

            myPreparedStatement.close();
            myConnection.close();
            return authData;

        }
    }

    public boolean deleteAuth(String authToken) throws SQLException {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "DELETE * FROM auth_data WHERE token = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, authToken);
            myPreparedStatement.executeQuery();

            return true;

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
            return false;
        } finally{
            myPreparedStatement.close();
            myConnection.close();

        }

    }

    public AuthData getAuth(String authToken) throws SQLException {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;

        AuthData foundData = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM auth_data WHERE token = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, authToken);
            resultSet = myPreparedStatement.executeQuery();

            while (resultSet.next()) {
                foundData = new AuthData(resultSet.getString("token"), resultSet.getString("username"));
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
}
