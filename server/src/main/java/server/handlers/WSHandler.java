package server.handlers;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.util.concurrent.CopyOnWriteArrayList;

@WebSocket
public class WSHandler {

    // List to store connected sessions
    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        // Add the new session to the list of sessions
        sessions.add(session);
        System.out.println("WebSocket connected: " + session.getRemoteAddress());
    }

//    @OnWebSocketMessage
//    public void onMessage(Session session, String message) throws Exception {
//        // Handle incoming messages
//        System.out.println("Received message: " + message);
//        // Example: Broadcast the message to all connected users
//        broadcast("Echo: " + message);
//    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s", message);
        session.getRemote().sendString("WebSocket response: " + message);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        // Remove the closed session from the list of sessions
        sessions.remove(session);
        System.out.println("WebSocket closed: " + statusCode + " - " + reason);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket error: " + throwable.getMessage());
    }

    // Method to broadcast a message to all connected users
    private static void broadcast(String message) {
        for (Session session : sessions) {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                System.err.println("Error broadcasting message: " + e.getMessage());
            }
        }
    }
}
