package pl.piotrkorys.chess;
import java.util.ArrayList;
import java.util.Scanner;

public class Pawn extends Piece {
    final int moveDirection;
    Field destinationField;
    boolean isItTest;

    public Pawn(String color, Field myPosition){
        super("pawn", color, myPosition);

        if (getPieceColor() == "White")
            moveDirection = 1;
        else
            moveDirection = -1;
    }

    @Override
    public boolean canMove(ArrayList<Field> fieldsToMove, boolean isItCheckmateTest) {
        isItTest = isItCheckmateTest;
        return super.canMove(fieldsToMove, isItCheckmateTest);
    }

    @Override
    protected boolean isMoveCorrect() {
        destinationField = fieldsToMove.get(fieldsToMove.size()-1);
        if(canMakeMoveForward())
            return isWayClear();
        else if(canCapture())
            return true;
        else return false;
    }

    private boolean canMakeMoveForward(){
        if(getMoveInHorizontal() == 0)
            return (canMakeExtraMove() || canMakeNormalMove());
        else return false;
    }

    private boolean canMakeExtraMove(){
        if(isHaveMovedBefore())
            return false;
        else if(getMoveInVertical() == 2*moveDirection){
            chessboard.findField(myPosition.getHorizontalPosition(),myPosition.getVerticalPosition()+moveDirection).setEnPassant(this);
            return true;
        }
        else return false;
    }

    private boolean canMakeNormalMove(){
        if(getMoveInVertical() == moveDirection)
            return true;
        else return false;
    }

    private boolean canCapture(){
        if(destinationField.getPieceAtField() == null){
            if(destinationField.getEnPassant() != null){
                chessboard.findField(myPosition.getHorizontalPosition() + getMoveInHorizontal(), myPosition.getVerticalPosition()).setPieceAtField(null);
                return true;
            }
            return false;
        }
        if(getMoveInVertical() == moveDirection && Math.abs(getMoveInHorizontal()) == 1) {
            return true;
        }
        else return false;
    }

    public void pawnPromotion(){
            Scanner input = new Scanner(System.in);
            String inputedPiece;
            System.out.println("PROMOTION!");
            do{
                System.out.print("Choose new piece: ");
                inputedPiece = input.next().toLowerCase();
            }while (!isPiece(inputedPiece.charAt(0)));

            changePieceAfterPromotion(inputedPiece.charAt(0));
    }

    public boolean canPromote(){
        if(getPieceColor() == "White" && destinationField.getVerticalPosition() == 8)
            return true;
        else if(getPieceColor() == "Black" && destinationField.getVerticalPosition() == 1)
            return true;
        else return false;
    }

    @Override
    protected boolean isWayClear() {
        for(int i = 1; i < fieldsToMove.size(); i++){
            if(fieldsToMove.get(i).getPieceAtField() != null)
                return false;
        }
        if(!isHaveMovedBefore())
            setHaveMovedBefore(true);
        return true;
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();

        checkIfFieldExists(myHorizontalPosition -1, myVerticalPosition+moveDirection);
        checkIfFieldExists(myHorizontalPosition +1, myVerticalPosition+moveDirection);

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

    private boolean isPiece(char firstLetterOfPiece){
        switch (firstLetterOfPiece){
            case 'k': case 'b': case 'r': case 'q':
                return true;
            default:
                return false;
        }
    }
}