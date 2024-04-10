import ServerConnection.ServerIntegration;
import ServerConnection.WebSocketIntegration;
import chess.*;
import ui.Menu;
import webSocketMessages.userCommands.*;
import ServerConnection.ServerIntegration;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

public class Main {

    static final String PORT = "8080";

    public static void main(String[] args) throws InvalidMoveException {

        ServerIntegration testServerIntegration = new ServerIntegration("8080");

        Menu menu = new Menu(PORT);

        // [B@368f3994

        // 506

//        System.out.println(testServerIntegration.register("grant", "pass", "e"));
//        System.out.println(testServerIntegration.createGame("[B@368f3994", "jellyfish"));
//        testServerIntegration.joinGame("[B@368f3994", "Black", 506);
//        testServerIntegration.joinGame("[B@368f3994", "White", 506);

//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", ChessGame.TeamColor.BLACK, 506);
//        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", BLACK, 506);
//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myLeave = new Leave("[B@368f3994", 590);
//        UserGameCommand myLeave = new Leave("[B@368f3994", 506);
//        UserGameCommand myResign = new Resign("[B@368f3994", 506);
//        UserGameCommand myLeave = new Leave("token", 1234);

//        menu.sendWebSocketMessage(myJoinObserver);
//        menu.sendWebSocketMessage(myResign);
//        menu.sendWebSocketMessage(myLeave);

        menu.runMenu();

    }
}