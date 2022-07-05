package pl.piotrkorys.chess;

import java.util.Arrays;
import java.util.List;

public class Board {
    String name;
    public Field[] fields = new Field[64];

    public Board() {
        setFields();
    }

    public Board(Board otherBoard) {
        for (int i = 0; i < fields.length; i++) {
            this.fields[i] = new Field(otherBoard.fields[i]);
            this.fields[i].setPieceAtField(otherBoard.fields[i].getPieceAtField());
        }
    }

    public void setFields() {
        for (int i = 0; i < fields.length; i++) {
            int x = (i % 8) + 1;
            int y = 8 - i / 8;

            fields[i] = new Field(x, y);
        }
        setPieces();
    }

    private void setPieces() {
        for (int i = 0; i < fields.length; i++) {
            switch (i) {
                case 0, 7 -> fields[i].setPieceAtField(new Rook("Black", fields[i]));
                case 1, 6 -> fields[i].setPieceAtField(new Knight("Black", fields[i]));
                case 2, 5 -> fields[i].setPieceAtField(new Bishop("Black", fields[i]));
                case 3 -> fields[i].setPieceAtField(new Queen("Black", fields[i]));
                case 4 -> fields[i].setPieceAtField(new King("Black", fields[i]));
                case 56, 63 -> fields[i].setPieceAtField(new Rook("White", fields[i]));
                case 57, 62 -> fields[i].setPieceAtField(new Knight("White", fields[i]));
                case 58, 61 -> fields[i].setPieceAtField(new Bishop("White", fields[i]));
                case 59 -> fields[i].setPieceAtField(new Queen("White", fields[i]));
                case 60 -> fields[i].setPieceAtField(new King("White", fields[i]));
                default -> fields[i].setPieceAtField(null);
            }
            if (i > 7 && i < 16)
                fields[i].setPieceAtField(new Pawn("Black", fields[i]));
            else if (i > 47 && i < 56)
                fields[i].setPieceAtField(new Pawn("White", fields[i]));
        }
    }

    public void printBoard() {
        System.out.print("    a  b  c  d  e  f  g  h");
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.println();
                System.out.print((8 - i / 8) + "  ");
            }

