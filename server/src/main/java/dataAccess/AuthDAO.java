package dataAccess;

import model.AuthData;
import model.UserData;

public interface AuthDAO {

    public AuthData createAuth(UserData userData);

    public boolean deleteAuth(String authToken);

    public AuthData getAuth(String authToken);

}
