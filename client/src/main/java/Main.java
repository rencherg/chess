import ServerConnection.ServerIntegration;
import chess.*;
import ui.PrintBoard;

public class Main {

    public static void main(String[] args) throws InvalidMoveException {

        PrintBoard myPrint = new PrintBoard();

        ChessGame myGame = new ChessGame();
        new ChessPosition(2, 2);
        myGame.makeMove(new ChessMove(new ChessPosition(2, 2), new ChessPosition(4, 2), null));
        ChessBoard chessBoard = myGame.getBoard();
//        chessBoard.resetBoard();

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        myPrint.printBoard(chessBoard);

//        try{
//            serverIntegration.restGetRequest("https://yesno.wtf/api", ServerConnection.ServerIntegration.RestMethod.GET, null, null);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }
}