package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private ChessGame.TeamColor color;

    public ChessGame() {

        board = new ChessBoard();
        board.resetBoard();
        color = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.color = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        //Some moves can't be made due to the King being in Check.

        Collection<ChessMove> collection;

        ChessPiece pieceAtPosition = this.board.getPiece(startPosition);

        if(pieceAtPosition == null){
            return null;
        }

        collection = pieceAtPosition.pieceMoves(this.board, startPosition);

        return collection;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        //Problems to address here -
        //1. How does chessmove work with whos color it is?

        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();

        //Get piece at original position
        ChessPiece pieceOriginalPosition = board.getPiece(startPosition);

        //Check if null at that position
        if(pieceOriginalPosition == null){
            throw new InvalidMoveException();
        } else if(pieceOriginalPosition.getTeamColor() != this.color){
            throw new InvalidMoveException();
        }

        Collection<ChessMove> collection = pieceOriginalPosition.pieceMoves(this.board, startPosition);

        Iterator<ChessMove> iterator = collection.iterator();

        ChessMove currentMove;

        boolean foundMove = false;

        while(iterator.hasNext()){
            currentMove = iterator.next();
            System.out.println(currentMove.getEndPosition().getRow() + " " + currentMove.getEndPosition().getColumn());
            if((currentMove.getEndPosition().getRow() == endPosition.getRow()) && (currentMove.getEndPosition().getColumn() == endPosition.getColumn())){
                foundMove = true;
                this.board.addPiece(endPosition, pieceOriginalPosition);
                this.board.addPiece(startPosition, null);
                break;
            }
        }

        //Throw error if there is a null or move is invalid there
        if(!foundMove){
            throw new InvalidMoveException();
        }
    }

    private ChessPosition findKing(TeamColor teamColor){

//        ChessPosition position;

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
//                position = new ChessPosition(i, j);
                ChessPiece pieceAtPosition = this.board.getPiece(new ChessPosition(i, j));
                if((pieceAtPosition != null )&&(pieceAtPosition.getPieceType() == ChessPiece.PieceType.KING)&&(pieceAtPosition.getTeamColor() == teamColor)){
                    return new ChessPosition(i, j);
                }
            }
        }
        return null;
    }

    //return true if given piece has given position as a possible move
    private boolean pieceContainsMove(ChessPiece currentPiece, ChessPosition currentPosition, ChessPosition targetPosition) {

        Collection<ChessMove> collection = currentPiece.pieceMoves(this.board, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()));

        Iterator<ChessMove> iterator = collection.iterator();

        ChessMove currentMove;

        boolean foundMove = false;

        while (iterator.hasNext()) {
            currentMove = iterator.next();
//            System.out.println(currentMove.getEndPosition().getRow() + " " + currentMove.getEndPosition().getColumn());
            if ((currentMove.getEndPosition().getRow() == targetPosition.getRow()) && (currentMove.getEndPosition().getColumn() == targetPosition.getColumn())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPosition = findKing(teamColor);

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPiece pieceAtPosition = this.board.getPiece(new ChessPosition(i, j));
                if((pieceAtPosition != null )&&(pieceAtPosition.getTeamColor() != teamColor) && pieceContainsMove(pieceAtPosition,new ChessPosition(i, j), kingPosition)){

                    return true;

                }
            }
        }

        return false;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
