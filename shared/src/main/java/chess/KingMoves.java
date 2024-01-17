package chess;

import java.util.Collection;

public class KingMoves extends ChessPieceMoves {

    public KingMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
