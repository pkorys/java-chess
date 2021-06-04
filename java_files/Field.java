public class Field {
    private Piece pieceAtField;
    final private int xPos;
    final private int yPos;

    public Field(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        pieceAtField = null;
    }

    public Piece getPieceAtField() {
        return pieceAtField;
    }

    public void setPieceAtField(Piece pieceAtField) {
        this.pieceAtField = pieceAtField;
    }

    public void printPiece(){ //Print colored piece symbol or space if field is empty
        if(pieceAtField == null)
            System.out.print(" ");
        else
            System.out.print(pieceAtField.getSymbol());
    }

    public int[] getPos(){
        return new int[]{xPos, yPos};
    }

}
