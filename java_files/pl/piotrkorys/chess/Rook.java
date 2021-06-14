package pl.piotrkorys.chess;

public class Rook extends Piece {

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
        if(super.isWayClear() && !isHaveMovedBefore())
            setHaveMovedBefore(true);
        return super.isWayClear();
    }

    private boolean isRookMovingInAStraightLine(){
        if(getMoveInHorizontal() != 0 && getMoveInVertical() != 0)
            return false;
        else return true;
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1,1};
        for (int i = 0; i < ones.length; i++){
            checkInStraightLine(ones[i], 0);
            checkInStraightLine(0, ones[i]);
        }
    }

    private void checkInStraightLine(int additionalHorizontal, int additionalVertical){
        Field nextField = new Field(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition());
        do{
            nextField = chessboard.findField(nextField.getHorizontalPosition()+additionalHorizontal,nextField.getVerticalPosition()+additionalVertical);
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