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

    public HttpURLConnection restRequest(String urlString, RestMethod method, String token, String body) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod(method.toString());


        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        if(token != null){
            connection.addRequestProperty("Authorization", token);
        }

        if(body != null){
            connection.setRequestProperty("Content-Type", "application/json");
        }


//        connection.connect();

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

        String token = null;
        HttpURLConnection connection = null;

        try{

            String urlString = "http://localhost:" + String.valueOf(port) + "/user";

            Map<String, String> jsonData = new HashMap<>();
            jsonData.put("email", email);
            jsonData.put("password", password);
            jsonData.put("username", username);

            String jsonBody = gson.toJson(jsonData);

            System.out.println(jsonBody);

            connection = this.restRequest(urlString, RestMethod.POST, null, jsonBody);

            InputStream test2 = connection.getInputStream();
            InputStreamReader test = new InputStreamReader(test2);
            BufferedReader in = new BufferedReader(test);
            String inputLine;
            StringBuilder responseBody = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = gson.fromJson(responseBody.toString(), JsonObject.class);

            token = jsonResponse.get("token").getAsString();

        }catch(IOException e){
            System.out.println("error with register");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            if(connection!= null){
                connection.disconnect();
            }

        }

        return token;
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
