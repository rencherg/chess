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

    @Test
    @DisplayName("dpr")
    public void registerCallTest() {
        String urlString = "http://localhost:8080/user";
        String jsonBody = "{\"username\":\"big boy\",\"password\":\"password\",\"email\":\"rencher.grant@gmail.com\"}";

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output
            connection.setDoOutput(true);

            // Write the request body to the connection
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("register positive")
    public void sampleTest() {
        String token = this.serverFacade.register("big boy", "password", "rencher.grant@gmail.com");
        Assertions.assertNull(token);
    }

}
