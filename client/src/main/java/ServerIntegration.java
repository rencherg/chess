import chess.ChessGame;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ServerIntegration {

    public enum RestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public HttpURLConnection restRequest(String urlString, RestMethod method, String token, String body) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod(method.toString());


        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        if(token != null){
            connection.addRequestProperty("Authorization", "fjaklc8sdfjklakl");
        }

        if(body != null){
            connection.setRequestProperty("Content-Type", "application/json");
        }


        connection.connect();

        // Enable output and set request body
        if(body != null){
            connection.setDoOutput(true);
            String requestBody = "{\"key\":\"value\"}";

            // Write the request body to the connection
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();
            outputStream.close();
        }

        return connection;
    }

    public String register(String username, String password, String email){

//        HttpURLConnection connection = restRequest();

        return "token";
    }

    public String login(String username, String password){
        return "token";
    }

    public void logout(String authToken){
    }

    public int createGame(String authToken, String gameName){
        //return game id
        return 0;
    }

    public ChessGame[] listGames(String authToken){

        ChessGame[] gameList = new ChessGame[1];

        return gameList;
    }

    public boolean joinGame(String authToken, String clientColor, int gameID){
        return true;
    }
}
