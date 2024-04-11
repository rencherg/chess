import chess.*;
import ui.Menu;

public class Main {

    static final String PORT = "8080";

    public static void main(String[] args) throws InvalidMoveException {

        Menu menu = new Menu(PORT);

        try{
            menu.runMenu();
        }catch(Exception e){}
    }
}

//Bugs
//Try to join after resignation bug

//Things to write
//Send the move that was made to all participants

//Code quality
//run implementation