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
//        testServerIntegration.joinGame("[B@368f3994", "Black", 507);
//        testServerIntegration.joinGame("[B@368f3994", "White", 507);

//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", WHITE, 507);
//        UserGameCommand myJoinPlayer = new JoinPlayer("[B@368f3994", BLACK, 506);
//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myJoinObserver = new JoinObserver("[B@368f3994", 506);
//        UserGameCommand myLeave = new Leave("[B@368f3994", 590);
//        UserGameCommand myLeave = new Leave("[B@368f3994", 506);
//        UserGameCommand myResign = new Resign("[B@368f3994", 506);
//        UserGameCommand myLeave = new Leave("token", 1234);
        ChessMove chessMove = new ChessMove(new ChessPosition(4, 2), new ChessPosition(5, 3), null);
//        ChessMove chessMove2 = new ChessMove(new ChessPosition(2, 2), new ChessPosition(5, 2), null);
        UserGameCommand myMakeMove = new MakeMove("[B@368f3994",507, chessMove);
//        UserGameCommand myMakeMove2 = new MakeMove("[B@368f3994",507, chessMove2);

//        menu.sendWebSocketMessage(myJoinObserver);
        menu.sendWebSocketMessage(myJoinPlayer);
//        menu.sendWebSocketMessage(myResign);
//        menu.sendWebSocketMessage(myLeave);
        menu.sendWebSocketMessage(myMakeMove);
//        menu.sendWebSocketMessage(myMakeMove2);

        menu.runMenu();

    }
}