            System.out.print("|");
            fields[i].printPiece();
            System.out.print("|");
        }
        System.out.println();
    }

    public Field getField(String pos) {
        int x = getXFromChar(pos.toLowerCase().charAt(0));
        int y;

        if (pos.length() > 1)
            y = Character.getNumericValue(pos.charAt(1));
        else
            y = -1;

        if (y < 1 || y > 8)
            y = -1;

        return findField(x, y);
    }

    private int getXFromChar(char toGetX) {
        char charsFromZeroToA = 96;
        return toGetX >= 97 && toGetX <= 104 ? toGetX - charsFromZeroToA : -1; //if char is in range 'a' to 'h' return char - 96 (1 for a, 2 for b, etc.)
    }


    public Field findField(int x, int y) {
        if (x < 1 || y < 1)
            return null;
        else if (x > 8 || y > 8)
            return null;
        else
            return fields[8 * (8 - y) + x - 1];
    }

    public boolean isMyFigure(String move, String color) {
        Field target = getField(move);
        return target != null && target.getPieceAtField() != null && target.getPieceAtField().getPieceColor().equals(color);
    }

    public boolean canIMoveTo(String move, String color) {
        Field target = getField(move);
        return target != null && (target.getPieceAtField() == null || !target.getPieceAtField().getPieceColor().equals(color));
    }

    public void makeChecking() {
        for (Field field : fields) {
            if (field.getPieceAtField() != null) {
                field.getPieceAtField().checkFields();
            }
        }
    }

    public void makeUnchecking() {
        for (Field field : fields)
            field.removePiecesCheckingMe();
    }

    public void addFields(List<Field> fieldsToTravel) {
        Field firstField = fieldsToTravel.get(0);
        Field lastField = fieldsToTravel.get(fieldsToTravel.size() - 1);
        fieldsToTravel.remove(lastField);

        int moveInVertical = lastField.getVerticalPosition() - fieldsToTravel.get(0).getVerticalPosition();
        int moveInHorizontal = lastField.getHorizontalPosition() - fieldsToTravel.get(0).getHorizontalPosition();
        boolean isItDiagonalMove = Math.abs(moveInHorizontal) == Math.abs(moveInVertical);

        if (moveInHorizontal == 0)
            addFieldsInHorizontal(fieldsToTravel, firstField, moveInVertical);

        else if (moveInVertical == 0) {
            addFieldsInVertical(fieldsToTravel, firstField, moveInHorizontal);
        } else if (isItDiagonalMove) {
            addFieldsDiagonally(fieldsToTravel, firstField, moveInHorizontal, moveInVertical);
        } else
            fieldsToTravel.add(lastField);
    }

    private void addFieldsInHorizontal(List<Field> fieldsToTravel, Field firstField, int moveInVertical) {
        if (Math.abs(moveInVertical) == moveInVertical)
            for (int i = 1; i <= moveInVertical; i++)
                fieldsToTravel.add(findField(firstField.getHorizontalPosition(), firstField.getVerticalPosition() + i));
        else
            for (int i = 1; i <= Math.abs(moveInVertical); i++)
                fieldsToTravel.add(findField(firstField.getHorizontalPosition(), firstField.getVerticalPosition() - i));
    }

    private void addFieldsInVertical(List<Field> fieldsToTravel, Field firstField, int moveInHorizontal) {
        if (Math.abs(moveInHorizontal) == moveInHorizontal)
            for (int i = 1; i <= moveInHorizontal; i++)
                fieldsToTravel.add(findField(firstField.getHorizontalPosition() + i, firstField.getVerticalPosition()));
        else
            for (int i = 1; i <= Math.abs(moveInHorizontal); i++)
                fieldsToTravel.add(findField(firstField.getHorizontalPosition() - i, firstField.getVerticalPosition()));
    }

    private void addFieldsDiagonally(List<Field> fieldsToTravel, Field firstField, int moveInHorizontal, int moveInVertical) {
        int verticalDir = moveInVertical / Math.abs(moveInVertical);
        int horizontalDir = moveInHorizontal / Math.abs(moveInHorizontal);

        for (int i = 1; i <= Math.abs(moveInHorizontal); i++)
            fieldsToTravel.add(findField(firstField.getHorizontalPosition() + (horizontalDir * i), firstField.getVerticalPosition() + (verticalDir * i)));
    }

    private boolean isKingChecked(Field field) {
        String kingsColor = field.getPieceAtField().getPieceColor();
        return field.isEnemyChecking(kingsColor);
    }

    public boolean isCheck() {
        for (Field field : fields) {
            boolean isKingOnThisField = field.getPieceAtField() != null && field.getPieceAtField().getPieceType().equals("King");

            if (isKingOnThisField) {
                return isKingChecked(field);
            }
        }
        return false;
    }

    public boolean isMyKingChecked(String color) {
        Field kingsField = Arrays.stream(fields)
                .filter(field -> field.getPieceAtField() != null)
                .filter(field -> field.getPieceAtField().getPieceType().equals("King") && field.getPieceAtField().getPieceColor().equals(color))
                .findFirst()
                .get();

        List<Piece> piecesCheckingKing = kingsField.getPiecesCheckingMe();

        return piecesCheckingKing.stream()
                .anyMatch(piece -> !piece.getPieceColor().equals(color));
    }

    public void swapPieces(List<Field> fieldsToTravel) {
        Field lastFieldToTravel = fieldsToTravel.get(fieldsToTravel.size() - 1);

        lastFieldToTravel.setPieceAtField(fieldsToTravel.get(0).getPieceAtField());
        lastFieldToTravel.getPieceAtField().setMyPosition(lastFieldToTravel);
        fieldsToTravel.get(0).setPieceAtField(null);
    }

    public void fixPositions() {
        for (Field field : fields)
            if (field.getPieceAtField() != null)
                field.getPieceAtField().setMyPosition(field);
    }

    public void removeEnPassant(String color) {
        for (Field field : fields) {
            if (field.getEnPassant() != null && field.getEnPassant().getPieceColor().equals(color))
                field.setEnPassant(null);
        }
    }
}