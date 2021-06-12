import java.util.ArrayList;

public class Piece {
    private static final String changeTextColorOnWhite = "\u001B[0m";
    private static final String changeTextColorOnBlack = "\u001B[30m";

    private String pieceType;
    private String pieceColor;
    private String pieceSymbol;
    protected static Board chessboard;
    protected Field myPosition;
    ArrayList<Field> fieldsToMove;

    public Piece(String pieceType, String pieceColor, Field myPosition) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.myPosition = myPosition;

        if(pieceColor == "Black")
            pieceSymbol = changeTextColorOnBlack + pieceType.charAt(0) + changeTextColorOnWhite;
        else
            pieceSymbol = changeTextColorOnWhite + pieceType.charAt(0);
    }

    public static void setChessboard(Board board){
        chessboard = board;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public String getPieceSymbol() {
        return pieceSymbol;
    }

    public void setMyPosition(Field myPosition) {
        this.myPosition = myPosition;
    }

    public boolean canMove(ArrayList<Field> fieldsToMove) {
        this.fieldsToMove = fieldsToMove;

        return isMoveCorrect();
    }

    protected boolean isMoveCorrect(){
        return isWayClear();
    }

    protected int moveInHorizontal(){
        return fieldsToMove.get(0).getHorizontalPosition() - fieldsToMove.get(fieldsToMove.size()-1).getHorizontalPosition();
    }

    protected int moveInVertical(){
        return fieldsToMove.get(0).getVerticalPosition() - fieldsToMove.get(fieldsToMove.size()-1).getVerticalPosition();
    }

    protected boolean isWayClear(){
        for(int i = 1; i < fieldsToMove.size()-1; i++){
            if(fieldsToMove.get(i).getPieceAtField() != null)
                return false;
        }
        return true;
    }

    protected void checkFields(){

    }

    protected boolean checkIfFieldExists(int horizontal, int vertical){
        if(chessboard.findField(horizontal, vertical) != null) {
            chessboard.findField(horizontal, vertical).setPieceCheckingMe(this);
            return true;
        }
        return false;
    }
}
