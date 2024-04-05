import chess.*;
import ui.Menu;

public class Main {

    static final String PORT = "8080";

    public static void main(String[] args) throws InvalidMoveException {

        Menu menu = new Menu(PORT);

        menu.sendWebSocketMessage("Dread Pirate Roberts");

        menu.runMenu();

    }
}