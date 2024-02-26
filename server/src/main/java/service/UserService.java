package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {

    private final MemoryUserDAO memoryUserDAO;
    private final MemoryAuthDAO memoryAuthDAO;

    public UserService() {
        this.memoryUserDAO = new MemoryUserDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
    }

    //checks if not null or empty
    private boolean checkInfo(String data){
        return((!data.equals("")) && (data.length() > 0));
    }

    public AuthData register(UserData userData) throws RuntimeException{
        if(memoryUserDAO.getUser(userData.getUsername()) != null){
            throw new RuntimeException("Error: already taken");
        } else if(checkInfo(userData.getUsername()) && checkInfo(userData.getPassword()) && checkInfo(userData.getEmail()) && (this.memoryUserDAO.getUser(userData.getUsername())==null)){
            UserData newUser = new UserData(userData.getUsername(), userData.getPassword(), userData.getEmail());
            this.memoryUserDAO.createUser(newUser);
            return this.memoryAuthDAO.createAuth(userData.getUsername());
        }else{
            throw new RuntimeException("Error: bad request");
        }
    }

    public AuthData login(String username, String password){
        if ((this.memoryUserDAO.checkUserData(username, password) == null)) {
            throw new RuntimeException("Error: unauthorized");
        } else if(checkInfo(username) && checkInfo(password)){// && (this.memoryAuthDAO.getAuthUsername(username) == null) This part may need to be included to prevent a user from logging in again
            return this.memoryAuthDAO.createAuth(username);
        }else{
            throw new RuntimeException("Error: bad request");
        }
    }

    public boolean logout(String authToken){
        if(this.memoryAuthDAO.deleteAuth(authToken)){
            return true;
        }else{
            throw new RuntimeException("Error: unauthorized");
        }
    }

}
