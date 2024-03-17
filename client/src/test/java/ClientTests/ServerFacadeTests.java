package ClientTests;

import ServerConnection.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

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

//    @Test
//    @DisplayName("dpr")
//    public void registerCallTest() {
//        String urlString = "http://localhost:8080/user";
//        String jsonBody = "{\"username\":\"big boy\",\"password\":\"password\",\"email\":\"rencher.grant@gmail.com\"}";
//
//        try {
//            URL url = new URL(urlString);
//
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("POST");
//
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            connection.setDoOutput(true);
//
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = jsonBody.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//
//            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    @DisplayName("register positive")
    public void sampleTest() {
        String token = this.serverFacade.register("big mayor", "password", "rencher.grant@gmail.com");
        Assertions.assertNull(token);
    }

}
