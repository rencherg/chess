import chess.*;
import ui.Menu;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.Leave;
import webSocketMessages.userCommands.UserGameCommand;

public class Main {

    static final String PORT = "8080";

    public static void main(String[] args) throws InvalidMoveException {

        Menu menu = new Menu(PORT);

        UserGameCommand myJoinObserver = new JoinObserver("token", 1234);
        UserGameCommand myLeave = new Leave("token", 1234);

        menu.sendWebSocketMessage(myJoinObserver);
        menu.sendWebSocketMessage(myLeave);

        menu.runMenu();

    }
}