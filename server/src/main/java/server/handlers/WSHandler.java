package server.handlers;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.userCommands.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static webSocketMessages.userCommands.UserGameCommand.CommandType.*;

@WebSocket
public class WSHandler {

    // List to store connected sessions
    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();
    List<UserSession> userSessionList = new ArrayList<>();
    SQLGameDAO sqlGameDAO = new SQLGameDAO();
    SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
    SQLUserDAO sqlUserDAO = new SQLUserDAO();

    Gson gson = new Gson();

    public enum ClientRole {
        OBSERVER,
        WHITE,
        BLACK

    }

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        // Add the new session to the list of sessions
        sessions.add(session);
        userSessionList.add(new UserSession(session));
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
        UserGameCommand receivedCommandClass = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType receivedCommand = receivedCommandClass.getCommandType();

//        for(UserSession userSession: userSessionList){
//            if(userSession.getUserSession().equals(session)){
//                userSession
//            }
//        }

        switch(receivedCommand){
            case JOIN_OBSERVER:
                JoinObserver joinObserver = gson.fromJson(message, JoinObserver.class);
                handleJoinObserver(session, joinObserver);
                break;
            case JOIN_PLAYER:
                JoinPlayer joinPlayer = gson.fromJson(message, JoinPlayer.class);
                handleJoinPlayer(session, joinPlayer);
                break;
            case MAKE_MOVE:
                MakeMove makeMove = gson.fromJson(message, MakeMove.class);
                handleMakeMove(session, makeMove);
                break;
            case LEAVE:
                Leave leave = gson.fromJson(message, Leave.class);
                handleLeave(session, leave);
                break;
            case RESIGN:
                Resign resign = gson.fromJson(message, Resign.class);
                handleResign(session, resign);
                break;
        }

        System.out.println(receivedCommand);
        session.getRemote().sendString("WebSocket response: " + message);

    }

    private void handleJoinObserver(Session session, JoinObserver joinObserver){

        String authToken = joinObserver.getAuthString();
        int gameId = joinObserver.getGameID();

        //Check authToken
        try {
            if(sqlAuthDAO.getAuth(authToken).equals(null)){
                //Notify of authentication failure
                return;
            }
        } catch (SQLException e) {
            //Notify of authentication failure
            return;
        }

        //verify game id
        try {

            GameData gameToJoin = sqlGameDAO.getGame(gameId);

            if(gameToJoin.equals(null)){
                //Notify of bad game id
                return;
            }

            ChessGame chessGame = gameToJoin.getGame();

            if(chessGame.isGameOver()){
                //Notify game is over
                return;
            }
        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                userSession.setGameId(gameId);
                userSession.setClientRole(ClientRole.OBSERVER);
            }
        }

        System.out.println("inside handle observer");

        //Other Items
//        3. Send load game to root client
//        4. Send notification to all other users in the game
    }


    private void handleJoinPlayer(Session session, JoinPlayer joinPlayer){
        String authToken = joinPlayer.getAuthString();
        int gameId = joinPlayer.getGameID();
        String username;

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData.equals(null)){
                //Notify of authentication failure
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            //Notify of authentication failure
            return;
        }

        //verify game id
        try {
            GameData gameToJoin = sqlGameDAO.getGame(gameId);
            if(gameToJoin.equals(null)){
                //Notify of bad game id
                return;
            }

            ChessGame chessGame = gameToJoin.getGame();

            if(chessGame.isGameOver()){
                //Notify game is over
                return;
            }else if(!((gameToJoin.getWhiteUsername().equals(username) && joinPlayer.getPlayerColor().equals(ChessGame.TeamColor.WHITE)) || (gameToJoin.getBlackUsername().equals(username) && joinPlayer.getPlayerColor().equals(ChessGame.TeamColor.BLACK)))){
                //Notify of incorrect color
                return;
            }
        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                userSession.setGameId(gameId);
                if(joinPlayer.getPlayerColor().equals(ChessGame.TeamColor.BLACK)){
                    userSession.setClientRole(ClientRole.BLACK);
                }else if(joinPlayer.getPlayerColor().equals(ChessGame.TeamColor.WHITE)){
                    userSession.setClientRole(ClientRole.WHITE);
                }

            }
        }

        System.out.println("inside handle observer");

        //Other Items
