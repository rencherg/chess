package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

abstract public class ParentHandler {
    protected UserService userService = new UserService();
    protected Gson gson = new Gson();
    protected Map<String, String> responseMap = new HashMap<>();

    protected void parseException(RuntimeException exception, Response res){

        if(exception.getMessage().equals("Error: unauthorized")){
            responseMap.put("message", "Error: unauthorized");
            res.status(401);
        } else if(exception.getMessage().equals("Error: already taken")){
            responseMap.put("message", "Error: already taken");
            res.status(403);
        } else{
            responseMap.put("message", "Error: bad request");
            res.status(400);
        }
    }

    public abstract String handleRequest(Request req, Response res);
}
