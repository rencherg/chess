package dataAccess;

import server.model.AuthData;
import server.model.UserData;

public interface AuthDAO {

    public AuthData createAuth(UserData userData);

    public boolean deleteAuth(String authToken);

    public AuthData getAuth(String authToken);

}
