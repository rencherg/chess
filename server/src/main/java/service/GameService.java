package service;

import chess.ChessGame;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

public class GameService {
    private MemoryGameDAO memoryGameDAO;
    private MemoryAuthDAO memoryAuthDAO;

    //Delete when db is implemented
    private MemoryUserDAO memoryUserDAO;

    private boolean checkInfo(String data){
        return((data != "") && (data != null) && (data.length() > 0));
    }

    public GameService() {
        this.memoryGameDAO = new MemoryGameDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
        this.memoryUserDAO = new MemoryUserDAO();
    }

    public GameData[] getGame(String authToken){
        if(this.memoryAuthDAO.getAuth(authToken) != null){
            return this.memoryGameDAO.listGames();
        }else{
            return null;
        }
    }

    public int createGame(String authToken, String gameName){
        if((this.checkInfo(gameName)) && this.memoryAuthDAO.getAuth(authToken) != null){
            GameData newGameData = this.memoryGameDAO.createGame(new ChessGame(), "", "", gameName);
            return newGameData.getGameID();
        }else{
            return -1;
        }
    }

    public boolean joinGame(String authToken, ChessGame.TeamColor clientColor, int gameID){
        AuthData userAuthData = this.memoryAuthDAO.getAuth(authToken);
        GameData gameData = this.memoryGameDAO.getGame(gameID);
        if((userAuthData != null) && (gameData != null)){

            //Potential bug if a user has already joined and another tries to join as the same color
            if(clientColor == ChessGame.TeamColor.WHITE && gameData.getWhiteUsername() == ""){
                gameData.setWhiteUsername(userAuthData.getUsername());
                return true;
            } else if(clientColor == ChessGame.TeamColor.BLACK && gameData.getBlackUsername() == ""){
                gameData.setBlackUsername(userAuthData.getUsername());
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //EVERYTHING BELOW SHOULD BE DELETED WHEN THE DB IS IMPLEMENTED

    public AuthData register(String username, String password, String email){
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
