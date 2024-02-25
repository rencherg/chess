package server;

import server.handlers.DeleteHandler;
import server.handlers.LoginHandler;
import server.handlers.LogoutHandler;
import server.handlers.RegisterHandler;
import spark.*;

public class Server {

    //TODO:
    //1 Fix bugs in service classes(There may be a security vulnerability)
    //2 Make handlers Inherit
    //3 Game Endpoints
    //4 Clean up code

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.post("/user", (req, res) ->
                (new RegisterHandler()).handleRequest(req,
                        res));

        Spark.post("/session", (req, res)->
                (new LoginHandler()).handleRequest(req,
                res));

        Spark.delete("/session", (req, res)->
                (new LogoutHandler()).handleRequest(req,
                res));

        Spark.post("/game", (req, res)->"Post Game");

        Spark.get("/game", (req, res)->"Get Game");

        Spark.put("/game", (req, res)->"Put Game");

        Spark.delete("/db", (req, res)->
                (new DeleteHandler()).handleRequest(req,
                        res));

        Spark.get("/hello", (req,res)->"Hello World Grant");


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
