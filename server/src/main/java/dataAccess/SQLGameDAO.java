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

    public GameData createGame(ChessGame game, String blackUsername, String whiteUsername, String gameName) throws SQLException {

        GameData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;
        String jsonString = gson.toJson(game);

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "INSERT INTO game_data\n" +
                    "(\n" +
                    "    white_username,\n" +
                    "    black_username,\n" +
                    "    game_name,\n" +
                    "    game_data\n" +
                    ")\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?);";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, whiteUsername);
            myPreparedStatement.setString(2, blackUsername);
            myPreparedStatement.setString(3, gameName);
            myPreparedStatement.setString(4, jsonString);
            resultSet = myPreparedStatement.executeQuery();

            while (resultSet.next()) {
                ChessGame gameFromDB = gson.fromJson(resultSet.getString("game_data"), ChessGame.class);
                foundData = new GameData(resultSet.getInt("id"), resultSet.getString("black_username"), resultSet.getString("white_username"), resultSet.getString("game_name"), gameFromDB);
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
        }

        return foundData;

    }

    public GameData[] listGames() throws SQLException {
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;
        int rowCount = DatabaseManager.rowCount("game_data");
        GameData gameDataArray[] = new GameData[rowCount];
        int currentIndex = 0;

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "SELECT * FROM game_data;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            resultSet = myPreparedStatement.executeQuery();

            while (resultSet.next()) {
                ChessGame game = gson.fromJson(resultSet.getString("game_data"), ChessGame.class);
                gameDataArray[currentIndex] = new GameData(resultSet.getInt("id"), resultSet.getString("black_username"), resultSet.getString("white_username"), resultSet.getString("game_name"), game);
                currentIndex++;
            }

        } catch (SQLException | DataAccessException e) {
            e.printStackTrace();
        } finally{
            if(resultSet != null){
                resultSet.close();
            }
            myPreparedStatement.close();
            myConnection.close();
        }

        return gameDataArray;
    }

    public GameData updateGame(ChessGame game, int gameID) throws SQLException {

        GameData foundData = null;
        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet resultSet = null;
        String jsonString = gson.toJson(game);

        try {

            myConnection = DatabaseManager.getConnection();
            String sqlQuery = "UPDATE game_data\n" +
                    "    SET game_data = ?\n" +
                    "    WHERE id = ?;";
            myPreparedStatement = myConnection.prepareStatement(sqlQuery);
            myPreparedStatement.setString(1, sqlQuery);
            myPreparedStatement.setString(2, String.valueOf(gameID));
            resultSet = myPreparedStatement.executeQuery();

            while (resultSet.next()) {
                ChessGame gameFromDB = gson.fromJson(resultSet.getString("game_data"), ChessGame.class);
                foundData = new GameData(resultSet.getInt("id"), resultSet.getString("black_username"), resultSet.getString("white_username"), resultSet.getString("game_name"), gameFromDB);
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
}
