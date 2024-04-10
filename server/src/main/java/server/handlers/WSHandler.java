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
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerError;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;
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
    private List<UserSession> userSessionList = new ArrayList<>();
    SQLGameDAO sqlGameDAO = new SQLGameDAO();
    SQLAuthDAO sqlAuthDAO = new SQLAuthDAO();
//    SQLUserDAO sqlUserDAO = new SQLUserDAO();
//    ServerError serverError;
//    ServerMessage serverMessage;

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

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s", message);
        UserGameCommand receivedCommandClass = gson.fromJson(message, UserGameCommand.class);
        UserGameCommand.CommandType receivedCommand = receivedCommandClass.getCommandType();

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
//        session.getRemote().sendString("WebSocket response: " + message);

    }

    private void handleJoinObserver(Session session, JoinObserver joinObserver){

        String authToken = joinObserver.getAuthString();
        int gameId = joinObserver.getGameID();
        ChessGame chessGame = null;
        String username = null;

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData == null){
                ServerError serverError = new ServerError("Error: Failed authentication");
                sendMessage(serverError, session);
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Failed authentication");
            sendMessage(serverError, session);
            return;
        }

        //verify game id
        try {

            GameData gameToJoin = sqlGameDAO.getGame(gameId);

            if(gameToJoin == null){
                ServerError serverError = new ServerError("Error: Bad game ID");
                sendMessage(serverError, session);
                return;
            }

            chessGame = gameToJoin.getGame();

            if(chessGame.isGameOver()){
                ServerError serverError = new ServerError("Error: The game is over");
                sendMessage(serverError, session);
                return;
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Bad game ID or other SQL issue");
            sendMessage(serverError, session);
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                userSession.setGameId(gameId);
                userSession.setClientRole(ClientRole.OBSERVER);
                System.out.println("Logged in as observer");
            }
        }

        System.out.println("inside handle observer");

        LoadGame loadGame = new LoadGame(chessGame);
        sendMessage(loadGame, session);
        Notification notification = new Notification("Notification: " + username + " has joined as an observer.");
        sendToAllExceptRoot(notification, session);
    }


    private void handleJoinPlayer(Session session, JoinPlayer joinPlayer){
        String authToken = joinPlayer.getAuthString();
        int gameId = joinPlayer.getGameID();
        String username;
        ChessGame chessGame = null;
        ChessGame.TeamColor teamColor = joinPlayer.getPlayerColor();
        String joinedTeam = null;

        if(teamColor == null){
            ServerError serverError = new ServerError("Error: Incorrect Color");
            sendMessage(serverError, session);
            return;
        }

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData == null){
                ServerError serverError = new ServerError("Error: Failed authentication");
                sendMessage(serverError, session);
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Failed authentication");
            sendMessage(serverError, session);
            return;
        }

        //verify game id
        try {
            GameData gameToJoin = sqlGameDAO.getGame(gameId);
            String whiteUsername = gameToJoin.getWhiteUsername();
            String blackUsername = gameToJoin.getBlackUsername();

            if(gameToJoin == null){
                ServerError serverError = new ServerError("Error: Bad game ID");
                sendMessage(serverError, session);
                return;
            }

            chessGame = gameToJoin.getGame();

            if(chessGame.isGameOver()){
                ServerError serverError = new ServerError("Error: Game is over");
                sendMessage(serverError, session);
                return;
            }else if(!((whiteUsername != null && whiteUsername.equals(username) && teamColor.equals(ChessGame.TeamColor.WHITE)) || (blackUsername != null && blackUsername.equals(username) && teamColor.equals(ChessGame.TeamColor.BLACK)))){
                ServerError serverError = new ServerError("Error: Incorrect Color");
                sendMessage(serverError, session);
                return;
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Bad game ID");
            sendMessage(serverError, session);
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                userSession.setGameId(gameId);
                if(teamColor.equals(ChessGame.TeamColor.BLACK)){
                    userSession.setClientRole(ClientRole.BLACK);
                    joinedTeam = "black";
                }else if(teamColor.equals(ChessGame.TeamColor.WHITE)){
                    userSession.setClientRole(ClientRole.WHITE);
                    joinedTeam = "white";
                }

            }
        }

        LoadGame loadGame = new LoadGame(chessGame);
        sendMessage(loadGame, session);
        Notification notification = new Notification("Notification: " + username + " has joined the " + joinedTeam + " team.");
        sendToAllExceptRoot(notification, session);
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
            if(authData == null){
                ServerError serverError = new ServerError("Error: Failed authentication");
                sendMessage(serverError, session);
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Failed authentication");
            sendMessage(serverError, session);
            return;
        }

        //verify game id
        try {
            gameData = sqlGameDAO.getGame(gameId);
            if(gameData == null){
                ServerError serverError = new ServerError("Error: Bad game ID");
                sendMessage(serverError, session);
                return;
            }

            chessGame = gameData.getGame();

            if(chessGame.isGameOver()){
                ServerError serverError = new ServerError("Error: The game is over");
                sendMessage(serverError, session);
                return;
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Bad game ID");
            sendMessage(serverError, session);
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){
                //Check that the participant making a move is a player

                ClientRole clientRole = userSession.getClientRole();

                //Check that the participant resigning is a player
                if(clientRole == null || clientRole.equals(ClientRole.OBSERVER)){
                    ServerError serverError = new ServerError("Error: Only players can make moves");
                    sendMessage(serverError, session);
                    return;

                //Check that its the players turn that wants to make a move
                }else if(!((clientRole.equals(ClientRole.BLACK) && chessGame.getTeamTurn().equals(ChessGame.TeamColor.BLACK))||(clientRole.equals(ClientRole.WHITE) && chessGame.getTeamTurn().equals(ChessGame.TeamColor.WHITE)))){
                    ServerError serverError = new ServerError("Error: It's not your turn");
                    sendMessage(serverError, session);
                    return;
                }
            }
        }

        //try to execute move - If it doesn't work then return error
        try{
            chessGame.makeMove(chessMove);
        }catch(InvalidMoveException e){
            ServerError serverError = new ServerError("Error: Invalid move");
            sendMessage(serverError, session);
            return;
        }

        //This may need to change to a set function if the chessgame object in the gameData object is not changed automatically
        try {
            sqlGameDAO.updateGame(gameData);
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Problem with the move");
            sendMessage(serverError, session);
            return;
        }

        //Check if the game is in checkmate or stalemate
        if(chessGame.isInStalemate(ChessGame.TeamColor.BLACK) || chessGame.isInStalemate(ChessGame.TeamColor.WHITE)){
            chessGame.setGameOver(true);

            markGameOver(gameData, session);

            Notification notification = new Notification("The game has reached stalemate!");
            sendToAllExceptRoot(notification, session);
        }else if(chessGame.isInCheckmate(ChessGame.TeamColor.BLACK)){
            markGameOver(gameData, session);

            Notification notification = new Notification("Black has won the game!");
            sendToAllExceptRoot(notification, session);
        }else if(chessGame.isInCheckmate(ChessGame.TeamColor.WHITE)){
            markGameOver(gameData, session);

            Notification notification = new Notification("White has won the game!");
            sendToAllExceptRoot(notification, session);
        }

        LoadGame loadGame = new LoadGame(chessGame);
        sendToAllExceptRoot(loadGame, session);
