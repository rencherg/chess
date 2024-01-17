package chess;

import java.util.Collection;

public class PawnMoves extends ChessPieceMoves {

    public PawnMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
