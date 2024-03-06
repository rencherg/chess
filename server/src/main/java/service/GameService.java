package service;

import chess.ChessGame;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;

public class GameService {
    private final MemoryGameDAO memoryGameDAO;
    private final MemoryAuthDAO memoryAuthDAO;

    private boolean checkInfo(String data){
        return((data != "") && (data != null) && (data.length() > 0));
    }

    public GameService() {
        this.memoryGameDAO = new MemoryGameDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
    }

    public GameData[] getGame(String authToken) throws RuntimeException{
        if(this.memoryAuthDAO.getAuth(authToken) != null){
            return this.memoryGameDAO.listGames();
        }else{
            throw new RuntimeException("Error: unauthorized");
        }
    }

    public int createGame(String authToken, String gameName) throws SQLException {
        if(this.memoryAuthDAO.getAuth(authToken) == null){
            throw new RuntimeException("Error: unauthorized");
        }if((this.checkInfo(gameName))){
            GameData newGameData = this.memoryGameDAO.createGame(new ChessGame(), null, null, gameName);
            return newGameData.getGameID();
        }else{
            throw new RuntimeException("Error: bad request");
        }
    }

    public boolean joinGame(String authToken, String clientColor, int gameID){
        AuthData userAuthData = this.memoryAuthDAO.getAuth(authToken);
        GameData gameData = this.memoryGameDAO.getGame(gameID);
        if(userAuthData == null){
            throw new RuntimeException("Error: unauthorized");
        } else if(gameData == null) {
            throw new RuntimeException("Error: bad request");
        }else if(clientColor == null){
            return true;
        }else if(((clientColor.equals("BLACK")) && (gameData.getBlackUsername()!=null) || ((clientColor.equals("WHITE")) && (gameData.getWhiteUsername()!=null)))){
            throw new RuntimeException("Error: already taken");
        }else{
            if(clientColor.equals("WHITE") && gameData.getWhiteUsername()==null){
                gameData.setWhiteUsername(userAuthData.getUsername());
                return true;
            } else if(clientColor.equals("BLACK") && gameData.getBlackUsername()==null){
                gameData.setBlackUsername(userAuthData.getUsername());
                return true;
            }else{
                throw new RuntimeException("Error: bad request");
            }
        }
    }
}
