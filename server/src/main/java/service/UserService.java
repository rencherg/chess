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

    public AuthData register(UserData userData) throws RuntimeException{
        if(memoryUserDAO.getUser(userData.getUsername()) != null){
            System.out.println("In here");
            throw new RuntimeException("Error: already taken");
        } else if(checkInfo(userData.getUsername()) && checkInfo(userData.getUsername()) && checkInfo(userData.getEmail()) && (this.memoryUserDAO.getUser(userData.getUsername())==null)){
            UserData newUser = new UserData(userData.getUsername(), userData.getPassword(), userData.getEmail());
            this.memoryUserDAO.createUser(newUser);
            return this.memoryAuthDAO.createAuth(userData.getUsername());
        }else{
            throw new RuntimeException("Error: bad request");
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
