import java.util.ArrayList;

public class Field {
    private Piece pieceAtField;
    final private int horizontalPosition;
    final private int verticalPosition;
    private ArrayList<Piece> piecesCheckingMe;
    private Pawn enPassant;

    public Pawn getEnPassant() {
        return enPassant;
    }

    public void setEnPassant(Pawn enPassant) {
        this.enPassant = enPassant;
    }

    public ArrayList<Piece> getPiecesCheckingMe() {
        return piecesCheckingMe;
    }

    public Field(int horizontalPosition, int verticalPosition) {
        this.horizontalPosition = horizontalPosition;
        this.verticalPosition = verticalPosition;
        pieceAtField = null;
        piecesCheckingMe = new ArrayList<Piece>();
    }

    public Field(Field anotherField){
        this.horizontalPosition = anotherField.getHorizontalPosition();
        this.verticalPosition = anotherField.getVerticalPosition();
        this.pieceAtField = anotherField.getPieceAtField();
        this.piecesCheckingMe = anotherField.piecesCheckingMe;
        this.enPassant = anotherField.getEnPassant();
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
    public boolean isEnemyChecking(String myColor){
        for(int i =0; i < piecesCheckingMe.size(); i++){
            if(piecesCheckingMe.get(i).getPieceColor() != myColor)
                return true;
        }
        return false;
    }
    public void printPiecesCheckingMe(){
        for(int i = 0; i < piecesCheckingMe.size(); i++)
            System.out.println(piecesCheckingMe.get(i).getPieceSymbol());
    }

}
