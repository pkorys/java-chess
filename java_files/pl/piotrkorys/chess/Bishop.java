package pl.piotrkorys.chess;

public class Bishop extends Piece {
    public Bishop(String color, Field myPosition){
        super("bishop", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        return isBishopMovingDiagonally() && isWayClear();
    }

    private boolean isBishopMovingDiagonally(){
        return Math.abs(getMoveInHorizontal()) == Math.abs(getMoveInVertical());
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1, 1};
        for (int i : ones) {
            for (int j : ones) checkNextField(i, j);
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
        if(nextField == null || chessboard.findField(nextField.getHorizontalPosition(),nextField.getVerticalPosition()) == null)
            return false;
        else return nextField.getPieceAtField() == null;
    }
}