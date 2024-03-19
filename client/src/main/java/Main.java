import ServerConnection.ServerIntegration;
import chess.*;
import ui.PrintBoard;

public class Main {

    public static void main(String[] args) {

        PrintBoard myPrint = new PrintBoard();
        ServerIntegration serverIntegration = new ServerIntegration("8080");

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
//        myPrint.printBoard(chessBoard);

//        try{
//            serverIntegration.restGetRequest("https://yesno.wtf/api", ServerConnection.ServerIntegration.RestMethod.GET, null, null);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }
}