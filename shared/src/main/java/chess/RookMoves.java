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

        boolean potentialMoves = true;

        recursiveCheck(1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,1,this.myPosition,this.myPosition,collection);
        recursiveCheck(-1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }

    private void recursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){

        //create potential new position
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);

        //check if position is on the board
        if(!isValidPosition(newPosition)){
            return;
        }

        //if position empty:
        if(board.getPiece(newPosition) == null){
            //add move to collection and move to next position
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            recursiveCheck(rowOffset, colOffset, newPosition, originalPosition, collection);

            //if occupied and enemy
        }else if(board.getPiece(newPosition).getPieceType() != board.getPiece(originalPosition).getPieceType()){
            //add to collection
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
        }
    }

    private boolean isValidPosition(ChessPosition position){

        int row = position.getRow();
        int col = position.getColumn();

        if (((row > 0) && (row < 9)) && ((col > 0) && (col < 9))){
            return true;
        } else{
            return false;
        }
    }
}