package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{

    public Resign(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        this.commandType = CommandType.RESIGN;
    }

    int gameID;

    public int getGameID() {
        return gameID;
    }
}
