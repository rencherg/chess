package chess;

import java.util.Collection;

public abstract class ChessPieceMoves {

    protected ChessBoard board;
    protected ChessPosition myPosition;

    protected ChessPieceMoves(ChessBoard board, ChessPosition myPosition){



        this.myPosition = myPosition;
        this.board = board;


//        System.out.println(board.toString());
    }

    public abstract Collection<ChessMove> getMoves();

    protected void recursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){

        //create potential new position
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);


        System.out.println("Beginning of function: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//        System.out.println("get piece at new position: " + board.getPiece(newPosition));

        //check if position is on the board
        if(!newPosition.isValidPosition()){

            System.out.println("Invalid Position: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            return;

            //if position empty:
        } else if(board.getPiece(newPosition) == null){
            //add move to collection and move to next position
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            System.out.println("First else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

//            System.out.println("Recursive check call: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            recursiveCheck(rowOffset, colOffset, newPosition, originalPosition, collection);

//            System.out.println("row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            //if occupied and enemy
        }else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(originalPosition).getTeamColor()){

//            System.out.println("Last else if: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            //add to collection
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            System.out.println("Last else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
        }
    }

    protected void nonRecursiveCheck(int rowOffset, int colOffset, ChessPosition currentPosition, ChessPosition originalPosition, Collection<ChessMove> collection){

        //create potential new position
        ChessPosition newPosition = new ChessPosition(currentPosition.getRow()+rowOffset, currentPosition.getColumn()+colOffset);


        System.out.println("Beginning of function: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
//        System.out.println("get piece at new position: " + board.getPiece(newPosition));

        //check if position is on the board
        if(!newPosition.isValidPosition()){

            System.out.println("Invalid Position: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            return;

            //if position empty:
        } else if(board.getPiece(newPosition) == null){
            //add move to collection and move to next position
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            System.out.println("First else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

//            System.out.println("Recursive check call: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

//            recursiveCheck(rowOffset, colOffset, newPosition, originalPosition, collection);

//            System.out.println("row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            //if occupied and enemy
        }else if(board.getPiece(newPosition).getTeamColor() != board.getPiece(originalPosition).getTeamColor()){

//            System.out.println("Last else if: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());

            //add to collection
            ChessMove currentMove = new ChessMove(originalPosition,newPosition,null);
            collection.add(currentMove);
            System.out.println("Last else if added: row: " + newPosition.getRow() + ", col: "+ newPosition.getColumn());
        }
    }

}

/*
Input: Board, Piece Type, Position
Output: Collection of Valid Moves

 */
