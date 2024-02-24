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
        return((data != "") && (data != null) && (data.length() > 0));
    }

    public AuthData register(String username, String password, String email){
//        System.out.println("Username: \"" + username + "\"");
//        System.out.println(checkInfo(username));
//        System.out.println((username != ""));
//        System.out.println((username == ""));
//        System.out.println("d"+username+"d");
//        System.out.println(username.length());
        if(checkInfo(username) && checkInfo(password) && checkInfo(email) && (this.memoryUserDAO.getUser(username)==null)){
            UserData newUser = new UserData(username, password, email);
            this.memoryUserDAO.createUser(newUser);
            return this.memoryAuthDAO.createAuth(username);
//            return authData.getAuthToken();
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
