package chess;


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

    private int passInt = 0;

    private ChessBoard board;
    private ChessGame.TeamColor color;
    private ChessMove lastMove;
    private ChessPiece lastMoveDestinationPiece;

    //For phase 5 only
    private int gameID;
    private String gameName;
    private String whiteUsername;
    private String blackUsername;
    private boolean gameOver;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public int getGameId() {
        return gameID;
    }

    public void setGameId(int gameId) {
        this.gameID = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

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

    public void toggleTeamTurn() {
        if(this.color.equals(TeamColor.WHITE)){
            this.color = TeamColor.BLACK;
        }else{
            this.color = TeamColor.WHITE;
        }
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

        if(this.lastMove.getPromotionPiece() != null){
            this.board.addPiece(this.lastMove.getStartPosition(), new ChessPiece(this.color, ChessPiece.PieceType.PAWN));
        }else{
            this.board.addPiece(this.lastMove.getStartPosition(), this.board.getPiece(this.lastMove.getEndPosition()));
        }

        this.board.addPiece(this.lastMove.getEndPosition(), this.lastMoveDestinationPiece);

        this.lastMove = null;
        this.lastMoveDestinationPiece = null;
        this.switchTurn();
    }

    //Makes move without any checking
    private void executeMove(ChessMove move){
        this.lastMoveDestinationPiece = this.board.getPiece(move.getEndPosition());
        if(move.getPromotionPiece() != null){
            this.board.addPiece(move.getEndPosition(), new ChessPiece(this.color,move.getPromotionPiece()));
        }else{
            this.board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        }
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

        Collection<ChessMove> validMoves = new ArrayList<>();

        //If piece is null return null
        ChessPiece pieceAtPosition = this.board.getPiece(startPosition);

        if(pieceAtPosition == null){
            return null;
        }

        ChessGame.TeamColor currentColor = pieceAtPosition.getTeamColor();

        //✅Create global Variable for the last move made
        //✅Implement undo move functionality - probably should handle turn color
        //✅Write a simple function to actually move pieces on the board - no checks
        //✅Also probably should handle turn color
        //✅Create Local variable for team color

        //✅Get every single possible move for that piece
        Collection<ChessMove> potentialMoves = pieceAtPosition.pieceMoves(this.board, startPosition);

        //If there is no king on the board then it's impossible to be in check
        if(this.findKing(currentColor) == null){
            return potentialMoves;
        }

        //✅For each move
        //✅Make the hypothetical move,
        //✅If the team is not in check add that move to the list of valid moves
        //✅undo the move
        ChessMove holder;
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

        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();

        //Get piece at original position
        ChessPiece selectedPiece = board.getPiece(startPosition);

        //Check if null at that position
        if(selectedPiece == null){
            throw new InvalidMoveException();

        } else if(selectedPiece.getTeamColor() != this.color){
            throw new InvalidMoveException();
        }

        Collection<ChessMove> validMoves = this.validMoves(startPosition);

        Iterator<ChessMove> iterator = validMoves.iterator();

        ChessMove currentMove;

        boolean foundMove = false;

        while(iterator.hasNext()){
            currentMove = iterator.next();
            if((currentMove.getEndPosition().getRow() == endPosition.getRow()) && (currentMove.getEndPosition().getColumn() == endPosition.getColumn())){
                foundMove = true;
                this.executeMove(move);
                break;
            }
        }

        //Throw error if there is a null or move is invalid there
        if(!foundMove){
            throw new InvalidMoveException();
        }

        this.passInt ++;
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

    //returns true if one of a piece's valid moves ends at a given position
    private boolean pieceContainsMove(ChessPiece currentPiece, ChessPosition currentPosition, ChessPosition targetPosition) {

        Collection<ChessMove> collection = currentPiece.pieceMoves(this.board, new ChessPosition(currentPosition.getRow(), currentPosition.getColumn()));

        Iterator<ChessMove> iterator = collection.iterator();

        ChessMove currentMove;

        boolean foundMove = false;

        while (iterator.hasNext()) {
            currentMove = iterator.next();
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

        if(kingPosition == null) {

        }

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
        return isInCheckmate(teamColor);
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
