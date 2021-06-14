import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Pawn extends Piece {
    final int moveDirection;
    Field destinationField;

    public Pawn(String color, Field myPosition){
        super("pawn", color, myPosition);

        if (getPieceColor() == "White")
            moveDirection = 1;
        else
            moveDirection = -1;
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
        else if(canCapture())
            return true;

        else return false;
    }

    private boolean canPawnMoveCorrect(){
        if(canMakeMoveForward())
            return canMakeMoveForward();
        else return false;
    }

    private boolean canMakeMoveForward(){
        if(moveInHorizontal() == 0)
            return (canMakeExtraMove() || canMakeNormalMove());
        else return false;
    }

    private boolean canMakeExtraMove(){
        if(isHaveMovedBefore())
            return false;
        else if(moveInVertical() == 2*moveDirection){
            chessboard.findField(myPosition.getHorizontalPosition(),myPosition.getVerticalPosition()+moveDirection).setEnPassant(this);
            return true;
        }

        else return false;
    }

    private boolean canMakeNormalMove(){
        if(moveInVertical() == moveDirection)
            return true;
        else return false;
    }

    private boolean canCapture(){
        if(destinationField.getPieceAtField() == null){
            if(destinationField.getEnPassant() != null){
                chessboard.findField(myPosition.getHorizontalPosition() + moveInHorizontal(), myPosition.getVerticalPosition()).setPieceAtField(null);
                return true;
            }
            return false;
        }
        if(moveInVertical() == moveDirection && Math.abs(moveInHorizontal()) == 1) {
            if(canPromote())
                pawnPromotion();
            return true;
        }
        else return false;
    }

    private void pawnPromotion(){
            Scanner input = new Scanner(System.in);
            String inputedPiece;
            System.out.println("PROMOTION!");
            do{
                System.out.print("Choose new piece: ");
                inputedPiece = input.next().toLowerCase();
            }while (!isPiece(inputedPiece.charAt(0)));

            changePieceAfterPromotion(inputedPiece.charAt(0));
    }

    private void changePieceAfterPromotion(char firstLetterOfPiece){
        switch (firstLetterOfPiece){
            case 'k':
                myPosition.setPieceAtField(new Knight(getPieceColor(), myPosition));
                break;
            case 'b':
                myPosition.setPieceAtField(new Bishop(getPieceColor(), myPosition));
                break;
            case 'r':
                myPosition.setPieceAtField(new Rook(getPieceColor(), myPosition));
                break;
            case 'q':
                myPosition.setPieceAtField(new Queen(getPieceColor(), myPosition));
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
        for(int i = 1; i < fieldsToMove.size(); i++){
            if(fieldsToMove.get(i).getPieceAtField() != null)
                return false;
        }
        if(!isHaveMovedBefore())
            setHaveMovedBefore(true);
        if(canPromote())
            pawnPromotion();
        return true;
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();

        checkIfFieldExists(myHorizontalPosition -1, myVerticalPosition+moveDirection);
        checkIfFieldExists(myHorizontalPosition +1, myVerticalPosition+moveDirection);

    }
}
