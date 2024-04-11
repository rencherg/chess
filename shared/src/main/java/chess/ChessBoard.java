package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece boardArray[][];

    public ChessBoard() {
        boardArray = new ChessPiece[8][8];
//        this.resetBoard();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        this.boardArray[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int rowPosition = position.getRow()-1;
        int colPosition = position.getColumn()-1;
        if(this.boardArray[rowPosition][colPosition] == null){
            return null;
        }else{
            return this.boardArray[position.getRow()-1][position.getColumn()-1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

//        ChessGame.TeamColor pieceColor, ChessPiece.PieceType type
        this.boardArray[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        this.boardArray[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.boardArray[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.boardArray[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        this.boardArray[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        this.boardArray[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        this.boardArray[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        this.boardArray[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        for(int i = 0; i < this.boardArray.length; i++){
            this.boardArray[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        for(int i = 0; i < this.boardArray.length; i++){
            this.boardArray[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        this.boardArray[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        this.boardArray[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.boardArray[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.boardArray[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        this.boardArray[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        this.boardArray[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        this.boardArray[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        this.boardArray[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);


    }

    @Override
    public String toString() {

        String returnString = "";

        for (int i = 0; i < this.boardArray.length; i++){
            for (int j = 0; j < this.boardArray.length; j++){

                if(this.boardArray[i][j] == null) {
                    returnString += ".";
                }else{

                    ChessPiece.PieceType type = this.boardArray[i][j].getPieceType();
                    ChessGame.TeamColor color = this.boardArray[i][j].getTeamColor();


                    switch(color){
                        case WHITE:
                            switch(type) {
                                case KING -> returnString += "K";
                                case PAWN -> returnString += "P";
                                case BISHOP -> returnString += "B";
                                case KNIGHT -> returnString += "N";
                                case QUEEN -> returnString += "Q";
                                case ROOK -> returnString += "R";
                            }
                            break;

                        case BLACK:
                            switch(type) {
                                case KING -> returnString += "k";
                                case PAWN -> returnString += "p";
                                case BISHOP -> returnString += "b";
                                case KNIGHT -> returnString += "n";
                                case QUEEN -> returnString += "q";
                                case ROOK -> returnString += "r";
                            }
                            break;
                    }


                }
            }
            returnString += "\n";
        }
        return returnString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(boardArray, that.boardArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(boardArray);
    }
}
