package webSocketMessages.serverMessages;

public class ServerError extends ServerMessage{

    public ServerError(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    String errorMessage;
}
