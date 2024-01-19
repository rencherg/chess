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

        int currentRow = this.myPosition.getRow();
        int currentCol = this.myPosition.getColumn();

//        boolean potentialMoves = true;

        System.out.println(board.toString());

        nonRecursiveCheck(1,2,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(2,1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-1,2,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(2,-1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(1,-2,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-2,1,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-1,-2,this.myPosition,this.myPosition,collection);
        nonRecursiveCheck(-2,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }
}
