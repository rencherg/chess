package server.handlers;

import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

public class RegisterHandler extends ParentHandler {

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

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
            this.parseException(exception, res);
        }

        gsonString = gson.toJson(responseMap);
        return gsonString;

    }
}
