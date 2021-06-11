public class Field {
    private Piece pieceAtField;
    final private int horizontalPosition;
    final private int verticalPosition;

    public Field(int horizontalPosition, int verticalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        pieceAtField = null;
    }

    public Piece getPieceAtField() {
        return pieceAtField;
    }

    public void setPieceAtField(Piece pieceAtField) {
        this.pieceAtField = pieceAtField;
    }

    public void printPiece(){
        if(pieceAtField == null)
            System.out.print(" ");
        else
            System.out.print(pieceAtField.getPieceSymbol());
    }

    public int getHorizontalPosition(){
        return horizontalPosition;
    }

    public int getVerticalPosition(){
        return verticalPosition;
    }

}
