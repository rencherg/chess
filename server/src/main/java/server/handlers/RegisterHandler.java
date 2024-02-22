package server.handlers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    public Object handleRequest(Request req, Response res) {

        Gson gson = new Gson();

//        RegisterRequest request = (LoginRequest)gson.fromJson(reqData, LoginRequest.class);
//
//        LoginService service = new LoginService();
//        LoginResult result = service.login(request);
//
//        return gson.toJson(result);
        String message = "{" +
                "\"message\": \"Dread Pirate Roberts\""+
                "}";
        return gson.toJson(message);

    }
}
