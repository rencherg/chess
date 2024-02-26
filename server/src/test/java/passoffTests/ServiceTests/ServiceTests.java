package passoffTests.ServiceTests;

//Non HTTP Service tests go here
import chess.ChessGame;
import dataAccess.AuthDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.DeleteService;
import service.GameService;
import service.UserService;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;

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
        Assertions.assertNotNull(this.userService.register(new UserData("cougarboy123", "password", "rencher.grant@gmail.com")));
    }

    @Test
    @Order(3)
    @DisplayName("register negative")
    public void registerTestNegative() throws Exception {
        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.userService.register(new UserData("", "", ""));
        });

        String expectedMessage = "Error: bad request";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("login positive")
    public void loginTestPositive() throws Exception {
        AuthData authData = this.userService.register(new UserData("fmulder", "trustno1", "f.mulder@fbi.gov"));
        this.userService.logout(authData.getAuthToken());
        Assertions.assertNotNull(this.userService.login("fmulder", "trustno1"));
    }

    @Test
    @Order(5)
    @DisplayName("login negative")
    public void loginTestNegative() throws Exception {
        AuthData authData = this.userService.register(new UserData("dscully", "trustno1", "f.mulder@fbi.gov"));
        this.userService.logout(authData.getAuthToken());
        authData = this.userService.register(new UserData("rencherg", "password", "rencher.grant@gmail.com"));
        this.userService.logout(authData.getAuthToken());

        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            this.userService.login("rencherg", "neverused");
        });

        String expectedMessage = "Error: unauthorized";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("logout positive")
    public void logoutTestPositive() throws Exception {
        AuthData authData = this.userService.register(new UserData("wskinner", "trustno1", "f.mulder@fbi.gov"));
        Assertions.assertTrue(this.userService.logout(authData.getAuthToken()));
    }

    @Test
    @Order(7)
    @DisplayName("logout negative")
    public void logoutTestNegative() throws Exception {
        AuthData authData = this.userService.register(new UserData("dreadpirateroberts", "trustno1", "f.mulder@fbi.gov"));
        ;
        Exception thrownException = assertThrows(RuntimeException.class, () -> {
            Assertions.assertFalse(this.userService.logout("invalid token"));
        });

        String expectedMessage = "Error: unauthorized";
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());

    }

    @Test
    @Order(8)
    @DisplayName("createGame positive")
    public void createGamePositive() throws Exception {
        AuthData authData = this.userService.register(new UserData("johnpauljones", "trustno1", "f.mulder@fbi.gov"));
        int id = this.gameService.createGame(authData.getAuthToken(), "my game");
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
        AuthData authData = this.userService.register(new UserData("akrycek", "trustno1", "f.mulder@fbi.gov"));
        int id = this.gameService.createGame(authData.getAuthToken(), "my game");
        Assertions.assertNotNull(this.gameService.joinGame(authData.getAuthToken(), ChessGame.TeamColor.WHITE, id));
    }

    @Test
    @Order(11)
    @DisplayName("joinGame negative")
    public void joinGameNegative() throws Exception {
        AuthData authData1 = this.userService.register(new UserData("mlg", "trustno1", "f.mulder@fbi.gov"));
        AuthData authData2 = this.userService.register(new UserData("grantacus_", "password", "rencher.grant@gmail.com"));
        int id = this.gameService.createGame(authData1.getAuthToken(), "my game");
        this.gameService.joinGame(authData1.getAuthToken(), ChessGame.TeamColor.WHITE, id);
        Assertions.assertFalse(this.gameService.joinGame(authData2.getAuthToken(), ChessGame.TeamColor.WHITE, id));
    }

    @Test
    @Order(12)
    @DisplayName("getGame positive")
    public void getGamePositive() throws Exception {
        AuthData authData = this.userService.register(new UserData("byu", "trustno1", "f.mulder@fbi.gov"));
        int id1 = this.gameService.createGame(authData.getAuthToken(), "my game 1");
        int id2 = this.gameService.createGame(authData.getAuthToken(), "my game 2");
        int id3 = this.gameService.createGame(authData.getAuthToken(), "my game 3");
        GameData[] gameData = this.gameService.getGame(authData.getAuthToken());
        Assertions.assertEquals(3, gameData.length);
    }

    @Test
    @Order(13)
    @DisplayName("getGame negative")
    public void getGameNegative() throws Exception {
        AuthData authData = this.userService.register(new UserData("cowboy", "trustno1", "f.mulder@fbi.gov"));
        int id2 = this.gameService.createGame(authData.getAuthToken(), "my game 1");
        Assertions.assertNull(this.gameService.getGame("invalid token"));
    }
}
