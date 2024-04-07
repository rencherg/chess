package webSocketMessages.serverMessages;

public class Notification extends ServerMessage{

    public Notification(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    String message;
}
