//Written by Piotr Korys, Lublin 2021
import java.util.ArrayList;
import java.util.Scanner;

public class Chess {
    private static Scanner input = new Scanner(System.in);
    private static Board chessboard = new Board();
    private static String turn;

    private static ArrayList<Field> fieldsToTravel = new ArrayList<Field>();

    public static void main(String[] args) {
        startGame();
        while (true) {
            chessboard.makeUnchecking();
            chessboard.makeChecking();
            fieldsToTravel.clear();
            chooseFigure();
            changeTurn();
            chessboard.printBoard();

        }

    }

    private static void startGame(){
        chessboard.setFields();
        chessboard.printBoard();
        Piece.setChessboard(chessboard);
        turn = "White";
        chessboard.makeChecking();
    }


    private static void chooseFigure(){
        String move;
        System.out.println("-=-=-=-" + turn + " turn-=-=-=-");
        do{
            System.out.print("Choose piece to move: ");
            move = input.next();
        }while(!chessboard.isMyFigure(move, turn));
        fieldsToTravel.add(chessboard.getField(move));
        makeMove();
    }

    private static void makeMove(){
        String move;
        do{
            System.out.print("Choose field to move: ");
            move = input.next();
            if(move.charAt(0) == 'q')
                break;
        }while(!chessboard.canIMoveTo(move, turn));
        if(move.charAt(0) != 'q'){
            fieldsToTravel.add(chessboard.getField(move));
            chessboard.addFields(fieldsToTravel);

            if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                swapPieces();
            }
            else
                tryMakeMoveAgain();
        }
        else
            quitMove();

    }
    private static void swapPieces(){
        fieldsToTravel.get(fieldsToTravel.size()-1).setPieceAtField(fieldsToTravel.get(0).getPieceAtField());
        fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().setMyPosition(fieldsToTravel.get(fieldsToTravel.size()-1));
        fieldsToTravel.get(0).setPieceAtField(null);
    }

    private static void quitMove(){
        fieldsToTravel.clear();
        chooseFigure();
    }

    private static void tryMakeMoveAgain(){
        Field temp = fieldsToTravel.get(0);
        fieldsToTravel.clear();
        fieldsToTravel.add(temp);
        makeMove();
    }

    private static void changeTurn(){
        if(turn == "White")
            turn = "Black";
        else
            turn = "White";
    }

}
