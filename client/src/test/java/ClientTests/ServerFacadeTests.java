package ClientTests;

import ServerConnection.ServerFacade;
import chess.ChessGame;
import org.junit.jupiter.api.*;
import server.Server;

public class ServerFacadeTests {

    private static final int PORT = 8080;

    ServerFacade serverFacade = new ServerFacade(String.valueOf(PORT));

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(PORT);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @AfterEach
    @Test
    public void clearDb(){
        this.serverFacade.clearDb();
    }

    @Test
    @DisplayName("register positive")
    public void registerPositiveTest() {
        String token = this.serverFacade.register("fmulder", "TrustNo1", "fmulder@fbi.gov");
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Logout positive")
    public void logoutPositiveTest() {
        String token = this.serverFacade.register("scully", "TrustNo1", "dscully@fbi.gov");
        this.serverFacade.logout(token);
    }

    @Test
    @DisplayName("Login Positive")
    public void loginPositive(){
        String username = "skinner";
        String password = "TrustNo1";
        String email = "wskinner@fbi.gov";
        String token = this.serverFacade.register(username, password, email);
        this.serverFacade.logout(token);
        token = this.serverFacade.login(username, password);
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Create Game Positive")
    public void createGamePositive(){
        String username = "rencherg";
        String password = "password";
        String email = "rencher.grant@gmail.com";
        String token = this.serverFacade.register(username, password, email);
        int gameId = this.serverFacade.createGame(token, "my game");
        Assertions.assertNotEquals(gameId, -1);
    }

    @Test
    @DisplayName("List Games Positive")
    public void listGamePositive(){
        String username = "rencherg";
        String password = "password";
        String email = "rencher.grant@gmail.com";
        String token = this.serverFacade.register(username, password, email);
        int gameId1 = this.serverFacade.createGame(token, "my game");
        int gameId2 = this.serverFacade.createGame(token, "my game 2");
        int gameId3 = this.serverFacade.createGame(token, "my game 3");
        ChessGame[] gameList = this.serverFacade.listGames(token);
        Assertions.assertEquals(3, gameList.length);
    }

    @Test
    @DisplayName("Join Game Positive")
    public void joinGamePositive(){
        String username = "rencherg";
        String password = "password";
        String email = "rencher.grant@gmail.com";
        String token = this.serverFacade.register(username, password, email);
        int gameId1 = this.serverFacade.createGame(token, "my game");
        this.serverFacade.joinGame(token, "WHITE", gameId1);
    }

}

//Todo:

//exception handling
//rest of tests
//front end stuff
//Port stuff