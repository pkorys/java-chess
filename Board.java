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
        System.out.println("   a b c d e f g h");
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
        int x;
        int y;

        if(pos.length() > 1)
            y = Character.getNumericValue(pos.charAt(1));
        else
            y = -1;

        if(y < 1 || y > 8)
            y = -1;

        switch (pos.charAt(0)){
            case 'a':
                x = 1;
                break;
            case 'b':
                x = 2;
                break;
            case 'c':
                x = 3;
                break;
            case 'd':
                x = 4;
                break;
            case 'e':
                x = 5;
                break;
            case 'f':
                x = 6;
                break;
            case 'g':
                x = 7;
                break;
            case 'h':
                x = 8;
                break;
            default:
                x = -1;
                break;
        }

        return findField(x, y);
    }
    public Field findField(int x, int y){
        if(x == -1 || y == -1)
            return null;
        else
            return fields[8*(8-y)+x-1];
    }

    public boolean isMyPiece(String move, String color){
        if(getField(move) == null || getField(move).getPieceAtField() == null){
            return false;
        }

        else if(getField(move).getPieceAtField().getColor() != color){
            return false;
        }

        else
            return true;
    }

    public boolean canIMoveTo(String move, String color){
        if(getField(move) == null){ //Field out of board
            return false;
        }


        else if(getField(move).getPieceAtField() != null && getField(move).getPieceAtField().getColor() == color)
            return false;

        else
            return true;
    }

    public void addFields(ArrayList<Field> fieldsToTravel) {
        int startX = fieldsToTravel.get(0).getPos()[0];
        int destinyX = fieldsToTravel.get(fieldsToTravel.size()-1).getPos()[0];
        int startY = fieldsToTravel.get(0).getPos()[1];
        int destinyY = fieldsToTravel.get(fieldsToTravel.size()-1).getPos()[1];
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
