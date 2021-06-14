package pl.piotrkorys.chess;

public class Bishop extends Piece {
    public Bishop(String color, Field myPosition){
        super("bishop", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        if(isBishopMovingDiagonally())
            return isWayClear();
        else return false;
    }

    private boolean isBishopMovingDiagonally(){
        if(Math.abs(getMoveInHorizontal()) != Math.abs(getMoveInVertical()))
            return false;
        else return true;
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1, 1};
        for (int i = 0; i < ones.length; i++) {
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