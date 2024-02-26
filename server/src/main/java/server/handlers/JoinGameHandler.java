package server.handlers;

import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends ParentHandler{

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            String authToken = req.headers("authorization");

            JsonObject jobj = gson.fromJson(req.body(), JsonObject.class);
            String playerColor = "";
            if(jobj.get("playerColor") == null){
                playerColor = null;
            }else{
                playerColor = jobj.get("playerColor").getAsString();
            }
            int gameID = jobj.get("gameID").getAsInt();

            gameService.joinGame(authToken, playerColor, gameID);
        }
        catch (RuntimeException exception){
            this.parseException(exception, res);
        }

        gsonString = gson.toJson(responseMap);
        return gsonString;

    }
}
