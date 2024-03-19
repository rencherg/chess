package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class PrintBoard {

    public void printBoard(ChessBoard board){

        out.print(SET_TEXT_BOLD);

        System.out.println();

        System.out.println("White Board:");
        printHeader(true);
        printRows(board, true);
        printHeader(true);

        System.out.println();

        System.out.println("Black Board:");
        printHeader(false);
        printRows(board, false);
        printHeader(false);

        out.print(RESET_TEXT_BOLD_FAINT);
        System.out.println();
    }

    private void printHeader(boolean isWhite) {
        out.print(SET_BG_COLOR_BLUE);
        out.print(SET_TEXT_COLOR_BLACK);

        if (isWhite) {
            System.out.print("    a  b  c  d  e  f  g  h    ");
        } else {
            System.out.print("    h  g  f  e  d  c  b  a    ");
        }

        out.print(SET_BG_COLOR_BRIGHT_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
        System.out.println();
    }

    private void printRows(ChessBoard board, boolean isWhite){

        if(isWhite){

            boolean bgWhite = true;

            for(int i = 8; i > 0; i--){
                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                for(int j = 0; j < 8; j++){
                    ChessPiece currentPiece = board.getPiece(new ChessPosition(i, j+1));
                    if(bgWhite){
                        out.print(SET_BG_COLOR_WHITE);
                    }else{
                        out.print(SET_BG_COLOR_LIGHT_GREY);
                    }
                    if(currentPiece == null){
                        System.out.print("   ");
                    }else if(currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" " + this.getPieceChar(currentPiece) + " ");
                    }else{
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" " + this.getPieceChar(currentPiece) + " ");
                    }

                    bgWhite = !bgWhite;
                }

                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                out.print(SET_BG_COLOR_BRIGHT_WHITE);
                out.print(SET_TEXT_COLOR_WHITE);
                System.out.println();
                bgWhite = !bgWhite;
            }
        }else{

            boolean bgWhite = false;

            for(int i = 1; i < 9; i++){
                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                for(int j = 1; j < 9; j++){
                    ChessPiece currentPiece = board.getPiece(new ChessPosition(i, j));
                    if(bgWhite){
                        out.print(SET_BG_COLOR_WHITE);
                    }else{
                        out.print(SET_BG_COLOR_LIGHT_GREY);
                    }
                    if(currentPiece == null){
                        System.out.print("   ");
                    }else if(currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(SET_TEXT_COLOR_BLUE);
                        System.out.print(" " + this.getPieceChar(currentPiece) + " ");
                    }else{
                        out.print(SET_TEXT_COLOR_RED);
                        System.out.print(" " + this.getPieceChar(currentPiece) + " ");
                    }

                    bgWhite = !bgWhite;
                }

                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                out.print(SET_BG_COLOR_BRIGHT_WHITE);
                out.print(SET_TEXT_COLOR_WHITE);
                System.out.println();
                bgWhite = !bgWhite;
            }
        }

    }

    private char getPieceChar(ChessPiece piece){
        char pieceChar = ' ';
        switch(piece.getPieceType()){
            case KING:
                pieceChar = 'K';
                break;
            case PAWN:
                pieceChar = 'P';
                break;
            case QUEEN:
                pieceChar = 'Q';
                break;
            case ROOK:
                pieceChar = 'R';
                break;
            case BISHOP:
                pieceChar = 'B';
                break;
            case KNIGHT:
                pieceChar = 'N';
                break;
        }
        return pieceChar;
    }

}
