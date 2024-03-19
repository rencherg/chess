package ui;

import ServerConnection.ServerFacade;
import chess.ChessGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    String port;
    private Scanner scanner = new Scanner(System.in);
    private ServerFacade serverFacade = new ServerFacade(port);
    private String authToken = null;
    private Map<Integer, ChessGame> gameMap = new HashMap<>();
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
        this.port = port;
    }

    private String getUserInput(){
        return scanner.nextLine();
    }

    public void runMenu(){
        loggedOutMenu();
    }

    private void loggedOutMenu(){

        System.out.println("Welcome to Chess!");

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

    private void register(){
        System.out.println("Type your username");
        String username = this.getUserInput();
        System.out.println("Type your password");
        String password = this.getUserInput();
        System.out.println("Type your email");
        String email = this.getUserInput();

        try{
            String authToken = this.serverFacade.register(username, password, email);
            this.authToken = authToken;
            this.loggedInMenu();
        }catch(RuntimeException e){
            System.out.println("Registration was unsuccessful");
        }

    }

    private void loggedInMenu(){
        System.out.println("Loggged in menu!");
    }

}
