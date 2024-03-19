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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerIntegration {

    private int port;
    Gson gson = new Gson();

    public ServerIntegration(int port) {
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

    public void clearDb() {
        String urlString = "http://localhost:8080/db";

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

        String urlString = "http://localhost:8080/user";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"email\":\"" + email + "\"}";
        String token = null;

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

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

    public String login(String username, String password) {
        String urlString = "http://localhost:8080/session";
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";
        String token = null;

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

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

    public void logout(String authToken) {
        String urlString = "http://localhost:8080/session";

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", authToken);

            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int createGame(String authToken, String gameName) {
        String urlString = "http://localhost:8080/game";
        String jsonBody = "{\"gameName\":\"" + gameName + "\"}";
        int gameId = -1;

        try {

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Authorization", authToken);

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

            gameId = jsonObject.get("gameID").getAsInt();

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameId;
    }

    public ChessGame[] listGames(String authToken) {

        ChessGame[] gameList = null;

        String urlString = "http://localhost:8080/game";

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

            String jsonString = responseBody.toString();

            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

            System.out.println(jsonObject);

            JsonElement jsonObject2 = jsonObject.get("games");

            ChessGame games = gson.fromJson(jsonString, ChessGame.class);

            // Access the deserialized objects
//            for (ChessGame chessGame : jsonObject2) {
//                System.out.println(games);
//            }



//            List<ChessGame> outputList = gson.fromJson(jsonString, ArrayList.class);

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameList;
    }

    public void joinGame(String authToken, String clientColor, int gameID){

    }
}
