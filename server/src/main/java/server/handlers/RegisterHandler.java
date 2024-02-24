package server.handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RegisterHandler {

    private UserService userService = new UserService();
    private Gson gson = new Gson();
    private Map<String, String> responseMap = new HashMap<>();

    public String handleRequest(Request req, Response res) {

        String gsonString = "";
        UserData userData = gson.fromJson(req.body(), UserData.class);

        //Change this to input an object instead of individual values
        AuthData authData = this.userService.register(userData.getUsername(), userData.getPassword(), userData.getEmail());

        if(authData != null){
            res.status(200);
            responseMap.put("authToken", authData.getAuthToken());
            responseMap.put("username", authData.getUsername());
        }else{
            res.status(400);
            responseMap.put("message", "Dread Pirate Roberts");
        }

        gsonString = gson.toJson(responseMap);
        return gsonString;

    }
}
