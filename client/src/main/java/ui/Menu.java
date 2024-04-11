package ui;

import ServerConnection.ServerFacade;
import ServerConnection.WebSocketIntegration;
import ServerConnection.WebSocketObserver;
import chess.*;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerError;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu implements WebSocketObserver {

    private String port;
//    private static final int PORT = 8080;
    ServerFacade serverFacade;
    private Scanner scanner = new Scanner(System.in);
    private String authToken = null;
    private Map<String, ChessGame> gameMap = new HashMap<>();
    private final PrintBoard printBoard = new PrintBoard();
    Gson gson = new Gson();
    ChessBoard currentBoard = null;
    int currentGameId = -1;
    ChessGame.TeamColor color;
    boolean isObserver = false;

    private static final String LOGGED_OUT_MENU = "Choose an Item\n" +
            "1 - Help\n" +
            "2 - Quit\n" +
            "3 - Login\n" +
            "4 - Register\n" +
            "Type the number of the option you want";

    private static final String LOGGED_IN_MENU = "Choose an Item\n" +
            "1 - Help\n" +
            "2 - Logout\n" +
            "3 - Create Game\n" +
            "4 - List Games\n" +
            "5 - Join Game\n" +
            "6 - Join Observer\n" +
            "Type the number of the option you want";

    private static final String GAMEPLAY_MENU = "Choose an Item\n" +
            "1 - Help\n" +
            "2 - Redraw Chess Board\n" +
            "3 - Leave\n" +
            "4 - Make Move\n" +
            "5 - Resign\n" +
            "6 - Highlight Legal Moves\n" +
            "Type the number of the option you want";

    private static final String LOGGED_OUT_HELP_STRING = "Help Instructions:\n" +
            "Quit - Exit the program\n" +
            "Login - Enter credentials to create a new login session\n" +
            "Register - Register as a new user";

    private static final String LOGGED_IN_HELP_STRING = "Help Instructions:\n" +
            "Logout - Exits the current login session\n" +
            "Create Game - Creates a new chess game\n" +
            "List Games - Lists all existing chess games\n" +
            "Join Game - Joins an existing chess game\n" +
            "Join Observer - Joins an existing chess game only as an observer";

    private static final String GAMEPLAY_HELP_STRING = "Help Instructions:\n" +
            "Redraw Chess Board - Draw the chess board on the screen\n" +
            "Leave - Leave the game\n" +
            "Make Move - Make a move in the game. It must be your turn and you must be a player to make a move\n" +
            "Resign - Give up. You must be a player to do this.\n" +
            "Highlight Legal Moves - Draw the chess board with all legal moves highlighted.";

    public Menu(String port){
        this.port = port;
        serverFacade = new ServerFacade(String.valueOf(port), this);
//        var port = server.run(PORT);
        System.out.println("Started test HTTP server on " + port);
    }

    private String getUserInput(){
        return scanner.nextLine();
    }

    public void runMenu(){
        System.out.println("Welcome to Chess!");
        loggedOutMenu();
    }

    private void loggedOutMenu(){

        boolean continueProgram = true;

        String userInput;

        while (continueProgram){
            System.out.println(this.LOGGED_OUT_MENU);
            userInput = getUserInput();
            switch(userInput){
                case "1":
                    System.out.println(this.LOGGED_OUT_HELP_STRING);
                    break;
                case "2":
                    continueProgram = false;
                    break;
                case "3":
                    this.login();
                    break;
                case "4":
                    this.register();
                    break;
            }
        }
    }

    private void loggedInMenu(){
        System.out.println("Logged In!");

        boolean continueProgram = true;

        String userInput;

        while (continueProgram){
            System.out.println(this.LOGGED_IN_MENU);
            userInput = getUserInput();
            boolean successJoin = false;
            switch(userInput){
                case "1":
                    System.out.println(this.LOGGED_IN_HELP_STRING);
                    break;
                case "2":
                    continueProgram = false;
                    break;
                case "3":
                    this.createGame();
                    break;
                case "4":
                    this.listGames();
                    break;
                case "5":
                    successJoin = this.joinGame();
                    UserGameCommand join = new JoinPlayer(authToken, color, currentGameId);
                    if(successJoin){
                        try {
                            serverFacade.sendWebSocketMessage(join);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        this.gameplayMenu();
                    }
                    break;
                case "6":
                    successJoin = this.joinGameAsObserver();
                    UserGameCommand joinAsObserver = new JoinObserver(authToken, currentGameId);
                    if(successJoin){
                        try {
                            serverFacade.sendWebSocketMessage(joinAsObserver);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        this.gameplayMenu();
                    }
                    break;
            }
        }
    }

    private void gameplayMenu(){
        System.out.println("Gameplay");

        boolean continueProgram = true;

        String userInput;

        while (continueProgram){
            System.out.println(this.GAMEPLAY_MENU);
            userInput = getUserInput();
            switch(userInput){
                case "1":
                    System.out.println(this.LOGGED_IN_HELP_STRING);
                    break;
                case "2":
                    printBoard.printBoard(currentBoard);
                    break;
                case "3":
                    UserGameCommand leave = new Leave(authToken, currentGameId);
                    try {
                        serverFacade.sendWebSocketMessage(leave);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return;
                case "4":
                    if(isObserver){
                        System.out.println("Error: Observers can't make moves");
                        break;
                    }
                    ChessMove move = getChessMove();
                    if(move != null){
                        UserGameCommand makeMove = new MakeMove(authToken, currentGameId, move);
                        try {
                            serverFacade.sendWebSocketMessage(makeMove);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case "5":
                    if(isObserver){
                        System.out.println("Error: Observers can't resign");
                        break;
                    }
                    UserGameCommand resign = new Resign(authToken, currentGameId);
                    try {
                        serverFacade.sendWebSocketMessage(resign);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "6":
                    printBoard.highlightLegalMoves(currentBoard);
                    break;
            }
        }
    }

    private void login(){
        System.out.println("Type your username");
        String username = this.getUserInput();
        System.out.println("Type your password");
        String password = this.getUserInput();

        try{
            String authToken = this.serverFacade.login(username, password);
            this.authToken = authToken;
            this.loggedInMenu();
        }catch(RuntimeException e){
            System.out.println("Login was unsuccessful");
        }

    }

    private void register() {
        System.out.println("Type your username");
        String username = this.getUserInput();
        System.out.println("Type your password");
        String password = this.getUserInput();
        System.out.println("Type your email");
        String email = this.getUserInput();

        try {
            String authToken = this.serverFacade.register(username, password, email);
            this.authToken = authToken;
            this.loggedInMenu();
        } catch (RuntimeException e) {
            System.out.println("Registration was unsuccessful");
        }
    }

    private void createGame(){
        System.out.println("Type the game name");
        String gameName = this.getUserInput();
        this.serverFacade.createGame(this.authToken, gameName);
        System.out.println("Success!");
    }

    private void listGames(){
        ChessGame gameList[] = this.serverFacade.listGames(this.authToken);

        String blackUsername = null;
        String whiteUsername = null;
        String gameName = null;

        if(gameList.length == 0){
            System.out.println("No games exist yet");
        }

        for(int i = 1; i <= gameList.length; i++){

            gameName = gameList[i-1].getGameName();
            whiteUsername = gameList[i-1].getWhiteUsername();
            blackUsername = gameList[i-1].getBlackUsername();

            if(blackUsername == null){
                blackUsername = "Empty";
            }

            if(whiteUsername == null){
                whiteUsername = "Empty";
            }

            this.gameMap.put(String.valueOf(i), gameList[i-1]);

            System.out.println(i + " - " + gameName + " Black Username: " + blackUsername + " White Username: " + whiteUsername);

        }
    }

    private boolean joinGame(){

        if(this.gameMap.isEmpty()){
            System.out.println("No games exist to join");
        }

        System.out.println("Type the game number from the last time all games were printed");
        String gameNumber = this.getUserInput();
        if(this.gameMap.get(gameNumber) != null){
            ChessGame selectedGame = this.gameMap.get(gameNumber);
            System.out.println(selectedGame.getGameName() + " selected!");
            System.out.println("Please type the desired team (Black/White)");
            String gameTeam = this.getUserInput();
            System.out.println(gameTeam.toLowerCase());
            if(gameTeam.toLowerCase().equals("white")){
                try{
                    this.serverFacade.joinGame(authToken, gameTeam.toUpperCase(), selectedGame.getGameId());
                }catch(Exception e){
                    System.out.println("Error with joining the game.");
                    return false;
                }
                System.out.println("Game successfully joined!");
                this.currentBoard = selectedGame.getBoard();
                this.currentGameId = selectedGame.getGameId();
                this.color = ChessGame.TeamColor.WHITE;
                this.isObserver = false;
                return true;
            }else if(gameTeam.toLowerCase().equals("black")){
                try{
                    this.serverFacade.joinGame(authToken, gameTeam.toUpperCase(), selectedGame.getGameId());
                }catch(Exception e){
                    System.out.println("Error with joining the game.");
                    return false;
                }
                System.out.println("Game successfully joined!");
                this.currentBoard = selectedGame.getBoard();
                this.currentGameId = selectedGame.getGameId();
                this.color = ChessGame.TeamColor.BLACK;
                this.isObserver = false;
                return true;
            }else{
                System.out.println("Incorrect team selected");
            }
        }else{
            System.out.println("Game not found");
        }
        return false;
    }

    private boolean joinGameAsObserver(){
        if(this.gameMap.isEmpty()){
            System.out.println("No games exist to join");
        }

        System.out.println("Type the game number from the last time all games were printed");
        String gameNumber = this.getUserInput();
        if(this.gameMap.get(gameNumber) != null){
            ChessGame selectedGame = this.gameMap.get(gameNumber);
            this.serverFacade.joinGame(authToken, null, selectedGame.getGameId());
            this.currentBoard = selectedGame.getBoard();
            this.currentGameId = selectedGame.getGameId();
            this.color = ChessGame.TeamColor.WHITE;
            System.out.println("Game successfully joined as an observer!");
            this.isObserver = true;
            return true;
        }else{
            System.out.println("Game not found");
        }
        return false;
    }

    public void onMessageReceived(String message) {

        ServerMessage receivedServerMessage = gson.fromJson(message, ServerMessage.class);
        ServerMessage.ServerMessageType receivedMessage = receivedServerMessage.getServerMessageType();

        switch(receivedMessage){
            case LOAD_GAME:
                LoadGame loadGame = gson.fromJson(message, LoadGame.class);
                handleLoadGame(loadGame);
                break;
            case ERROR:
                ServerError serverError = gson.fromJson(message, ServerError.class);
                handleError(serverError);
                break;
            case NOTIFICATION:
                Notification notification = gson.fromJson(message, Notification.class);
                handleNotification(notification);
                break;
        }
    }

    private void handleLoadGame(LoadGame loadGame){
        System.out.print("New board received");
        ChessGame game = loadGame.getGame();
        ChessBoard board = game.getBoard();
        this.currentBoard = board;
        printBoard.printBoard(board);
    }

    private void handleError(ServerError serverError){
        System.out.println(serverError.getErrorMessage());
    }

    private void handleNotification(Notification notification){
        System.out.println(notification.getMessage());
    }

    public void sendWebSocketMessage(UserGameCommand userGameCommand){
        try{
            this.serverFacade.sendWebSocketMessage(userGameCommand);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ChessMove getChessMove(){

        ChessPosition originPosition = null;
        ChessPosition destinationPosition = null;
        ChessPiece.PieceType promotionPieceType = null;

        boolean continueOrigin = true;
        boolean continueDestination = true;
        boolean continuePromotion = true;

        while(continueOrigin){
            System.out.println("Type the origin (ex: a1) OR type Q to cancel");
            String origin = this.getUserInput();

            if(origin.toUpperCase().equals("Q")){
                return null;
            }else{
                originPosition = printBoard.getPositionFromCoordinates(origin);
                if(originPosition != null){
                    continueOrigin = false;
                }
            }
        }

        while(continueDestination){
            System.out.println("Type the destination (ex: a1) OR type Q to cancel");
            String destination = this.getUserInput();

            if(destination.substring(0,1).toUpperCase().equals("Q")){
                return null;
            }else{
                destinationPosition = printBoard.getPositionFromCoordinates(destination);
                if(destinationPosition != null){
                    continueDestination = false;
                }
            }
        }

        while(continuePromotion){
            System.out.println("Type the desired promotion piece (QBNR), Q for quit or M for N/A");
            String promotionString = this.getUserInput();

            if(promotionString.substring(0,1).toUpperCase().equals("Q")){
                return null;
            }else if(promotionString.substring(0,1).toUpperCase().equals("M")){
                continuePromotion = false;
            }else{
                promotionPieceType = getPromotionPiece(promotionString);
                if(promotionPieceType != null){
                    continuePromotion = false;
                }
            }
        }

        return new ChessMove(originPosition, destinationPosition, promotionPieceType);
    }

    private ChessPiece.PieceType getPromotionPiece(String input){

        if (input.length() != 1){
            return null;
        }

        String inputUpper = input.toUpperCase();

        switch(input){
            case "Q":
                return ChessPiece.PieceType.QUEEN;
            case "N":
                return ChessPiece.PieceType.KNIGHT;
            case "B":
                return ChessPiece.PieceType.BISHOP;
            case "R":
                return ChessPiece.PieceType.ROOK;
        }

        return null;
    }
}