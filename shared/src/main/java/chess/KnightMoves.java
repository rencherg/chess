package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves extends ChessPieceMoves {

    public KnightMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        ChessMove exampleChessMove = new ChessMove(new ChessPosition(1,1), new ChessPosition(2,3),null);

        collection.add(exampleChessMove);

        return collection;
    }
}
