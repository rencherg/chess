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

        int currentRow = this.myPosition.getRow();
        int currentCol = this.myPosition.getColumn();

//        boolean potentialMoves = true;

        System.out.println(board.toString());

        recursiveCheck(1,1,this.myPosition,this.myPosition,collection);
        recursiveCheck(1,-1,this.myPosition,this.myPosition,collection);
        recursiveCheck(-1,1,this.myPosition,this.myPosition,collection);
        recursiveCheck(-1,-1,this.myPosition,this.myPosition,collection);

        return collection;
    }
}
