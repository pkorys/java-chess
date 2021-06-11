import java.util.ArrayList;

public class Board {
    public Field[] fields = new Field[64];

    public void setFields(){
        for(int i = 0; i < fields.length; i++){
            fields[i] = new Field((i%8)+1, 8-i/8);
        }
        setPieces();
    }

    private void setPieces(){
        for(int i = 0; i < fields.length; i++){
            if(i==0 || i == 7)
                fields[i].setPieceAtField(new Rook("Black"));
            else if (i == 56 || i == 63)
                fields[i].setPieceAtField(new Rook("White"));
            else if (i == 1 || i == 6)
                fields[i].setPieceAtField(new Knight("Black"));
            else if(i == 57 || i == 62)
                fields[i].setPieceAtField(new Knight("White"));
            else if(i == 2 || i == 5)
                fields[i].setPieceAtField(new Bishop("Black"));
            else if(i == 58 || i == 61)
                fields[i].setPieceAtField(new Bishop("White"));
            else if(i == 3)
                fields[i].setPieceAtField(new Queen("Black"));
            else if(i == 59)
                fields[i].setPieceAtField(new Queen("White"));
            else if(i == 4)
                fields[i].setPieceAtField(new King("Black"));
            else if(i == 60)
                fields[i].setPieceAtField(new King("White"));
            else if(i > 7 && i < 16)
                fields[i].setPieceAtField(new Pawn("Black"));
            else if(i > 47 && i < 56)
                fields[i].setPieceAtField(new Pawn("White"));
            else
                fields[i].setPieceAtField(null);
        }
    }

    public void printBoard(){
        System.out.print("   a b c d e f g h");
        for(int i = 0; i < 64; i++){
            if(i%8==0){
                System.out.println();
                System.out.print((8-i/8) + "  ");
            }

            fields[i].printPiece();
            System.out.print(" ");
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
        if(x == -1 || y == -1)
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

    public void addFields(ArrayList<Field> fieldsToTravel) {
        int startX = fieldsToTravel.get(0).getHorizontalPosition();
        int destinyX = fieldsToTravel.get(fieldsToTravel.size()-1).getHorizontalPosition();
        int startY = fieldsToTravel.get(0).getVerticalPosition();
        int destinyY = fieldsToTravel.get(fieldsToTravel.size()-1).getVerticalPosition();
        int it = 1;

        if(startX > destinyX){
            for(int i = startX - it; i > destinyX; i--){
                if(startY < destinyY){
                    for(int j = startY+it; j < destinyY; j++)
                        fieldsToTravel.add(fieldsToTravel.size()-1, findField(i,j));
                }
                else{
                    for(int j = destinyY+it; j < startY; j++)
                        fieldsToTravel.add(fieldsToTravel.size()-1, findField(i,j));
                }
                it++;
            }
        }
        else if(startX == destinyX){
            if(startY < destinyY){
                for(int j = startY; j < destinyY; j++)
                    fieldsToTravel.add(fieldsToTravel.size()-1, findField(startX,j));
            }
            else{
                for(int j = startY; j > destinyY; j--)
                    fieldsToTravel.add(fieldsToTravel.size()-1, findField(startX,j));
            }
        }
        else{
            for(int i = startX+it; i < destinyX; i++){
                if(startY > destinyY){
                    for(int j = destinyY+it; j < startY; j++)
                        fieldsToTravel.add(fieldsToTravel.size()-1, findField(i,j));
                }
                else{

                    for(int j = startY+it; j < destinyY; j++)
                        fieldsToTravel.add(fieldsToTravel.size()-1, findField(i,j));
                }
                it++;
            }
        }

        for(int i = 1; i < fieldsToTravel.size(); i++){
            if(fieldsToTravel.get(i) == fieldsToTravel.get(0))
                fieldsToTravel.remove(i);
        }
    }
}
