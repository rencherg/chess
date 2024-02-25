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

public class LogoutHandler extends ParentHandler {

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
            this.parseException(exception, res);
        }

        gsonString = gson.toJson(responseMap);

        return gsonString;

    }
}