//        4. Notify all other users of the move that was made
    }

    private void handleResign(Session session, Resign resign){
        String authToken = resign.getAuthString();
        int gameId = resign.getGameID();
        String username;
        ChessGame chessGame;
        GameData gameData;

        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData == null){
                ServerError serverError = new ServerError("Error: Failed authentication");
                sendMessage(serverError, session);
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Failed authentication");
            sendMessage(serverError, session);
            return;
        }

        //verify game id
        try {
            gameData = sqlGameDAO.getGame(gameId);
            if(gameData == null){
                ServerError serverError = new ServerError("Error: Bad game ID");
                sendMessage(serverError, session);
                return;
            }

            chessGame = gameData.getGame();

            if(chessGame.isGameOver()){
                ServerError serverError = new ServerError("Error: The game is over");
                sendMessage(serverError, session);
                return;
            }
        } catch (SQLException e) {
            //Notify of bad game id
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){

                ClientRole clientRole = userSession.getClientRole();

                //Check that the participant resigning is a player
                if(clientRole == null || clientRole.equals(ClientRole.OBSERVER)){
                    ServerError serverError = new ServerError("Error: You must be a player to resign.");
                    sendMessage(serverError, session);
                    return;
                }else{

                    if(clientRole.equals(ClientRole.WHITE)){
                        Notification notification = new Notification("Notification: " + username + ", the white player has resigned! Black wins the game!");
                        sendToAll(notification, session);
                    }else if(clientRole.equals(ClientRole.BLACK)) {
                        Notification notification = new Notification("Notification: " + username + ", the black player has resigned! White wins the game!");
                        sendToAll(notification, session);
                    }

                    markGameOver(gameData, session);

                    break;
                }

            }
        }
    }

    private void handleLeave(Session session, Leave leave){
        String authToken = leave.getAuthString();
        int gameId = leave.getGameID();
        String username;
        GameData joinedGame;


        //Check authToken
        try {
            AuthData authData = sqlAuthDAO.getAuth(authToken);
            if(authData == null){
                ServerError serverError = new ServerError("Error: Failed authentication");
                sendMessage(serverError, session);
                return;
            }else{
                username = authData.getUsername();
            }
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Failed authentication");
            sendMessage(serverError, session);
            return;
        }

        //verify game id
        try {
            joinedGame = sqlGameDAO.getGame(gameId);
            if(joinedGame == null){
                ServerError serverError = new ServerError("Error: Bad game ID");
                sendMessage(serverError, session);
                return;
            }

        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Bad game ID");
            sendMessage(serverError, session);
            return;
        }

        for(UserSession userSession: userSessionList){
            if(userSession.getUserSession().equals(session)){

                ClientRole clientRole = userSession.getClientRole();

                if(clientRole != null && clientRole.equals(ClientRole.BLACK)){
                    joinedGame.setBlackUsername(null);
                    Notification notification = new Notification("Notification: " + username + ", the black player has left the game.");
                    sendToAllExceptRoot(notification, session);
                }else if(clientRole != null && clientRole.equals(ClientRole.WHITE)){
                    joinedGame.setWhiteUsername(null);
                    Notification notification = new Notification("Notification: " + username + ", the white player has left the game.");
                    sendToAllExceptRoot(notification, session);
                }else if(clientRole != null && clientRole.equals(ClientRole.OBSERVER)){
                    Notification notification = new Notification("Notification: " + username + ", an observer has left the game.");
                    sendToAllExceptRoot(notification, session);
                }else{
                    ServerError serverError = new ServerError("Error: You must be an observer or player to leave.");
                    sendMessage(serverError, session);
                }

                userSession.setClientRole(null);
                userSession.setGameId(-1);

            }
        }

        //Other Items
//        3. Front end needs to terminate websocket connection!

    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {

        // Remove the closed session from the list of sessions
        UserSession userSession = getUserSession(session);
        userSessionList.remove(userSession);

        sessions.remove(session);
        System.out.println("WebSocket closed: " + statusCode + " - " + reason);
    }

    @OnWebSocketError
    public void onError(Session session, Throwable throwable) {
        System.out.println("WebSocket error: " + throwable.getMessage());
    }

    //Sends message to all users in the same game
    private void sendToAll(ServerMessage serverMessage, Session rootSession) {

        UserSession rootUserSession = getUserSession(rootSession);
        int id = rootUserSession.getGameId();

        if(rootUserSession == null){
            System.out.print("Error: userSession not found.");
            return;
        }

        userSessionList.forEach(userSession -> {
            try {
                final Session session = userSession.getUserSession();
                sendMessage(serverMessage, session);
            } catch (Exception e) {
                System.err.println("Error broadcasting message: " + e.getMessage());
            }
        });
    }

    //Sends message to all users in the same game
    private void sendToAllExceptRoot(ServerMessage serverMessage, Session rootSession) {

        UserSession rootUserSession = getUserSession(rootSession);
        int id = rootUserSession.getGameId();

        if(rootUserSession == null){
            System.out.print("Error: userSession not found.");
            return;
        }

        userSessionList.forEach(userSession -> {
            try {
                if(rootUserSession.getGameId() != id){
                    final Session session = userSession.getUserSession();
                    sendMessage(serverMessage, session);
                }
            } catch (Exception e) {
                System.err.println("Error broadcasting message: " + e.getMessage());
            }
        });
    }

    //send a message to the given session
    private void sendMessage(ServerMessage serverMessage, Session session) {
        try {
            String message = gson.toJson(serverMessage);
            session.getRemote().sendString(message);
        } catch (Exception e) {
            System.err.println("Error broadcasting message: " + e.getMessage());
        }
    }

    //Get the userSession object given a Session object
    private UserSession getUserSession(Session session) {
        for (UserSession userSession : userSessionList) {
            if (userSession.getUserSession().equals(session)) {
                return userSession;
            }
        }
        return null;
    }

    private void markGameOver(GameData gameData, Session session){

        ChessGame chessGame = gameData.getGame();

        chessGame.setGameOver(true);

        //This may need to change to a set function if the chessgame object in the gameData object is not changed automatically
        try {
            sqlGameDAO.updateGame(gameData);
        } catch (SQLException e) {
            ServerError serverError = new ServerError("Error: Problem with the move");
            sendMessage(serverError, session);
            return;
        }
    }

}