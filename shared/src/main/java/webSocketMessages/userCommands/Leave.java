package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{

    public Leave(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.LEAVE;
    }

    int gameID;

    public int getGameID() {
        return gameID;
    }
}
