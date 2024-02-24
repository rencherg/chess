package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {

    public AuthData createAuth(String username);

    public boolean deleteAuth(String authToken);

    public AuthData getAuth(String authToken);

    public AuthData getAuthUsername(String username);

}
