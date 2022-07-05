package pl.piotrkorys.chess;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(String color, Field myPosition) {
        super("King", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        return !isDestinationFieldChecked() && (isCastling() || isMakingNormalMove());
    }

    private boolean isDestinationFieldChecked() {
        Field destinationField = fieldsToMove.get(fieldsToMove.size() - 1);
        return destinationField.isEnemyChecking(getPieceColor());
    }

    private boolean isCastling() {
        if (canMakeCastling()) {
            makeCastling();
            setHaveMovedBefore(true);
            return true;
        }
        return false;
    }

    private boolean isMakingNormalMove() {
        if (canMakeNormalMove()) {
            setHaveMovedBefore(true);
            return true;
        }
        return false;
    }

    private boolean canMakeNormalMove() {
        return (Math.abs(getMoveInVertical()) <= 1 && Math.abs(getMoveInHorizontal()) <= 1);
    }

    private boolean canMakeCastling() {
        if (Math.abs(getMoveInHorizontal()) == 2)
            return areFieldsUnchecked() && canMakeShortCastling() && canMakeLongCastling();

        return false;
    }

    private boolean areFieldsUnchecked() {
        for (Field field : fieldsToMove)
            if (field.isEnemyChecking(getPieceColor()))
                return false;
        return true;
    }

    private boolean canMakeShortCastling() {
        if (getMoveInHorizontal() == -2 && chessboard.findField(2, myPosition.getVerticalPosition()).getPieceAtField() == null)
            return canMakeCastling(1);
        return false;
    }

    private boolean canMakeLongCastling() {
        if (getMoveInHorizontal() == 2)
            return canMakeCastling(8);
        return false;
    }

    private boolean canMakeCastling(int rookHorizontal) {
        Piece nearbyRook = chessboard.findField(rookHorizontal, myPosition.getVerticalPosition()).getPieceAtField();
        if (nearbyRook != null && nearbyRook.getPieceType().equals("rook")) {
            return !nearbyRook.haveMovedBefore();
        }
        return false;
    }

    private void makeCastling() {
        switch (getMoveInHorizontal()) {
            case -2 -> makeCastling(1, -1);
            case 2 -> makeCastling(8, 1);
        }
    }

    private void makeCastling(int rookHorizontal, int rookNewHorizontal) {
        Rook rook = (Rook) chessboard.findField(rookHorizontal, myPosition.getVerticalPosition()).getPieceAtField();
        chessboard.swapPieces(createListOfFieldsToTravel(rook, rookNewHorizontal));
    }

    private List<Field> createListOfFieldsToTravel(Rook rook, int rookNewHorizontal) {
        List<Field> pathForRook = new ArrayList<>();
        pathForRook.add(rook.myPosition);
        pathForRook.add(chessboard.findField(myPosition.getHorizontalPosition() + rookNewHorizontal, myPosition.getVerticalPosition()));

        return pathForRook;
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();
        int[] ones = new int[]{-1, 0, 1};
        for (int i : ones) {
            for (int j : ones) {
                if (i == 0 && j == 0)
                    continue;
                checkIfFieldExists(myHorizontalPosition + i, myVerticalPosition + j);
            }
        }
    }
}