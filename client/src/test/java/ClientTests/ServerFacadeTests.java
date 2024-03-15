package ClientTests;

import org.junit.jupiter.api.*;
import server.Server;

public class ServerFacadeTests {

    ServerFacade serverFacade = new ServerFacade();

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void sampleTest() {
        ServerFacade
    }

}
