package clientTests;

import ServerConnection.ServerFacade;
import chess.ChessGame;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServerFacadeTests {

    private static final int PORT = 0;

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
    @DisplayName("Clear DB")
    public void clearDb(){
        this.serverFacade.clearDb();
    }

    @Test
    @DisplayName("Clear DB Positive")
    public void clearDbPositive(){

        String token = this.serverFacade.register("fmulder", "TrustNo1", "fmulder@fbi.gov");

        this.serverFacade.clearDb();

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.listGames(token);
        });

        String expectedMessage = "list games failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    @DisplayName("register positive")
    public void registerPositiveTest() {
        String token = this.serverFacade.register("fmulder", "TrustNo1", "fmulder@fbi.gov");
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("register negative")
    public void registerNegativeTest() {

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            String token = this.serverFacade.register("", "", "");
        });

        String expectedMessage = "Register failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    @DisplayName("Logout positive")
    public void logoutPositiveTest() {
        String token = this.serverFacade.register("scully", "TrustNo1", "dscully@fbi.gov");this.serverFacade.createGame(token, "my game");
        this.serverFacade.logout(token);

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.listGames(token);
        });

        String expectedMessage = "list games failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());

    }

    @Test
    @DisplayName("Logout negative")
    public void logoutNegativeTest() {

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.logout("random");
        });

        String expectedMessage = "logout failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
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
    @DisplayName("Login Negative")
    public void loginNegative(){
        String username = "skinner";
        String password = "TrustNo1";
        String email = "wskinner@fbi.gov";
        String token = this.serverFacade.register(username, password, email);
        this.serverFacade.logout(token);

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.login("badusername", "badpassword");
        });

        String expectedMessage = "login failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());

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
    @DisplayName("Create Game Negative")
    public void createGameNegative(){
        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.createGame("badtoken", "name");
        });

        String expectedMessage = "create game failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    @DisplayName("List Games Positive")
    public void listGamePositive(){
        String username = "rencherg";
        String password = "password";
        String email = "rencher.grant@gmail.com";
        String token = this.serverFacade.register(username, password, email);
        this.serverFacade.createGame(token, "my game");
        this.serverFacade.createGame(token, "my game 2");
        this.serverFacade.createGame(token, "my game 3");
        ChessGame[] gameList = this.serverFacade.listGames(token);
        Assertions.assertEquals(3, gameList.length);
    }

    @Test
    @DisplayName("List Games Negative")
    public void listGameNegative(){
        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.listGames("badtoken");
        });

        String expectedMessage = "list games failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
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
        this.serverFacade.joinGame(token, "BLACK", gameId1);

        ChessGame games[] = this.serverFacade.listGames(token);
        Assertions.assertEquals(username, games[0].getBlackUsername());
        Assertions.assertEquals(username, games[0].getWhiteUsername());

    }

    @Test
    @DisplayName("Join Game Negative")
    public void joinGameNegative(){
        String username = "rencherg";
        String password = "password";
        String email = "rencher.grant@gmail.com";
        String token = this.serverFacade.register(username, password, email);
        int gameId1 = this.serverFacade.createGame(token, "my game");
        this.serverFacade.logout(token);

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.serverFacade.joinGame(token, "WHITE", gameId1);
        });

        String expectedMessage = "join game failed";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

}