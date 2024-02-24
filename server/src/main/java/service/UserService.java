package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {

    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;

    public UserService() {
        this.memoryUserDAO = new MemoryUserDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
    }

    //checks of not bland
    private boolean checkInfo(String data){
        return((data != "") && (data != null));
    }

    public String register(String username, String password, String email){
        if(checkInfo(username) && checkInfo(password) && checkInfo(email) && (this.memoryUserDAO.getUser(username)==null)){
            UserData newUser = new UserData(username, password, email);
            this.memoryUserDAO.createUser(newUser);
            AuthData authData = this.memoryAuthDAO.createAuth(username);
            return authData.getAuthToken();
        }else{
            return null;
        }
    }

    public String login(String username, String password){
        if(checkInfo(username) && checkInfo(password) && (this.memoryUserDAO.checkUserData(username, password) != null) && (this.memoryAuthDAO.getAuthUsername(username) == null)){
            AuthData authData = this.memoryAuthDAO.createAuth(username);
            return authData.getAuthToken();
        }else{
            return null;
        }
    }

    public boolean logout(String authToken){
        return this.memoryAuthDAO.deleteAuth(authToken);
    }

}
