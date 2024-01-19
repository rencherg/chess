package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class RookMoves extends ChessPieceMoves {

    public RookMoves(ChessBoard board, ChessPosition myPosition) {
        super(board, myPosition);
    }

    @Override
    public Collection<ChessMove> getMoves() {
        Collection<ChessMove> collection = new ArrayList<ChessMove>();

        recursiveCheck(1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,1,this.myPosition,this.myPosition,collection);
        recursiveCheck(-1,0,this.myPosition,this.myPosition,collection);
        recursiveCheck(0,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }
}