import java.util.ArrayList;

public class Rook extends Piece {
    boolean haveRookMovedBefore = false;

    public Rook(String color, Field myPosition){
        super("rook", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect(){
        if(isRookMovingInAStraightLine())
            return isWayClear();
        else return false;
    }

    @Override
    protected boolean isWayClear() {
        if(super.isWayClear() && !haveRookMovedBefore)
            haveRookMovedBefore = true;
        return super.isWayClear();
    }

    private boolean isRookMovingInAStraightLine(){
        if(moveInHorizontal() != 0 && moveInVertical() != 0)
            return false;
        else return true;
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1,1};
        for (int i = 0; i < ones.length; i++){
            checkVertical(ones[i]);
            checkHorizontal(ones[i]);
        }
    }

    private void checkVertical(int i){
        Field nextField = new Field(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition());
        do{
            nextField = chessboard.findField(nextField.getHorizontalPosition(),nextField.getVerticalPosition()+i);
            if(nextField == null)
                break;
            checkIfFieldExists(nextField.getHorizontalPosition(),nextField.getVerticalPosition());
        }while (canGoOnThisField(nextField));
    }

    private void checkHorizontal(int i){
        Field nextField = new Field(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition());
        do{
            nextField = chessboard.findField(nextField.getHorizontalPosition()+i,nextField.getVerticalPosition());
            if(nextField == null)
                break;
            checkIfFieldExists(nextField.getHorizontalPosition(),nextField.getVerticalPosition());
        }while (canGoOnThisField(nextField));
    }

    private boolean canGoOnThisField(Field nextField){
        if(nextField == null)
            return false;
        if(chessboard.findField(nextField.getHorizontalPosition(),nextField.getVerticalPosition()) == null)
            return false;
        else if(nextField.getPieceAtField() == null)
            return true;
        else
            return false;
    }
}
