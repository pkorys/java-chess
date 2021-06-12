import java.util.ArrayList;

public class Queen extends Piece {
    ArrayList<Field> fieldsToMove;

    public Queen(String color, Field myPosition){
        super("queen", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        if(isQueenMovingDiagonally() || isQueenMovingInAStraightLine())
            return isWayClear();
        else return false;
    }

    private boolean isQueenMovingDiagonally(){
        if(Math.abs(moveInHorizontal()) != Math.abs(moveInVertical()))
            return false;
        else return true;
    }

    private boolean isQueenMovingInAStraightLine(){
        if(moveInHorizontal() != 0 && moveInVertical() != 0)
            return false;
        else return true;
    }

    protected void checkFields() {
        int[] ones = new int[]{-1,1};
        for (int i = 0; i < ones.length; i++){
            checkVertical(ones[i]);
            checkHorizontal(ones[i]);
            for (int j = 0; j < ones.length; j++)
                checkNextField(ones[i], ones[j]);
        }
    }

    private void checkNextField(int i, int j){
        Field nextField = new Field(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition());
        do {
            nextField = chessboard.findField(nextField.getHorizontalPosition()+i, nextField.getVerticalPosition()+j);
            if(nextField == null)
                break;
            checkIfFieldExists(nextField.getHorizontalPosition(),nextField.getVerticalPosition());
        }while (canGoOnThisField(nextField));
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
