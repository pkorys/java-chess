import java.util.ArrayList;

public class Field {
    private Piece pieceAtField;
    final private int horizontalPosition;
    final private int verticalPosition;
    private ArrayList<Piece> piecesCheckingMe;

    public Field(int horizontalPosition, int verticalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        pieceAtField = null;
        piecesCheckingMe = new ArrayList<Piece>();
    }

    public Piece getPieceAtField() {
        return pieceAtField;
    }

    public void setPieceAtField(Piece pieceAtField) {
        this.pieceAtField = pieceAtField;
        if(pieceAtField != null)
        this.pieceAtField.setMyPosition(this);
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

    public void setPieceCheckingMe(Piece piece){
        piecesCheckingMe.add(piece);
    }

    public void removePiecesCheckigMe(){
                piecesCheckingMe.clear();
    }
    public void printPiecesCheckingMe(){
        for(int i = 0; i < piecesCheckingMe.size(); i++)
            System.out.println(piecesCheckingMe.get(i).getPieceSymbol());
    }

}
