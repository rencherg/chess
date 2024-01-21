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

        moveCheck(1,2,this.myPosition,this.myPosition,collection, false);
        moveCheck(2,1,this.myPosition,this.myPosition,collection, false);
        moveCheck(-1,2,this.myPosition,this.myPosition,collection, false);
        moveCheck(2,-1,this.myPosition,this.myPosition,collection, false);
        moveCheck(1,-2,this.myPosition,this.myPosition,collection, false);
        moveCheck(-2,1,this.myPosition,this.myPosition,collection, false);
        moveCheck(-1,-2,this.myPosition,this.myPosition,collection, false);
        moveCheck(-2,-1,this.myPosition,this.myPosition,collection, false);

        return collection;
    }
}
