package passoffTests.ServiceTests;

//Non HTTP Service tests go here
import chess.ChessGame;
import model.GameData;
import org.junit.jupiter.api.*;
import service.DeleteService;
import service.GameService;
import service.UserService;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceTests {

    private DeleteService deleteService = new DeleteService();
    private GameService gameService = new GameService();
    private UserService userService = new UserService();

    @Test
    @Order(1)
    @DisplayName("Delete positive")
    public void deleteTest() throws Exception {
        Assertions.assertTrue(this.deleteService.clear());
    }

    @Test
    @Order(2)
    @DisplayName("register positive")
    public void registerTestPositive() throws Exception {
        Assertions.assertNotNull(this.userService.register("rencherg", "password", "rencher.grant@gmail.com"));
    }

    @Test
    @Order(3)
    @DisplayName("register negative")
    public void registerTestNegative() throws Exception {
        Assertions.assertNull(this.userService.register("", "", ""));
    }

    @Test
    @Order(4)
    @DisplayName("login positive")
    public void loginTestPositive() throws Exception {
        String token = this.userService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        this.userService.logout(token);
        Assertions.assertNotNull(this.userService.login("fmulder", "trustno1"));
    }

    @Test
    @Order(5)
    @DisplayName("login negative")
    public void loginTestNegative() throws Exception {
        String token = this.userService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        this.userService.logout(token);
        token = this.userService.register("rencherg", "password", "f.mulder@fbi.gov");
        this.userService.logout(token);
        Assertions.assertNull(this.userService.login("rencherg", "sample"));
    }

    @Test
    @Order(6)
    @DisplayName("logout positive")
    public void logoutTestPositive() throws Exception {
        String token = this.userService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        Assertions.assertTrue(this.userService.logout(token));
    }

    @Test
    @Order(7)
    @DisplayName("logout negative")
    public void logoutTestNegative() throws Exception {
        String token = this.userService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        Assertions.assertFalse(this.userService.logout("sample token"));
    }

    @Test
    @Order(8)
    @DisplayName("createGame positive")
    public void createGamePositive() throws Exception {
        String token = this.gameService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        int id = this.gameService.createGame(token, "my game");
        Assertions.assertNotEquals(-1, id);
    }

    @Test
    @Order(9)
    @DisplayName("createGame negative")
    public void createGameNegative() throws Exception {
        int id = this.gameService.createGame("invalid token", "my game");
        Assertions.assertEquals(-1, id);
    }

    @Test
    @Order(10)
    @DisplayName("joinGame positive")
    public void joinGamePositive() throws Exception {
        String token = this.gameService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        int id = this.gameService.createGame(token, "my game");
        Assertions.assertNotNull(this.gameService.joinGame(token, ChessGame.TeamColor.WHITE, id));
    }

    @Test
    @Order(11)
    @DisplayName("joinGame negative")
    public void joinGameNegative() throws Exception {
        String token1 = this.gameService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        String token2 = this.gameService.register("rencherg", "password", "rencher.grant@gmail.com");
        int id = this.gameService.createGame(token1, "my game");
        this.gameService.joinGame(token1, ChessGame.TeamColor.WHITE, id);
        Assertions.assertFalse(this.gameService.joinGame(token2, ChessGame.TeamColor.WHITE, id));
    }

    @Test
    @Order(12)
    @DisplayName("getGame positive")
    public void getGamePositive() throws Exception {
        String token = this.gameService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        int id1 = this.gameService.createGame(token, "my game 1");
        int id2 = this.gameService.createGame(token, "my game 2");
        int id3 = this.gameService.createGame(token, "my game 3");
        GameData[] gameData = this.gameService.getGame(token);
        Assertions.assertEquals(3, gameData.length);
    }

    @Test
    @Order(13)
    @DisplayName("getGame negative")
    public void getGameNegative() throws Exception {
        String token = this.gameService.register("fmulder", "trustno1", "f.mulder@fbi.gov");
        int id2 = this.gameService.createGame(token, "my game 1");
        Assertions.assertNull(this.gameService.getGame("invalid token"));
    }
}
