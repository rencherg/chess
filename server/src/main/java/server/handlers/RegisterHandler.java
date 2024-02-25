package server.handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class RegisterHandler {

    private UserService userService = new UserService();
    private Gson gson = new Gson();
    private Map<String, String> responseMap = new HashMap<>();
//    private Set<String> errorCodeSet = new HashSet<>();

    public String handleRequest(Request req, Response res) {

//        this.errorCodeSet.add("Error: unauthorized");
//        this.errorCodeSet.add("Error: already taken");

        String gsonString = "";
//        UserData userData = gson.fromJson(req.body(), UserData.class);

        //Change this to input an object instead of individual values
        try{

            UserData userData = gson.fromJson(req.body(), UserData.class);
            AuthData authData = this.userService.register(userData);

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

        gsonString = gson.toJson(responseMap);
        return gsonString;

    }
}
