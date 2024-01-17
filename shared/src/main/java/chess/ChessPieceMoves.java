package chess;

import java.util.Collection;

public abstract class ChessPieceMoves {

    private ChessBoard board;
    private ChessPosition myPosition;

    public ChessPieceMoves(ChessBoard board, ChessPosition myPosition){
        this.myPosition = myPosition;
        this.board = board;
    }

    public abstract Collection<ChessMove> getMoves();

}

/*
Input: Board, Piece Type, Position
Output: Collection of Valid Moves

 */
