package dataAccess;

import model.AuthData;

//Auth Dao
public interface AuthDAO {

    AuthData createAuth(String username);

    boolean deleteAuth(String authToken);

    AuthData getAuth(String authToken);

//    AuthData getAuthUsername(String username);

}
