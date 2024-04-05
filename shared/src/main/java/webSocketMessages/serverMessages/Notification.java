package webSocketMessages.serverMessages;

public class Notification extends ServerMessage{

    public Notification(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    String message;
}
