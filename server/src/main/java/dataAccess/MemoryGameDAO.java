package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class MemoryGameDAO implements GameDAO {

//    Set<GameData> gameSet = new HashSet();

    public MemoryGameDAO(){

    }

    private int getUniqueID(){

        int id = 0;

        boolean foundValidToken = false;

        while(foundValidToken == false){
            id = ThreadLocalRandom.current().nextInt(1000, 10000);
            if(this.getGame(id) == null){
                foundValidToken = true;
            }
        }

        return id;
    }

    public GameData createGame(ChessGame game, String blackUsername, String whiteUsername, String gameName){
        GameData gameData = new GameData(this.getUniqueID(), blackUsername, whiteUsername, gameName, game);
        TempDB.gameSet.add(gameData);
        return gameData;
    }

    public GameData getGame(int gameID){

        Iterator<GameData> dataIterator = TempDB.gameSet.iterator();

        GameData iteratorData;

        while (dataIterator.hasNext()) {

            iteratorData = dataIterator.next();

            if(iteratorData.getGameID() == gameID){
                return iteratorData;
            }
        }
        return null;

    }

    public GameData[] listGames(){
        GameData gameDataArray[] = new GameData[TempDB.gameSet.size()];

        int i = 0;
        for (GameData game : TempDB.gameSet)
            gameDataArray[i++] = game;

        return gameDataArray;
    }

    public GameData updateGame(ChessGame game, int gameID){
        GameData gameData = this.getGame(gameID);
        gameData.setGame(game);
        return gameData;
    }
}
