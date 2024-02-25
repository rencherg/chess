package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LogoutHandler {

    private UserService userService = new UserService();
    private Gson gson = new Gson();
    private Map<String, String> responseMap = new HashMap<>();

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            String authToken = req.headers("authorization");

            System.out.println(authToken);

            boolean successfulLogout = this.userService.logout(authToken);

            if(successfulLogout){
                res.status(200);
            }
        }
        catch (RuntimeException exception){
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

//        if(!responseMap.isEmpty()){
//            gsonString = gson.toJson(responseMap);
//        }else{
//            gsonString = "{}";
//        }
        gsonString = gson.toJson(responseMap);

        return gsonString;

    }
}
