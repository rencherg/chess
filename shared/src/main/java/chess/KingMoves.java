package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves extends ChessPieceMoves {

    public KingMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        nonRecursiveCheck(1,0,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(0,1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-1,0,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(0,-1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(1,1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(1,-1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-1,1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-1,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }
}
