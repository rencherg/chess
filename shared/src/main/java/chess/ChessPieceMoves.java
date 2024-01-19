package chess;

import java.util.Collection;

public abstract class ChessPieceMoves {

    protected ChessBoard board;
    protected ChessPosition myPosition;

    protected ChessPieceMoves(ChessBoard board, ChessPosition myPosition){

        this.myPosition = myPosition;
        this.board = board;

    }

    public abstract Collection<ChessMove> getMoves();

    protected void recursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){

        //create potential new position
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);

      //check if position is on the board
        if(!newPosition.isValidPosition()){

            //if position empty:
        } else if(board.getPiece(newPosition) == null){
            //add move to collection and move to next position
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            recursiveCheck(rowOffset, colOffset, newPosition, originalPosition, collection);

            //if occupied and enemy
        }else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(originalPosition).getTeamColor()){

            //add to collection
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
        }
    }

    protected void nonRecursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){

        //create potential new position
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);

        //check if position is on the board
        if(!newPosition.isValidPosition()){

            //if position empty:
        } else if((board.getPiece(newPosition) == null) && newPosition.isValidPosition()){
            //add move to collection and move to next position
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);

            //if occupied and enemy
        }else if((board.getPiece(newPosition).getTeamColor() != board.getPiece(originalPosition).getTeamColor()) && newPosition.isValidPosition()){

            //add to collection
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            System.out.println("Last else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
        }
    }

}
