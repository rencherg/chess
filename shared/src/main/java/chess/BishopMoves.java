package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves extends ChessPieceMoves {

    public BishopMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        moveCheck(1,1,this.myPosition,this.myPosition,collection, true);
        moveCheck(1,-1,this.myPosition,this.myPosition,collection, true);
        moveCheck(-1,1,this.myPosition,this.myPosition,collection, true);
        moveCheck(-1,-1,this.myPosition,this.myPosition,collection, true);

        return collection;
    }
}
