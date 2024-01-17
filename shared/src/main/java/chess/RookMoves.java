package chess;

import java.util.Collection;

public class RookMoves extends ChessPieceMoves {

    public RookMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
