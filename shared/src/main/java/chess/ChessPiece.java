package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        if(board.getPiece(myPosition) == null){
            return null;
        }

        switch(type) {
            case KING:
                KingMoves myKingMoves = new KingMoves(board, myPosition);
                return myKingMoves.getMoves();
            case ROOK:
                RookMoves myRookMoves = new RookMoves(board, myPosition);
                return myRookMoves.getMoves();
            case QUEEN:
                QueenMoves myQueenMoves = new QueenMoves(board, myPosition);
                return myQueenMoves.getMoves();
            case PAWN:
                PawnMoves myPawnMoves = new PawnMoves(board, myPosition);
                return myPawnMoves.getMoves();
            case BISHOP:
                BishopMoves myBishopMoves = new BishopMoves(board, myPosition);
                return myBishopMoves.getMoves();
            case KNIGHT:
                KnightMoves myKnightMoves = new KnightMoves(board, myPosition);
                return myKnightMoves.getMoves();
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}

/*

How to do this?

Check how the


Pawn:

If enemy piece exists in attack place:
(If enemy in row - 1 and col -1 or row -1 and col +1)

If not moved:

if


 */
