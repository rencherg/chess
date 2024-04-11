package ui;

import chess.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class PrintBoard {

    private Scanner scanner = new Scanner(System.in);

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

            boolean bgWhite = true;

            for(int i = 1; i < 9; i++){
                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                for(int j = 8; j > 0; j--){
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

    public void highlightLegalMoves(ChessBoard board){
        ChessGame game = new ChessGame();
        game.setBoard(board);
        ChessPosition position = getValidPosition();
        if(position == null){
            return;
        }
        Collection<ChessMove> validMoves = game.validMoves(position);
        if(validMoves == null){
            System.out.println("Selected position had no piece");
        }else{
            printBoardHighlighted(board, position, validMoves);
        }
    }

    private ChessPosition getValidPosition() {

        boolean continueAsking = true;

        while(continueAsking){
            System.out.println("Type the position of the piece you would like to see (ex: a1) OR type Q to cancel");
            String input = scanner.nextLine();

            if(input.toUpperCase().equals("Q")){
                return null;
            }else{
                char firstChar = input.charAt(0);
                char secondChar = input.charAt(1);

                if ((input == null) || (input.equals("")) || (input.length() != 2) || (firstChar < 'a' || firstChar > 'h') || (secondChar < '1' || secondChar > '8')){
                    return null;
                }

                ChessPosition chessPosition = new ChessPosition(secondChar - '0', firstChar - 'a' + 1);

                return chessPosition;
            }
        }

        return null;
    }

    private void printBoardHighlighted(ChessBoard board, ChessPosition position, Collection<ChessMove> validMoves){

        out.print(SET_TEXT_BOLD);

        System.out.println();

        System.out.println("White Board:");
        printHeader(true);
        printRowsHighlighted(board, true, position, validMoves);
        printHeader(true);

        System.out.println();

        System.out.println("Black Board:");
        printHeader(false);
        printRowsHighlighted(board, false, position, validMoves);
        printHeader(false);

        out.print(RESET_TEXT_BOLD_FAINT);
        System.out.println();

        //print board, when a destination is printed, if it's the same as something in the list the highlight it
    }

    private void printRowsHighlighted(ChessBoard board, boolean isWhite, ChessPosition position, Collection<ChessMove> validMoves){

        if(isWhite){

            boolean bgWhite = true;

            for(int i = 8; i > 0; i--){
                out.print(SET_BG_COLOR_BLUE);
                out.print(SET_TEXT_COLOR_BLACK);
                System.out.print(" " + i + " ");

                for(int j = 0; j < 8; j++){
                    ChessPosition currentPosition = new ChessPosition(i, j+1);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    if(checkForMoveMatch(currentPosition, validMoves)){
                        out.print(SET_BG_COLOR_GREEN);
                    }else if(currentPosition.getColumn() == position.getColumn() && currentPosition.getRow() == position.getRow()){
                        out.print(SET_BG_COLOR_YELLOW);
                    }else if(bgWhite){
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

                for(int j = 8; j > 0; j--){
                    ChessPosition currentPosition = new ChessPosition(i, j);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    if(checkForMoveMatch(currentPosition, validMoves)){
                        out.print(SET_BG_COLOR_GREEN);
                    }else if(currentPosition.getColumn() == position.getColumn() && currentPosition.getRow() == position.getRow()){
                        out.print(SET_BG_COLOR_YELLOW);
                    }else if(bgWhite){
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

    private boolean checkForMoveMatch(ChessPosition position, Collection<ChessMove> validMoves){

        Iterator<ChessMove> moveIterator = validMoves.iterator();

        while(moveIterator.hasNext()){
            ChessMove currentMove = moveIterator.next();
            ChessPosition currentPosition = currentMove.getEndPosition();
            if(currentPosition.getColumn() == position.getColumn() && currentPosition.getRow() == position.getRow()){
                return true;
            }
        }
        return false;
    }
}