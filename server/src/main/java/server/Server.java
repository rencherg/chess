package server;

import server.handlers.LoginHandler;
import server.handlers.LogoutHandler;
import server.handlers.RegisterHandler;
import spark.*;

public class Server {

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

        Spark.delete("/db", (req, res)->"DB");

        Spark.get("/hello", (req,res)->"Hello World Grant");


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
