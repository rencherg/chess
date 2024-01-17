package chess;

import java.util.Collection;

public class QueenMoves extends ChessPieceMoves {

    public QueenMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
