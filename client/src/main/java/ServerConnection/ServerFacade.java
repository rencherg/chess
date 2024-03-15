package ServerConnection;

import chess.ChessGame;

public class ServerFacade {

    ServerIntegration serverIntegration = new ServerIntegration();

    public String register(String username, String password, String email){

        return serverIntegration.register(username, password, email);
    }

    public String login(String username, String password){
        return serverIntegration.login(username, password);
    }

    public void logout(String authToken){
        serverIntegration.logout(authToken);
    }

    public int createGame(String authToken, String gameName){
        return serverIntegration.createGame(authToken, gameName);
    }

    public ChessGame[] listGames(String authToken){

        return serverIntegration.listGames(authToken);
    }

    public boolean joinGame(String authToken, String clientColor, int gameID){
        return serverIntegration.joinGame(authToken, clientColor, gameID);
    }

}
