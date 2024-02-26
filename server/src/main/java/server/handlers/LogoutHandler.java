package server.handlers;

import spark.Request;
import spark.Response;

public class LogoutHandler extends ParentHandler {

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            String authToken = req.headers("authorization");

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
