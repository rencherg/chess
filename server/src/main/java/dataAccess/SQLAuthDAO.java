package dataAccess;

import model.AuthData;
import model.UserData;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

public class SQLAuthDAO  implements AuthDAO{

    public SQLAuthDAO() {

    }

    //Generates a unique token
    private String getUniqueToken(){

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        String token = "";

        boolean foundValidToken = false;

        while(foundValidToken == false){
            random.nextBytes(bytes);
            token = bytes.toString();
            if(this.getAuth(token) == null){
                foundValidToken = true;
            }
        }

        return token;
    }

    public AuthData createAuth(String username){

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        boolean wasSuccesful;

        UserData foundData = null;

        //Getuserid first

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "INSERT INTO auth_data\n" +
                    "(\n" +
                    "    user_id,\n" +
                    "    username,\n" +
                    "    token\n" +
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

    public boolean deleteAuth(String authToken){

        Iterator<AuthData> dataIterator = TempDB.authSet.iterator();

        AuthData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getAuthToken().equals(authToken)){
                TempDB.authSet.remove(iteratorData);
                return true;
            }
        }
        return false;

    }

    public AuthData getAuth(String authToken){

        Iterator<AuthData> dataIterator = TempDB.authSet.iterator();

        AuthData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getAuthToken().equals(authToken)){
                return iteratorData;
            }
        }
        return null;

    }
}
