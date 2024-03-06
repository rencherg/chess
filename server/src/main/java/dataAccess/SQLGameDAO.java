package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGameDAO implements GameDAO{

    Gson gson = new Gson();
    //String jsonString = gson.toJson(person);

    public GameData createGame(ChessGame game, String blackUsername, String whiteUsername, String gameName) throws SQLException {
        GameData gameData = new GameData(this.getUniqueID(), blackUsername, whiteUsername, gameName, game);
        TempDB.gameSet.add(gameData);
        return gameData;
    }

    public GameData getGame(int gameID) throws SQLException {

        GameData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM game_data WHERE game_id = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, String.valueOf(gameID));
            resultSet = myPreparedStatement.executeQuery();

            // Step 5: Process the result
            while (resultSet.next()) {
                ChessGame game = gson.fromJson(resultSet.getString("game_data"), ChessGame.class);
                foundData = new GameData(resultSet.getInt("id"), resultSet.getString("black_username"), resultSet.getString("white_username"), resultSet.getString("game_name"), game);
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                resultSet.close();
            }
            myPreparedStatement.close();
            myConnection.close();

            return foundData;
        }

    }

    public GameData[] listGames() throws SQLException {
        GameData gameDataArray[];

        GameData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;

//        try {
//
//            myConnection = DatabaseManager.getConnection();
//            String sqlQuery = "SELECT * FROM game_data WHERE game_id = ?;";
//            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
//            myPreparedStatement.setString(1, String.valueOf(gameID));
//            resultSet = myPreparedStatement.executeQuery();
//
//            // Step 5: Process the result
//            while (resultSet.next()) {
//                ChessGame game = gson.fromJson(resultSet.getString("game_data"), ChessGame.class);
//                foundData = new GameData(resultSet.getInt("id"), resultSet.getString("black_username"), resultSet.getString("white_username"), resultSet.getString("game_name"), game);
//            }
//
//        } catch (SQLException | DataAccessException e) {
//            e.printStackTrace();
//        } finally{
//            if(resultSet != null){
//                resultSet.close();
//            }
//            myPreparedStatement.close();
//            myConnection.close();
//
//            return foundData;
//        }

//        return gameDataArray;
        return null;
    }

    public GameData updateGame(ChessGame game, int gameID) throws SQLException {
        GameData gameData = this.getGame(gameID);
        gameData.setGame(game);
        return gameData;
    }
}
