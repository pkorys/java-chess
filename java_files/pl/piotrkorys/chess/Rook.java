package pl.piotrkorys.chess;

public class Rook extends Piece {

    public Rook(String color, Field myPosition) {
        super("rook", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        if (isRookMovingInAStraightLine())
            return isWayClear();
        else return false;
    }

    @Override
    protected boolean isWayClear() {
        if (super.isWayClear() && !haveMovedBefore())
            setHaveMovedBefore(true);
        return super.isWayClear();
    }

    private boolean isRookMovingInAStraightLine() {
        return getMoveInHorizontal() == 0 || getMoveInVertical() == 0;
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1, 1};
        for (int one : ones) {
            checkInStraightLine(one, 0);
            checkInStraightLine(0, one);
        }
    }

    private void checkInStraightLine(int additionalHorizontal, int additionalVertical) {
        Field nextField = new Field(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition());
        do {
            nextField = chessboard.findField(nextField.getHorizontalPosition() + additionalHorizontal, nextField.getVerticalPosition() + additionalVertical);
            if (nextField == null)
                break;
            checkIfFieldExists(nextField.getHorizontalPosition(), nextField.getVerticalPosition());
        } while (canGoOnThisField(nextField));
    }

    private boolean canGoOnThisField(Field nextField) {
        if (nextField == null || chessboard.findField(nextField.getHorizontalPosition(), nextField.getVerticalPosition()) == null)
            return false;
        else return nextField.getPieceAtField() == null;
    }
}