//        3. Send load game to root client
//        4. Send notification to all other users in the game
    }

    private void handleMakeMove(Session session, MakeMove makeMove){
        String authToken = makeMove.getAuthString();
        int gameId = makeMove.getGameID();
        String username;
        ChessGame chessGame;
        GameData gameData;
        ChessMove chessMove = makeMove.getMove();

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData.equals(null)){
                //Notify of authentication failure
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            //Notify of authentication failure
            return;
        }

        //verify game id
        try {
            gameData = sqlGameDAO.getGame(gameId);
            if(gameData.equals(null)){
                //Notify of bad game id
                return;
            }

            chessGame = gameData.getGame();

            if(chessGame.isGameOver()){
                //Notify game is over
                return;
            }
        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                //Check that the participant making a move is a player
                if(!(((userSession.getClientRole().equals(ClientRole.BLACK))||(userSession.getClientRole().equals(ClientRole.WHITE))))){
                    //notify that observers can't make moves
                    return;

                //Check that its the players turn that wants to make a move
                }else if(!((userSession.getClientRole().equals(ClientRole.BLACK) && chessGame.getTeamTurn().equals(ChessGame.TeamColor.BLACK))||(userSession.getClientRole().equals(ClientRole.WHITE) && chessGame.getTeamTurn().equals(ChessGame.TeamColor.WHITE)))){
                    //Notify the player that it's not their turn
                    return;
                }else{
                    chessGame.setGameOver(true);
                }

            }
        }

        //try to execute move - If it doesn't work then return error
        try{
            chessGame.makeMove(chessMove);
        }catch(InvalidMoveException e){
            //Notify the user of invalid move.
            return;
        }

        //This may need to change to a set function if the chessgame object in the gameData object is not changed automatically
        try {
            sqlGameDAO.updateGame(gameData);
        } catch (SQLException e) {
            //notify user of bad sql action
            return;
        }

        //Other Items
//        3. Send new game notification to all users in the game
//        4. Notify all other users of the move that was made
    }

    private void handleResign(Session session, Resign resign){
        String authToken = resign.getAuthString();
        int gameId = resign.getGameID();
        String username;
        ChessGame chessGame;

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData.equals(null)){
                //Notify of authentication failure
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            //Notify of authentication failure
            return;
        }

        //verify game id
        try {
            GameData gameToJoin = sqlGameDAO.getGame(gameId);
            if(gameToJoin.equals(null)){
                //Notify of bad game id
                return;
            }

            chessGame = gameToJoin.getGame();

            if(chessGame.isGameOver()){
                //Notify game is over
                return;
            }
        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                //Check that the participant resigning is a player
                if(!((userSession.getClientRole().equals(ClientRole.BLACK))||(userSession.getClientRole().equals(ClientRole.WHITE)))){
                    //notify that observers can't resign
                    return;
                }else{
                    chessGame.setGameOver(true);
                }

            }
        }

        System.out.println("inside handle observer");

        //Other Items
//        3. Send notification to all other users in the game saying the game is over and the other player won
    }

    private void handleLeave(Session session, Leave leave){
        String authToken = leave.getAuthString();
        int gameId = leave.getGameID();
        String username;
        GameData joinedGame;

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData.equals(null)){
                //Notify of authentication failure
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            //Notify of authentication failure
            return;
        }

        //verify game id
        try {
            joinedGame = sqlGameDAO.getGame(gameId);
            if(joinedGame.equals(null)){
                //Notify of bad game id
                return;
            }

        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){

                if(userSession.getClientRole().equals(ClientRole.BLACK)){
                    joinedGame.setBlackUsername(null);
                    //notify others that black player left
                }else if(userSession.getClientRole().equals(ClientRole.WHITE)){
                    joinedGame.setWhiteUsername(null);
                    //notify others that white player left
                }else{
                    //notify others that observer left
                }

                userSession.setClientRole(null);
                userSession.setGameId(-1);

            }
        }

        System.out.println("inside handle observer");

        //Other Items
//        3. Front end needs to terminate websocket connection!

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
