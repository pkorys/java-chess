import java.util.ArrayList;
import java.util.Scanner;

public class Pawn extends Piece{
    boolean haveMoved = false; //Did pawn moved before?
    int moveDirection;

    public Pawn(String color){
        super("pawn", color);

        //Setting right moving direction
        if (getColor() == "White")
            moveDirection = -1;
        else
            moveDirection = 1;
    }

    public boolean canMove(ArrayList<Field> fields) {
        int verticalMove = fields.get(0).getPos()[1] - fields.get(fields.size()-1).getPos()[1]; //Distance pawn want to travel vertical
        int horizontalMove = fields.get(0).getPos()[0] - fields.get(fields.size()-1).getPos()[0]; //Distance pawn want to travel horizontal

        if (horizontalMove == 0) { //Pawn want to move in vertical axis
            return moveVertical(verticalMove, fields);
        }
        else if (horizontalMove == 1 || horizontalMove == -1) { //Pawn is trying to move diagonally to capture piece
            return capture(verticalMove, fields);
        }
        else return false;
    }

    private boolean moveVertical(int verticalMove, ArrayList<Field> fields){
        if (!haveMoved) { //Pawn didn't move before
            if (verticalMove == moveDirection && fields.get(fields.size()-1).getPieceAtField() == null) { //Pawn want to move one or two fields and these fields are empty
                haveMoved = true;
                return true;
            }
            else if(verticalMove == 2*moveDirection ){
                for(int i = 1; i < fields.size(); i++){
                    if(fields.get(i).getPieceAtField() != null)
                        return false;
                }
                haveMoved = true;
                return true;
            }
            else return false;
        }
        else {
            if (verticalMove == moveDirection && fields.get(fields.size()-1).getPieceAtField() == null){ //Pawn moved before and now wants to go on next field
                promotion(fields);
                return true;
            }

            else return false;
        }
    }

    private boolean capture(int verticalMove, ArrayList<Field> fields){
        if (fields.get(fields.size()-1).getPieceAtField() == null)
            return false;
        if (verticalMove == moveDirection) {
            if (fields.get(fields.size()-1).getPieceAtField().getColor() == getColor())
                return false;
            else{
                promotion(fields);
                return true;
            }
        } else return false;
    }

    private void promotion(ArrayList<Field> field){ //Try to promote pawn
        if(!canPromote(field.get(field.size()-1)))
            return;
        else{
            Scanner input = new Scanner(System.in);
            String piece;
            System.out.println("PROMOTION!");
            do{
                System.out.print("Choose new piece: ");
                piece = input.next();
            }while (!isPiece(piece.charAt(0)));

            switch (piece.charAt(0)){
                case 'k':
                    field.get(0).setPieceAtField(new Knight(getColor()));
                    break;
                case 'b':
                    field.get(0).setPieceAtField(new Bishop(getColor()));
                    break;
                case 'r':
                    field.get(0).setPieceAtField(new Rook(getColor()));
                    break;
                case 'q':
                    field.get(0).setPieceAtField(new Queen(getColor()));
                    break;
            }
        }
    }

    private boolean canPromote(Field field){
        if(getColor() == "White" && field.getPos()[1] == 8)
            return true;
        else if(getColor() == "Black" && field.getPos()[0] == 1)
            return true;
        else return false;
    }

    private boolean isPiece(char fig){
        switch (fig){
            case 'k': case 'b': case 'r': case 'q':
                return true;
            default:
                return false;
        }
    }

}
