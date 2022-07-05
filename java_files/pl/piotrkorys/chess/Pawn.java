package pl.piotrkorys.chess;

import java.util.List;
import java.util.Scanner;

public class Pawn extends Piece {
    final int moveDirection;
    Field destinationField;
    boolean isItTest;

    public Pawn(String color, Field myPosition) {
        super("pawn", color, myPosition);

        moveDirection = color.equals("White") ? 1 : -1;
    }

    @Override
    public boolean canMove(List<Field> fieldsToMove, boolean isItCheckmateTest) {
        isItTest = isItCheckmateTest;
        return super.canMove(fieldsToMove, isItCheckmateTest);
    }

    @Override
    protected boolean isMoveCorrect() {
        destinationField = fieldsToMove.get(fieldsToMove.size() - 1);

        if (canMakeMoveForward())
            return isWayClear();
        return canCapture();
    }

    private boolean canMakeMoveForward() {
        return getMoveInHorizontal() == 0 && (canMakeExtraMove() || canMakeNormalMove());
    }

    private boolean canMakeExtraMove() {
        if (!haveMovedBefore() && getMoveInVertical() == 2 * moveDirection) {
            chessboard.findField(myPosition.getHorizontalPosition(), myPosition.getVerticalPosition() + moveDirection).setEnPassant(this);
            return true;
        } else return false;
    }

    private boolean canMakeNormalMove() {
        return getMoveInVertical() == moveDirection;
    }

    private boolean canCapture() {
        if (destinationField.getPieceAtField() == null && destinationField.getEnPassant() != null) {
            chessboard.findField(myPosition.getHorizontalPosition() + getMoveInHorizontal(), myPosition.getVerticalPosition()).setPieceAtField(null);
            return true;
        }
        return getMoveInVertical() == moveDirection && Math.abs(getMoveInHorizontal()) == 1;
    }

    public void pawnPromotion() {
        Scanner input = new Scanner(System.in);
        String inputtedPiece;
        System.out.println("PROMOTION!");
        do {
            System.out.print("Choose new piece: ");
            inputtedPiece = input.next().toLowerCase();
        } while (!isPiece(inputtedPiece.charAt(0)));

        changePieceAfterPromotion(inputtedPiece.charAt(0));
    }

    public boolean canPromote() {
        if (getPieceColor().equals("White") && destinationField.getVerticalPosition() == 8)
            return true;
        else return getPieceColor().equals("Black") && destinationField.getVerticalPosition() == 1;
    }

    @Override
    protected boolean isWayClear() {
        for (Field field : fieldsToMove) {
            if (field.getPieceAtField() != null)
                return false;
        }
        if (!haveMovedBefore())
            setHaveMovedBefore(true);
        return true;
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();

        checkIfFieldExists(myHorizontalPosition - 1, myVerticalPosition + moveDirection);
        checkIfFieldExists(myHorizontalPosition + 1, myVerticalPosition + moveDirection);

    }

    private void changePieceAfterPromotion(char firstLetterOfPiece) {
        switch (firstLetterOfPiece) {
            case 'k' -> myPosition.setPieceAtField(new Knight(getPieceColor(), myPosition));
            case 'b' -> myPosition.setPieceAtField(new Bishop(getPieceColor(), myPosition));
            case 'r' -> myPosition.setPieceAtField(new Rook(getPieceColor(), myPosition));
            case 'q' -> myPosition.setPieceAtField(new Queen(getPieceColor(), myPosition));
        }
    }

    private boolean isPiece(char firstLetterOfPiece) {
        return firstLetterOfPiece == 'k' || firstLetterOfPiece == 'b' || firstLetterOfPiece == 'r' || firstLetterOfPiece == 'q';
    }
}