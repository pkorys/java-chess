import java.util.ArrayList;
import java.util.Scanner;

public class Pawn extends Piece {
    boolean havePawnMovedBefore = false;
    final int moveDirection;
    Field destinationField;

    public Pawn(String color, Field myPosition){
        super("pawn", color, myPosition);

        if (getPieceColor() == "White")
            moveDirection = -1;
        else
            moveDirection = 1;
    }

    @Override
    public boolean canMove(ArrayList<Field> fieldsToMove) {
        destinationField = fieldsToMove.get(fieldsToMove.size()-1);
        return super.canMove(fieldsToMove);
    }

    @Override
    protected boolean isMoveCorrect() {
        if(canPawnMoveCorrect())
            return isWayClear();
        else return false;
    }

    private boolean canPawnMoveCorrect(){
        if(canMakeMoveForward())
            return canMakeMoveForward();
        else return canCapture();
    }

    private boolean canMakeMoveForward(){
        if(moveInHorizontal() == 0)
            return (canMakeExtraMove() || canMakeNormalMove());
        else return false;
    }

    private boolean canMakeExtraMove(){
        if(havePawnMovedBefore)
            return false;
        else if(moveInVertical() == 2*moveDirection)
            return true;
        else return false;
    }

    private boolean canMakeNormalMove(){
        if(moveInVertical() == moveDirection)
            return true;
        else return false;
    }

    private boolean canCapture(){
        if(destinationField.getPieceAtField() == null)
            return false;
        if(moveInVertical() == moveDirection && Math.abs(moveInHorizontal()) == 1)
            return true;
        else return false;
    }

    private void pawnPromotion(){
            Scanner input = new Scanner(System.in);
            String inputedPiece;
            System.out.println("PROMOTION!");
            do{
                System.out.print("Choose new piece: ");
                inputedPiece = input.next();
            }while (!isPiece(inputedPiece.charAt(0)));

            changePieceAfterPromotion(inputedPiece.charAt(0));
    }

    private void changePieceAfterPromotion(char firstLetterOfPiece){
        switch (firstLetterOfPiece){
            case 'k':
                destinationField.setPieceAtField(new Knight(getPieceColor(), destinationField));
                break;
            case 'b':
                destinationField.setPieceAtField(new Bishop(getPieceColor(), destinationField));
                break;
            case 'r':
                destinationField.setPieceAtField(new Rook(getPieceColor(), destinationField));
                break;
            case 'q':
                destinationField.setPieceAtField(new Queen(getPieceColor(), destinationField));
                break;
        }
    }

    private boolean canPromote(){
        if(getPieceColor() == "White" && destinationField.getVerticalPosition() == 8)
            return true;
        else if(getPieceColor() == "Black" && destinationField.getVerticalPosition() == 1)
            return true;
        else return false;
    }

    private boolean isPiece(char firstLetterOfPiece){
        switch (firstLetterOfPiece){
            case 'k': case 'b': case 'r': case 'q':
                return true;
            default:
                return false;
        }
    }

    @Override
    protected boolean isWayClear() {
        if(super.isWayClear() && !havePawnMovedBefore)
            havePawnMovedBefore = true;
        else if(canPromote())
            pawnPromotion();

        return super.isWayClear();
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();

        checkIfFieldExists(myHorizontalPosition -1, myVerticalPosition-moveDirection);
        checkIfFieldExists(myHorizontalPosition +1, myVerticalPosition-moveDirection);

    }
}
