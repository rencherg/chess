package ServerConnection;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ServerIntegration {

    private String port;
    Gson gson = new Gson();

    public ServerIntegration(String port) {
        this.port = port;
    }

    public enum RestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    public void clearDb() {
        String urlString = "http://localhost:" + this.port + "/db";

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");

            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String register(String username, String password, String email) {

        String urlString = "http://localhost:" + this.port + "/user";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + email + "\"}";
        String token;

        try {

            JsonObject jsonObject = handleRequestDetails(urlString, jsonBody, null);

            token = jsonObject.get("authToken").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Register failed");
        }

        return token;
    }

    public String login(String username, String password) {
        String urlString = "http://localhost:" + this.port + "/session";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        String token;

        try {

            JsonObject jsonObject = handleRequestDetails(urlString, jsonBody, null);

            token = jsonObject.get("authToken").getAsString();

        } catch (Exception e) {
            throw new RuntimeException("login failed");
        }

        return token;
    }

    private JsonObject handleRequestDetails(String urlString, String jsonBody, String authToken) throws IOException {

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");

        connection.setRequestProperty("Content-Type", "application/json");
        if(authToken != null){
            connection.addRequestProperty("Authorization", authToken);
        }

        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if (responseCode > 399){
            throw new RuntimeException();
        }

        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        }

        String jsonString = responseBody.toString();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        connection.disconnect();

        return jsonObject;
    }

    public void logout(String authToken) {
        String urlString = "http://localhost:" + this.port + "/session";

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", authToken);

            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            if (responseCode > 399){
                throw new RuntimeException();
            }

            connection.disconnect();

        } catch (Exception e) {
            throw new RuntimeException("logout failed");
        }
    }

    public int createGame(String authToken, String gameName) {
        String urlString = "http://localhost:" + this.port + "/game";
        String jsonBody = "{\"gameName\":\"" + gameName + "\"}";
        int gameId = -1;

        try {

            JsonObject jsonObject = handleRequestDetails(urlString, jsonBody, authToken);

            gameId = jsonObject.get("gameID").getAsInt();

        } catch (Exception e) {
            throw new RuntimeException("create game failed");
        }

        return gameId;
    }

    public ChessGame[] listGames(String authToken) {

        ChessGame[] gameList = null;

        String urlString = "http://localhost:" + this.port + "/game";

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            connection.addRequestProperty("Authorization", authToken);

            connection.setDoOutput(true);

            StringBuilder responseBody = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            }

            int responseCode = connection.getResponseCode();

            if (responseCode > 399){
                throw new RuntimeException();
            }

            String jsonString = responseBody.toString();

            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            JsonElement jsonElement = jsonObject.get("games");
            JsonArray responseArray = jsonElement.getAsJsonArray();

            gameList = new ChessGame[responseArray.size()];

            Iterator<JsonElement> myIterator = responseArray.iterator();

            int index = 0;

            while(myIterator.hasNext()){
                JsonObject chessGameElement = myIterator.next().getAsJsonObject();
                gameList[index] = gson.fromJson(chessGameElement, ChessGame.class);
                index++;
            }

            connection.disconnect();

        } catch (Exception e) {
            throw new RuntimeException("list games failed");
        }

        return gameList;
    }

    public void joinGame(String authToken, String clientColor, int gameID){

        String urlString = "http://localhost:" + this.port + "/game";
        String jsonBody;
        if(clientColor == null){
            jsonBody = "{\"gameID\":\"" + String.valueOf(gameID) + "\"}";
        }else{
            jsonBody = "{\"playerColor\":\"" + clientColor + "\",\"gameID\":\"" + String.valueOf(gameID) + "\"}";
        }

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("PUT");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", authToken);

            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            if (responseCode > 399){
                throw new RuntimeException();
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("join game failed");
        }
    }
}
