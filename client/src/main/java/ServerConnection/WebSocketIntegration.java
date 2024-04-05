package ServerConnection;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebSocketIntegration extends Endpoint {

    public Session session;

    public WebSocketIntegration(String port, WebSocketObserver observer) {
        try{
            URI uri = new URI("ws://localhost:" + port + "/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {

                public void onMessage(String message) {
                    observer.onMessageReceived(message);
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
