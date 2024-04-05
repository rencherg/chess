package webSocketMessages.serverMessages;

public class Error extends ServerMessage{

    public Error(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    String errorMessage;
}
