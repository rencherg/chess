import ServerConnection.ServerIntegration;
import chess.*;
import ui.Menu;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.Leave;
import webSocketMessages.userCommands.UserGameCommand;
import ServerConnection.ServerIntegration;

import static chess.ChessGame.TeamColor.BLACK;

public class Main {

    static final String PORT = "8080";

    public static void main(String[] args) throws InvalidMoveException {

        ServerIntegration testServerIntegration = new ServerIntegration("8080");

        Menu menu = new Menu(PORT);

        // [B@368f3994

        // 506

//        System.out.println(testServerIntegration.register("grant", "pass", "e"));
//        System.out.println(testServerIntegration.createGame("[B@368f3994", "jellyfish"));
        testServerIntegration.joinGame("[B@368f3994", "Black", 506);

//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", ChessGame.TeamColor.BLACK, 506);
        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", BLACK, 506);
//        UserGameCommand myLeave = new Leave("token", 1234);

        menu.sendWebSocketMessage(myJoinPlayer);
//        menu.sendWebSocketMessage(myLeave);

        menu.runMenu();

    }
}