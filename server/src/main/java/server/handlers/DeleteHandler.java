package server.handlers;

import service.DeleteService;
import spark.Request;
import spark.Response;

public class DeleteHandler extends ParentHandler {
    private DeleteService deleteService = new DeleteService();

    public String handleRequest(Request req, Response res) {

        String gsonString = "";

        try{

            deleteService.clear();

        }
        catch (RuntimeException exception){
            responseMap.put("message", "Error: bad request");
            res.status(400);
        }

        gsonString = gson.toJson(responseMap);

        return gsonString;

    }
}