//Written by Piotr Korys, Lublin 2021

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pl.piotrkorys.chess.*;

public class Chess {
    private static Scanner input = new Scanner(System.in);
    private static Board chessboard = new Board();
    private static String turn;
    private static String piece;
    private static String move;
    private static List<Field> fieldsToTravel = new ArrayList<Field>();

    public static void main(String[] args) {
        startGame();
        do{
            playRound();
        }while(!chessboard.isCheck() || !CheckmateController.isCheckMate(chessboard, turn));
        chessboard.printBoard();
        changeTurn();
        System.out.println("Checkmate! " + turn + " wins!");
    }

    private static void startGame(){
        Piece.setChessboard(chessboard);
        turn = "White";
    }

    private static void playRound(){
        chessboard.fixPositions();
        chessboard.makeUnchecking();
        chessboard.makeChecking();
        fieldsToTravel.clear();
        chessboard.removeEnPassant(turn);
        chessboard.printBoard();
        if(chessboard.isCheck())
            System.out.println("CHECK");
        choosePiece();
        changeTurn();
    }

    private static void choosePiece(){
        System.out.println("-=-=-=-" + turn + " turn-=-=-=-");
        do{
            System.out.print("Choose piece to move: ");
            piece = input.next();
        }while(!chessboard.isMyFigure(piece, turn));
        fieldsToTravel.add(chessboard.getField(piece));
        chooseFieldToMove();
    }

    private static void chooseFieldToMove() {
        do {
            System.out.print("Choose field to move: ");
            move = input.next().toLowerCase();
            if (move.charAt(0) == 'q')
                break;
        } while (!chessboard.canIMoveTo(move, turn));
        analyzeInput();
    }
    private static void analyzeInput(){
        if(move.charAt(0) != 'q'){
            fieldsToTravel.add(chessboard.getField(move));
            chessboard.addFields(fieldsToTravel);
            if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel, false)){
                    Board temp = new Board(chessboard);
                    fieldsToTravel.clear();
                    fieldsToTravel.add(temp.getField(piece));
                    fieldsToTravel.add(temp.getField(move));
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    temp.swapPieces(fieldsToTravel);
                    temp.makeUnchecking();
                    temp.makeChecking();
                    if(temp.isMyKingChecked(turn)){
                        fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().setChessboard(chessboard);
                        chessboard.fixPositions();
                        quitMove();
                    }
                    else {
                        chessboard = temp;
                        fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().setChessboard(chessboard);
                        if(fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField() instanceof Pawn){
                            if(((Pawn)fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField()).canPromote())
                                ((Pawn) fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField()).pawnPromotion();
                        }
                    }
            }
            else
                tryMakeMoveAgain();
        }
        else
            quitMove();
    }

    private static void quitMove(){
        fieldsToTravel.clear();
        choosePiece();
    }

    private static void tryMakeMoveAgain(){
        Field temp = fieldsToTravel.get(0);
        fieldsToTravel.clear();
        fieldsToTravel.add(temp);
        chooseFieldToMove();
    }

    private static void changeTurn(){
        if(turn.equals("White"))
            turn = "Black";
        else
            turn = "White";
    }
}