package chess;

import java.util.Collection;

public abstract class ChessPieceMoves {

    protected ChessBoard board;
    protected ChessPosition myPosition;

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
