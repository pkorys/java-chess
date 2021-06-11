import java.util.ArrayList;
import java.util.Scanner;

public class Chess {
    private static Scanner input = new Scanner(System.in);
    private static Board playArena = new Board();
    private static String turn;

    private static ArrayList<Field> fieldsToTravel = new ArrayList<Field>();

    public static void main(String[] args) {
        startGame();
        while (true) {
            fieldsToTravel.clear();
            chooseFigure();
            changeTurn();
            playArena.printBoard();
        }

    }

    public static void startGame(){
        playArena.setFields();
        playArena.printBoard();
        turn = "White";
    }


    public static void chooseFigure(){
        String move;
        System.out.println("-=-=-=-" + turn + " turn-=-=-=-");
        do{
            System.out.print("Choose figure to move: ");
            move = input.next();
        }while(!playArena.isMyFigure(move, turn));
        fieldsToTravel.add(playArena.getField(move));
        makeMove();
    }

    public static void makeMove(){
        String move;
        do{
            System.out.print("Choose field to move: ");
            move = input.next();
            if(move.charAt(0) == 'q')
                break;
        }while(!playArena.canIMoveTo(move, turn));
        if(move.charAt(0) != 'q'){ //Quit move
            fieldsToTravel.add(playArena.getField(move));
            playArena.addFields(fieldsToTravel);

            if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){ //Make move
                fieldsToTravel.get(fieldsToTravel.size()-1).setPieceAtField(fieldsToTravel.get(0).getPieceAtField());
                fieldsToTravel.get(0).setPieceAtField(null);
            }
            else {
                Field temp = fieldsToTravel.get(0);
                fieldsToTravel.clear();
                fieldsToTravel.add(temp);
                makeMove();
            }
        }
        else {
            fieldsToTravel.clear();
            chooseFigure();
        }
    }


    public static void changeTurn(){
        if(turn == "White")
            turn = "Black";
        else
            turn = "White";
    }

}
