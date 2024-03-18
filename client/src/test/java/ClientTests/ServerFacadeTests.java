package ClientTests;

import ServerConnection.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacadeTests {

    private static final int PORT = 8080;

    ServerFacade serverFacade = new ServerFacade(PORT);

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

    @Test
    @DisplayName("register positive")
    public void registerPositiveTest() {
        String token = this.serverFacade.register("mr jones", "password", "rencher.grant@gmail.com");
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Logout positive")
    public void logoutPositiveTest() {
        String token = this.serverFacade.register("big frank", "password", "rencher.grant@gmail.com");
        this.serverFacade.logout(token);
    }

}
