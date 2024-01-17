package chess;

import java.util.Collection;

public class BishopMoves extends ChessPieceMoves {

    public BishopMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        return null;
    }
}
