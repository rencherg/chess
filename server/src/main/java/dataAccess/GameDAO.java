package dataAccess;

import chess.ChessGame;
import model.GameData;

public interface GameDAO {

    public GameData createGame(ChessGame game, String blackUsername, String whiteUsername, String gameName);

    public GameData getGame(int gameID);

    public GameData[] listGames();

    public GameData updateGame(ChessGame game, int gameID);

}
