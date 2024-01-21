package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves extends ChessPieceMoves {

    public QueenMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        moveCheck(1,0,this.myPosition,this.myPosition,collection, true);
        moveCheck(0,1,this.myPosition,this.myPosition,collection, true);
        moveCheck(-1,0,this.myPosition,this.myPosition,collection, true);
        moveCheck(0,-1,this.myPosition,this.myPosition,collection, true);
        moveCheck(1,1,this.myPosition,this.myPosition,collection, true);
        moveCheck(1,-1,this.myPosition,this.myPosition,collection, true);
        moveCheck(-1,1,this.myPosition,this.myPosition,collection, true);
        moveCheck(-1,-1,this.myPosition,this.myPosition,collection, true);

        return collection;
    }
}
