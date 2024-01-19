package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves extends ChessPieceMoves {

    public PawnMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {

        System.out.println(board.toString());

        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        ChessPosition emptyPosition1 = null;
        ChessPosition emptyPosition2 = null;
        ChessPosition attackPosition1 = null;
        ChessPosition attackPosition2 = null;

        //create potential new position depending on color
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

            System.out.println("Color is white");

            emptyPosition1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn());
            emptyPosition2 = new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn());
            attackPosition1 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1);
            attackPosition2 = new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + -1);

        } else if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

            System.out.println("Color is black");

            emptyPosition1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn());
            emptyPosition2 = new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn());
            attackPosition1 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1);
            attackPosition2 = new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + -1);

        }


        if (emptyPosition1.isValidPosition() && (board.getPiece(emptyPosition1) == null)) {
            //if promotion needed
            if((emptyPosition1.getRow() == 8) || (emptyPosition1.getRow() == 1)){
                for (ChessPiece.PieceType piece : ChessPiece.PieceType.values()) {
                    if((piece != ChessPiece.PieceType.PAWN) && (piece != ChessPiece.PieceType.KING)){
                        ChessMove currentMove = new ChessMove(myPosition, emptyPosition1, piece);
                        collection.add(currentMove);
                    }
                }
            }else{
                ChessMove currentMove = new ChessMove(myPosition, emptyPosition1, null);
                collection.add(currentMove);
            }


//            System.out.println("emptyPosition2 valid: " + emptyPosition2.isValidPosition() + "GetPiece at empty position1: " + board.getPiece(emptyPosition2));
//            System.out.println("row at empty position2: " + emptyPosition2.getRow());
            if (emptyPosition2.isValidPosition() && (board.getPiece(emptyPosition2) == null)) {

//                System.out.println("hello");
//
//                System.out.println("row at myposition: " + myPosition.getRow());
//
//                System.out.println("color: " + board.getPiece(myPosition).getTeamColor());

                if ((board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) && (myPosition.getRow() == 2)) {

//                    System.out.println("hello2");

                    ChessMove secondaryMove = new ChessMove(myPosition, emptyPosition2, null);
                    collection.add(secondaryMove);

                } else if ((board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK) && (myPosition.getRow() == 7)) {

                    ChessMove secondaryMove = new ChessMove(myPosition, emptyPosition2, null);
                    collection.add(secondaryMove);
                }

            }
        }

//        System.out.println("attackposition1 valid: " + attackPosition1.isValidPosition() + "GetPiece at attack position1: " + board.getPiece(attackPosition1));
//        if(board.getPiece(attackPosition1) != null) {
//            System.out.println("board.getPiece(attackPosition1).getTeamColor(): " + board.getPiece(attackPosition1).getTeamColor() + "board.getPiece(myPosition).getTeamColor(): " + board.getPiece(myPosition).getTeamColor());
//        }
        if (attackPosition1.isValidPosition() && (board.getPiece(attackPosition1) != null)) {
            if ((board.getPiece(attackPosition1).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {
                //if promotion needed
                if((attackPosition1.getRow() == 8) || (attackPosition1.getRow() == 1)){
                    for (ChessPiece.PieceType piece : ChessPiece.PieceType.values()) {
                        if((piece != ChessPiece.PieceType.PAWN) && (piece != ChessPiece.PieceType.KING)){
                            ChessMove currentMove = new ChessMove(myPosition, attackPosition1, piece);
                            collection.add(currentMove);
                        }
                    }
                }else{
                    ChessMove currentMove = new ChessMove(myPosition, attackPosition1, null);
                    collection.add(currentMove);
                }
            }
        }

//        System.out.println("attackposition2 valid: " + attackPosition2.isValidPosition() + "GetPiece at attack position2: " + board.getPiece(attackPosition2));
//        if(board.getPiece(attackPosition2) != null){
//            System.out.println("board.getPiece(attackPosition2).getTeamColor(): " + board.getPiece(attackPosition2).getTeamColor() + "board.getPiece(myPosition).getTeamColor(): " + board.getPiece(myPosition).getTeamColor());
//        }
        if (attackPosition2.isValidPosition() && (board.getPiece(attackPosition2) != null)) {
            if ((board.getPiece(attackPosition2).getTeamColor() != board.getPiece(myPosition).getTeamColor())) {

                //if promotion needed
                if((attackPosition2.getRow() == 8) || (attackPosition2.getRow() == 1)){
                    for (ChessPiece.PieceType piece : ChessPiece.PieceType.values()) {
                        if((piece != ChessPiece.PieceType.PAWN) && (piece != ChessPiece.PieceType.KING)){
                            ChessMove currentMove = new ChessMove(myPosition, attackPosition2, piece);
                            collection.add(currentMove);
                        }
                    }
                }else{
                    ChessMove currentMove = new ChessMove(myPosition, attackPosition2, null);
                    collection.add(currentMove);
                }
            }
        }

        return collection;
    }
}
