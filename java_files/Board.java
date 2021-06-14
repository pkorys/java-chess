import java.util.ArrayList;

public class Board {
    String name;
    public Field[] fields = new Field[64];
    public Board(){
    }
    public Board(Board otherBoard){
        for(int i =0; i <fields.length; i++)
            this.fields[i] = new Field(otherBoard.fields[i]);
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
           fields[i].removePiecesCheckigMe();
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
    
    public boolean isCheckMate(String color){
        for (int i = 0; i < fields.length; i++){
            if(fields[i].getPieceAtField() != null){
                if(fields[i].getPieceAtField().getPieceColor().equals(color)){
                    Piece pieceOnField = fields[i].getPieceAtField();
                    switch (pieceOnField.getPieceType()){
                        case "pawn":{
                            if(!isPawnInMate((Pawn) pieceOnField, color))
                                return false;
                            break;
                        }
                        case "knight":{
                            if(!isKnightInMate((Knight) pieceOnField, color))
                                return false;
                            break;
                        }
                        case "bishop":{
                            if(!isBishopInMate((Bishop) pieceOnField, color))
                                return false;
                            break;
                        }
                        case "rook":{
                            if(!isRookInMate((Rook) pieceOnField, color))
                                return false;
                            break;
                        }
                        case "queen":{
                            if(!isQueenInMate((Queen) pieceOnField, color))
                                return false;
                            break;
                        }
                        case "King":{
                            if(!isKingInMate((King) pieceOnField, color))
                                return false;
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isPawnInMate(Pawn pawn, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[][] moves = new int[][]{{0,1}, {0,2}, {-1,1}, {1,1}};
        for (int i = 0; i < moves.length; i++){
            Board temp = new Board(this);
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition(), pawn.myPosition.getVerticalPosition()));
            fieldsToTravel.add(temp.findField(pawn.myPosition.getHorizontalPosition()+moves[i][0], pawn.myPosition.getVerticalPosition()+(moves[i][1]*pawn.moveDirection)));
            if(fieldsToTravel.get(fieldsToTravel.size()-1) != null){
            temp.addFields(fieldsToTravel);
            fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                    temp.swapPieces(fieldsToTravel);
                    temp.makeUnchecking();
                    temp.makeChecking();
                    if(!temp.isMyKingChecked(color))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean isKnightInMate(Knight knight, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1, 1};
        int[] twos = new int[]{-2, 2};

        for (int i = 0; i < ones.length; i++){
            for(int j = 0; j < twos.length; j++) {
                Board temp = new Board(this);
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + ones[i], knight.myPosition.getVerticalPosition() + twos[j]));
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if (fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)) {
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if (!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }

        for (int i = 0; i < ones.length; i++){
            for(int j = 0; j < twos.length; j++) {
                Board temp = new Board(this);
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition(), knight.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(knight.myPosition.getHorizontalPosition() + twos[i], knight.myPosition.getVerticalPosition() + ones[j]));
                fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                if (fieldsToTravel.get(fieldsToTravel.size() - 1) != null) {
                    if (fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)) {
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if (!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isBishopInMate(Bishop bishop, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < fields.length; i++){
            Board temp = new Board(this);
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(bishop.myPosition.getHorizontalPosition(), bishop.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(bishop)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                        if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                            temp.swapPieces(fieldsToTravel);
                            temp.makeUnchecking();
                            temp.makeChecking();
                            if(!temp.isMyKingChecked(color))
                                return false;
                        }
                    }
            }
        }
        return true;
    }

    private boolean isRookInMate(Rook rook, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < fields.length; i++){
            Board temp = new Board(this);
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(rook.myPosition.getHorizontalPosition(), rook.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(rook)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isQueenInMate(Queen queen, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        for (int i = 0; i < fields.length; i++){
            Board temp = new Board(this);
            fieldsToTravel.clear();
            fieldsToTravel.add(temp.findField(queen.myPosition.getHorizontalPosition(), queen.myPosition.getVerticalPosition()));
            for (int j = 0; j < temp.fields[i].getPiecesCheckingMe().size(); j++){
                if(temp.fields[i].getPiecesCheckingMe().get(j).equals(queen)){
                    fieldsToTravel.add(temp.fields[i]);
                    temp.addFields(fieldsToTravel);
                    fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isKingInMate(King king, String color){
        ArrayList<Field> fieldsToTravel = new ArrayList<>();
        int[] ones = new int[]{-1,0,1};
        for (int i = 0; i < ones.length; i++){
            for (int j = 0; j < ones.length; j++){
                if(ones[i] == 0 && ones[j] == 0)
                    continue;
                Board temp = new Board(this);
                int myVerticalPosition = king.myPosition.getVerticalPosition();
                int myHorizontalPosition = king.myPosition.getHorizontalPosition();
                fieldsToTravel.clear();
                fieldsToTravel.add(temp.findField(king.myPosition.getHorizontalPosition(), king.myPosition.getVerticalPosition()));
                fieldsToTravel.add(temp.findField(myHorizontalPosition+ones[i], myVerticalPosition+ones[j]));
                if(fieldsToTravel.get(fieldsToTravel.size()-1) != null){
                temp.addFields(fieldsToTravel);
                fieldsToTravel.get(0).getPieceAtField().setChessboard(temp);
                if(fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField() == null){
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                    }
                }
                else if(fieldsToTravel.get(fieldsToTravel.size()-1).getPieceAtField().getPieceColor() != color){
                    if(fieldsToTravel.get(0).getPieceAtField().canMove(fieldsToTravel)){
                        temp.swapPieces(fieldsToTravel);
                        temp.makeUnchecking();
                        temp.makeChecking();
                        if(!temp.isMyKingChecked(color))
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
