package pl.piotrkorys.chess;
import java.util.ArrayList;

public class Board {
    String name;
    public Field[] fields = new Field[64];
    public Board(){
    }
    public Board(Board otherBoard){
        for(int i =0; i <fields.length; i++) {
            this.fields[i] = new Field(otherBoard.fields[i]);
            this.fields[i].setPieceAtField(otherBoard.fields[i].getPieceAtField());
        }
    }

    public void setFields(){
        for(int i = 0; i < fields.length; i++){
            fields[i] = new Field((i%8)+1, 8-i/8);
        }
        setPieces();
    }

    private void setPieces(){
        for(int i = 0; i < fields.length; i++){
            if(i==0 || i == 7)
                fields[i].setPieceAtField(new Rook("Black", fields[i]));
            else if (i == 56 || i == 63)
                fields[i].setPieceAtField(new Rook("White", fields[i]));
            else if (i == 1 || i == 6)
                fields[i].setPieceAtField(new Knight("Black", fields[i]));
            else if(i == 57 || i == 62)
                fields[i].setPieceAtField(new Knight("White", fields[i]));
            else if(i == 2 || i == 5)
                fields[i].setPieceAtField(new Bishop("Black", fields[i]));
            else if(i == 58 || i == 61)
                fields[i].setPieceAtField(new Bishop("White", fields[i]));
            else if(i == 3)
                fields[i].setPieceAtField(new Queen("Black", fields[i]));
            else if(i == 59)
                fields[i].setPieceAtField(new Queen("White", fields[i]));
            else if(i == 4)
                fields[i].setPieceAtField(new King("Black", fields[i]));
            else if(i == 60)
                fields[i].setPieceAtField(new King("White", fields[i]));
            else if(i > 7 && i < 16)
                fields[i].setPieceAtField(new Pawn("Black", fields[i]));
            else if(i > 47 && i < 56)
                fields[i].setPieceAtField(new Pawn("White", fields[i]));
            else
                fields[i].setPieceAtField(null);
        }
    }

    public void printBoard(){
        System.out.print("    a   b   c   d   e   f   g   h");
        for(int i = 0; i < 64; i++){
            if(i%8==0){
                System.out.println();
                System.out.print((8-i/8) + "  ");
            }

            System.out.print("|");
            fields[i].printPiece();
            System.out.print("| ");
        }
        System.out.println();
    }
    public Field getField(String pos){
        int x = getXFromChar(pos.charAt(0));
        int y;

        if(pos.length() > 1)
            y = Character.getNumericValue(pos.charAt(1));
        else
            y = -1;

        if(y < 1 || y > 8)
            y = -1;

        return findField(x, y);
    }

    private int getXFromChar(char toGetX){
        switch (toGetX){
            case 'a':
                return  1;
            case 'b':
                return  2;
            case 'c':
                return 3;
            case 'd':
                return  4;
            case 'e':
                return  5;
            case 'f':
                return  6;
            case 'g':
                return  7;
            case 'h':
                return 8;
            default:
                return -1;
        }
    }

    public Field findField(int x, int y){
        if(x < 1 || y < 1)
            return null;
        else if(x > 8 || y > 8)
            return null;
        else
            return fields[8*(8-y)+x-1];
    }

    public boolean isMyFigure(String move, String color){
        if(getField(move) == null || getField(move).getPieceAtField() == null){
            return false;
        }

        else if(getField(move).getPieceAtField().getPieceColor() != color){
            return false;
        }

        else
            return true;
    }

    public boolean canIMoveTo(String move, String color){
        if(getField(move) == null){
            return false;
        }
        else if(getField(move).getPieceAtField() != null && getField(move).getPieceAtField().getPieceColor() == color)
            return false;

        else
            return true;
    }

    public void makeChecking(){
        ArrayList<Field> kings = new ArrayList<>();
        for (int i =0; i < fields.length; i++){
            if(fields[i].getPieceAtField()!= null){
                fields[i].getPieceAtField().checkFields();
                if(fields[i].getPieceAtField().getPieceType() == "King")
                    kings.add(fields[i]);
            }
        }
    }

