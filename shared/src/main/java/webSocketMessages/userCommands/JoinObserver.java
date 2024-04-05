package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{

    public JoinObserver(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_OBSERVER;
    }

    int gameID;

    public int getGameID() {
        return gameID;
    }
}
