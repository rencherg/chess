package chess;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Watchable;
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
    private ChessMove lastMove;
    private ChessPiece lastMoveDestinationPiece;

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

    private void undoMove(){

        this.board.addPiece(this.lastMove.getStartPosition(), this.board.getPiece(this.lastMove.getEndPosition()));
        this.board.addPiece(this.lastMove.getEndPosition(), this.lastMoveDestinationPiece);

        this.lastMove = null;
        this.lastMoveDestinationPiece = null;
        this.switchTurn();
    }

    private void executeMove(ChessMove move){
        this.lastMoveDestinationPiece = this.board.getPiece(move.getEndPosition());
        this.board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        this.board.addPiece(move.getStartPosition(), null);
        this.lastMove = move;
        this.switchTurn();
    }

    private void switchTurn(){
        if (this.color == TeamColor.BLACK){
            this.color = TeamColor.WHITE;
        }else{
            this.color = TeamColor.BLACK;
        }
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition){

        ChessGame.TeamColor currentColor = board.getPiece(startPosition).getTeamColor();

        //Return all valid moves that will result in the team not being in check after the move
        //How?

        Collection<ChessMove> validMoves = new ArrayList<>();

        //If piece is null return null
        ChessPiece pieceAtPosition = this.board.getPiece(startPosition);

        if(pieceAtPosition == null){
            return null;
        }

        //✅Create global Variable for the last move made
        //✅Implement undo move functionality - probably should handle turn color
        //✅Write a simple function to actually move pieces on the board - no checks
        //✅Also probably should handle turn color
        //✅Create Local variable for team color

        //✅Get every single possible move for that piece
        Collection<ChessMove> potentialMoves = pieceAtPosition.pieceMoves(this.board, startPosition);

        //✅For each move
        //✅Make the hypothetical move,
        //✅If the team is not in check add that move to the list of valid moves
        //✅undo the move
        ChessMove holder = null;
        Iterator<ChessMove> moveIterator = potentialMoves.iterator();
        while(moveIterator.hasNext()){
            holder = moveIterator.next();
            this.executeMove(holder);
            if(!this.isInCheck(currentColor)){
                validMoves.add(holder);
            }
            this.undoMove();
        }

        //✅Return the list of valid moves.
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        //How to do this function -
        //1. How does chessmove work with whos color it is?
        //It seems like this method should change the turn color after a move
        //Also reject out of turn move
        //2. Implement this LAST
        //3. Another method will handle the actual piece changing
        //4. Pretty much get the list of valid moves and if the given move isn't one then throw error

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
//            System.out.println(currentMove.getEndPosition().getRow() + " " + currentMove.getEndPosition().getColumn());
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
    public boolean isInCheck(@NotNull TeamColor teamColor) {

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

        Collection<ChessMove> allValidMoves = new ArrayList<>();

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){

                ChessPiece pieceAtPosition = this.board.getPiece(new ChessPosition(i, j));
                if((pieceAtPosition != null )&&(pieceAtPosition.getTeamColor() == teamColor)){
                    allValidMoves.addAll(this.validMoves(new ChessPosition(i, j)));
                }
            }
        }
        return (allValidMoves.isEmpty());
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //No moves at all are reported and it is our turn
        //Maybe the same as isInCheckmate?
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        //Does it need to be a deep copy?
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
