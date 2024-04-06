package ServerConnection;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class ServerFacade {

    ServerIntegration serverIntegration;
    private WebSocketIntegration webSocketIntegration;

    public ServerFacade(String port, WebSocketObserver observer){
        serverIntegration = new ServerIntegration(port);
        webSocketIntegration = new WebSocketIntegration(port, observer);
    }

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

    public void joinGame(String authToken, String clientColor, int gameID){
        serverIntegration.joinGame(authToken, clientColor, gameID);
    }

    public void clearDb(){
        serverIntegration.clearDb();
    }

    public void sendWebSocketMessage(UserGameCommand userGameCommand) throws Exception {
        this.webSocketIntegration.send(userGameCommand);
    }

}
