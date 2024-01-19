package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class RookMoves extends ChessPieceMoves {

    public RookMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        int currentRow = this.myPosition.getRow();
        int currentCol = this.myPosition.getColumn();

//        boolean potentialMoves = true;

        System.out.println(board.toString());

        recursiveCheck(1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,1,this.myPosition,this.myPosition,collection);
        recursiveCheck(-1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }

//    private void recursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){
//
//        //create potential new position
//        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);
//
//
//        System.out.println("Beginning of function: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
////        System.out.println("get piece at new position: " + board.getPiece(newPosition));
//
//        //check if position is on the board
//        if(!newPosition.isValidPosition()){
//
//            System.out.println("Invalid Position: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//
//            return;
//
//            //if position empty:
//        } else if(board.getPiece(newPosition) == null){
//            //add move to collection and move to next position
//            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
//            collection.add(currentMove);
//            System.out.println("First else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//
////            System.out.println("Recursive check call: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//
//            recursiveCheck(rowOffset, colOffset, newPosition, originalPosition, collection);
//
////            System.out.println("row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//
//            //if occupied and enemy
//        }else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(originalPosition).getTeamColor()){
//
////            System.out.println("Last else if: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//
//            //add to collection
//            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
//            collection.add(currentMove);
//            System.out.println("Last else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//        }
//    }

//    private boolean isValidPosition(ChessPosition position){
//
//        int row = position.getRow();
//        int col = position.getColumn();
//
//        if (((row > 0) && (row < 9)) && ((col > 0) && (col < 9))){
//            return true;
//        } else{
//            return false;
//        }
//    }
}