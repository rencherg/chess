package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {

    public JoinPlayer(String authToken, ChessGame.TeamColor playerColor, int gameID) {
        super(authToken);
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    ChessGame.TeamColor playerColor;
    int gameID;

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameID() {
        return gameID;
    }
}
