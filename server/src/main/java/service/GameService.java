package service;

import chess.ChessGame;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;

public class GameService {
    private MemoryGameDAO memoryGameDAO;
    private MemoryAuthDAO memoryAuthDAO;

    public GameService() {
        this.memoryGameDAO = new MemoryGameDAO();
        this.memoryAuthDAO = new MemoryAuthDAO();
    }

    public GameData[] getGame(String authToken){
        if(this.memoryAuthDAO.getAuth(authToken) != null){
            return this.memoryGameDAO.listGames();
        }else{
            return null;
        }
    }

    public int createGame(String authToken, String gameName){
        if(this.memoryAuthDAO.getAuth(authToken) != null){
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


}