    public void makeUnchecking(){
        for (int i =0; i < fields.length; i++){
           fields[i].removePiecesCheckingMe();
        }
    }

    public void addFields(ArrayList<Field> fieldsToTravel) {
        Field firstField = fieldsToTravel.get(0);
        Field lastField = fieldsToTravel.get(fieldsToTravel.size()-1);
        fieldsToTravel.remove(lastField);
        int moveInVertical = lastField.getVerticalPosition() - fieldsToTravel.get(0).getVerticalPosition();
        int moveInHorizontal = lastField.getHorizontalPosition() - fieldsToTravel.get(0).getHorizontalPosition();

        if(moveInHorizontal == 0){
            if(Math.abs(moveInVertical) == moveInVertical)
                for(int i=1; i <= moveInVertical; i++)
                    fieldsToTravel.add(findField(firstField.getHorizontalPosition(), firstField.getVerticalPosition()+i));
                else
                    for(int i=1; i <=Math.abs(moveInVertical); i++)
                        fieldsToTravel.add(findField(firstField.getHorizontalPosition(), firstField.getVerticalPosition()-i));
        }
        else if(moveInVertical == 0){
            if(Math.abs(moveInHorizontal) == moveInHorizontal)
                for(int i=1; i <= moveInHorizontal; i++)
                    fieldsToTravel.add(findField(firstField.getHorizontalPosition()+i, firstField.getVerticalPosition()));
            else
                for(int i=1; i <=Math.abs(moveInHorizontal); i++)
                    fieldsToTravel.add(findField(firstField.getHorizontalPosition()-i, firstField.getVerticalPosition()));
        }
        else if(Math.abs(moveInHorizontal) == Math.abs(moveInVertical)){
            int verticalDir = moveInVertical / Math.abs(moveInVertical);
            int horizontalDir = moveInHorizontal / Math.abs(moveInHorizontal);
            for (int i = 1; i <= Math.abs(moveInHorizontal); i++)
                fieldsToTravel.add(findField(firstField.getHorizontalPosition()+(horizontalDir*i), firstField.getVerticalPosition()+(verticalDir*i)));
        }
        else fieldsToTravel.add(lastField);
    }

    private boolean isKingChecked(Field field){
            if(field.isEnemyChecking(field.getPieceAtField().getPieceColor()))
                return true;
            else
                return false;
    }

    public boolean isCheck(){
        for(int i =0; i < fields.length; i++){
            if(fields[i].getPieceAtField() != null){
                if(fields[i].getPieceAtField().getPieceType().equals("King"))
                    if(isKingChecked(fields[i]))
                        return true;
            }
        }
        return false;
    }

    public boolean isMyKingChecked(String color){
        for(int i = 0; i < fields.length; i++){
            if(fields[i].getPieceAtField() != null){
                if(fields[i].getPieceAtField().getPieceType().equals("King") && fields[i].getPieceAtField().getPieceColor().equals(color)){
                    if(isKingChecked(fields[i]))
                        return true;
                }
            }
        }
        return false;
    }

    public void swapPieces(ArrayList<Field> fieldsToTravel){
        fieldsToTravel.get(fieldsToTravel.size()-1).setPieceAtField(fieldsToTravel.get(0).getPieceAtField());
        fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().setMyPosition(fieldsToTravel.get(fieldsToTravel.size()-1));
        fieldsToTravel.get(0).setPieceAtField(null);
    }

    public void fixPositions(){
        for(int i = 0; i < fields.length; i++){
            if(fields[i].getPieceAtField() != null)
                fields[i].getPieceAtField().setMyPosition(fields[i]);
        }
    }

    public void removeEnPassant(String color){
        for (int i =0; i <fields.length; i++){
            if(fields[i].getEnPassant() != null){
                if(fields[i].getEnPassant().getPieceColor() == color)
                    fields[i].setEnPassant(null);
            }
        }
    }
}