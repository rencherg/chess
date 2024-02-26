package server.handlers;

import model.GameData;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class ListGameHandler extends ParentHandler{

    private Map<String, GameData[]> gameResponseMap = new HashMap<>();

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            String authToken = req.headers("authorization");

            GameData[] gameData = this.gameService.getGame(authToken);

            gameResponseMap.put("games", gameData);

            gsonString = gson.toJson(gameResponseMap);
        }
        catch (RuntimeException exception){
            this.parseException(exception, res);
            gsonString = gson.toJson(responseMap);
        }

        return gsonString;

    }
}
