package ui;

import ServerConnection.ServerFacade;
import ServerConnection.WebSocketIntegration;
import ServerConnection.WebSocketObserver;
import chess.ChessBoard;
import chess.ChessGame;
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

    private final String PORT;
//    private static final int PORT = 8080;
    ServerFacade serverFacade;
//    private static Server server = new Server();
    private Scanner scanner = new Scanner(System.in);
    private String authToken = null;
    private Map<String, ChessGame> gameMap = new HashMap<>();
    private PrintBoard printBoard = new PrintBoard();
    Gson gson = new Gson();


    private final String LOGGED_OUT_MENU = "Choose an Item\n" +
            "1 - Help\n" +
            "2 - Quit\n" +
            "3 - Login\n" +
            "4 - Register\n" +
            "Type the number of the option you want";

    private final String LOGGED_IN_MENU = "Choose an Item\n" +
            "1 - Help\n" +
            "2 - Logout\n" +
            "3 - Create Game\n" +
            "4 - List Games\n" +
            "5 - Join Game\n" +
            "6 - Join Observer\n" +
            "Type the number of the option you want";

    private final String LOGGED_OUT_HELP_STRING = "Help Instructions:\n" +
            "Quit - Exit the program\n" +
            "Login - Enter credentials to create a new login session\n" +
            "Register - Register as a new user";

    private final String LOGGED_IN_HELP_STRING = "Help Instructions:\n" +
            "Logout - Exits the current login session\n" +
            "Create Game - Creates a new chess game\n" +
            "List Games - Lists all existing chess games\n" +
            "Join Game - Joins an existing chess game\n" +
            "Join Observer - Joins an existing chess game only as an observer";

    public Menu(String port){
        this.PORT = port;
        serverFacade = new ServerFacade(String.valueOf(PORT), this);
//        var port = server.run(PORT);
        System.out.println("Started test HTTP server on " + port);
    }

    private String getUserInput(){
        return scanner.nextLine();
    }

    public void runMenu(){
        System.out.println("Welcome to Chess!");
        loggedOutMenu();
//        server.stop();
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
                    this.joinGame();
                    break;
                case "6":
                    this.joinGameAsObserver();
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

    private void joinGame(){

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
                this.serverFacade.joinGame(authToken, gameTeam.toUpperCase(), selectedGame.getGameId());
                System.out.println("Game successfully joined!");
                printBoard.printBoard(selectedGame.getBoard());
            }else if(gameTeam.toLowerCase().equals("black")){
                this.serverFacade.joinGame(authToken, gameTeam.toUpperCase(), selectedGame.getGameId());
                System.out.println("Game successfully joined!");
                printBoard.printBoard(selectedGame.getBoard());
            }else{
                System.out.println("Incorrect team selected");
            }
        }else{
            System.out.println("Game not found");
        }
    }

    private void joinGameAsObserver(){
        if(this.gameMap.isEmpty()){
            System.out.println("No games exist to join");
        }

        System.out.println("Type the game number from the last time all games were printed");
        String gameNumber = this.getUserInput();
        if(this.gameMap.get(gameNumber) != null){
            ChessGame selectedGame = this.gameMap.get(gameNumber);
            this.serverFacade.joinGame(authToken, null, selectedGame.getGameId());
            System.out.println("Game successfully joined as an observer!");
            printBoard.printBoard(selectedGame.getBoard());
        }else{
            System.out.println("Game not found");
        }
    }

    public void onMessageReceived(String message) {
        // Process the received message
//        System.out.println("Received message from websocket!!: " + message);

        //Deserialize and process
//        System.out.printf("Received: %s", message);
        ServerMessage receivedServerMessage = gson.fromJson(message, ServerMessage.class);
        ServerMessage.ServerMessageType receivedMessage = receivedServerMessage.getServerMessageType();

//        for(UserSession userSession: userSessionList){
//            if(userSession.getUserSession().equals(session)){
//                userSession
//            }
//        }

        switch(receivedMessage){
            case LOAD_GAME:
                LoadGame loadGame = gson.fromJson(message, LoadGame.class);
//                handleJoinObserver(session, joinObserver);
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

//        System.out.println(receivedCommand);
    }

    private void handleLoadGame(LoadGame loadGame){
        //Figure out how to store the board
        System.out.println("New board received");
        ChessGame game = loadGame.getGame();
        ChessBoard board = game.getBoard();
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

//    public void webSocketTest(){
//        try{
//            JoinObserver joinObserver = new JoinObserver("nviurenvire", 12345);
//            this.serverFacade.sendWebSocketMessage(joinObserver);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
