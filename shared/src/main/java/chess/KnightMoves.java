package chess;

import java.util.Collection;

public class KnightMoves extends ChessPieceMoves {

    public KnightMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
