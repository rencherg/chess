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

public class LoginHandler extends ParentHandler {

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            JsonObject jobj = gson.fromJson(req.body(), JsonObject.class);
            String username = jobj.get("username").getAsString();
            String password = jobj.get("password").getAsString();

            AuthData authData = this.userService.login(username, password);

            if(authData != null){
                res.status(200);
                responseMap.put("authToken", authData.getAuthToken());
                responseMap.put("username", authData.getUsername());
            }else{
                res.status(400);
                responseMap.put("message", "Error: bad request");
            }
        }
        catch (RuntimeException exception){
            this.parseException(exception, res);
        }

        gsonString = gson.toJson(responseMap);
        return gsonString;

    }
}
