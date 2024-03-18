package ServerConnection;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerIntegration {

    private int port;
    Gson gson = new Gson();

    public ServerIntegration(int port){
        this.port = port;
    }

    public enum RestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

//    public HttpURLConnection restRequest(String urlString, RestMethod method, String token, String body) throws IOException {
//        URL url = new URL(urlString);
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setReadTimeout(5000);
//        connection.setRequestMethod(method.toString());
//
//
//        // Set HTTP request headers, if necessary
//        // connection.addRequestProperty("Accept", "text/html");
//        if(token != null){
//            connection.addRequestProperty("Authorization", token);
//        }
//
//        if(body != null){
//            connection.setRequestProperty("Content-Type", "application/json");
//        }
//
//
////        connection.connect();
//
//        // Enable output and set request body
//        if(body != null){
//            connection.setDoOutput(true);
//            String requestBody = "{\"key\":\"value\"}";
//
//            // Write the request body to the connection
//            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes(requestBody);
//            outputStream.flush();
//            outputStream.close();
//        }
//
//        return connection;
//    }

    public String register(String username, String password, String email){

        String urlString = "http://localhost:8080/user";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + email + "\"}";
        String token = null;

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", token);

            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            StringBuilder responseBody = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            }

            String jsonString = responseBody.toString();

            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            token = jsonObject.get("authToken").getAsString();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    public String login(String username, String password){
        return "token";
    }

    public void logout(String authToken){
        String urlString = "http://localhost:8080/session";;

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
