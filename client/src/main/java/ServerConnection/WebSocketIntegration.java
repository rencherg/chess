package ServerConnection;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebSocketIntegration extends Endpoint {

//    public static void main(String[] args) throws Exception {
//        var ws = new WebSocketIntegration();
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter a message you want to echo");
//        while (true) ws.send(scanner.nextLine());
//    }

    public Session session;

    public WebSocketIntegration(String port) {
        try{
            URI uri = new URI("ws://localhost:" + port + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {

                public void onMessage(String message) {
                    System.out.println(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
