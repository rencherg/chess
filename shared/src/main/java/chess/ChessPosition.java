package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private int boardRow;
    private int boardCol;

    public ChessPosition(int row, int col) {
        this.boardRow = row;
        this.boardCol = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return boardRow == that.boardRow && boardCol == that.boardCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardRow, boardCol);
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.boardRow;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.boardCol;
    }

    public boolean isValidPosition(){

        if (((this.boardRow > 0) && (this.boardRow < 9)) && ((this.boardCol > 0) && (this.boardCol < 9))){
            return true;
        } else{
            return false;
        }
    }

}
