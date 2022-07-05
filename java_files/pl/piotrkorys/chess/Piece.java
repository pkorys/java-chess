package pl.piotrkorys.chess;
import java.util.List;

public class Piece {
    private static final String changeTextColorToWhite = "\u001B[0m";
    private static final String changeTextColorOnBlack = "\u001B[30m";

    private final String pieceType;
    private final String pieceColor;
    private final String pieceSymbol;
    protected static Board chessboard;
    protected Field myPosition;
    List<Field> fieldsToMove;
    private boolean haveMovedBefore;

    public Piece(String pieceType, String pieceColor, Field myPosition) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.myPosition = myPosition;
        this.haveMovedBefore = false;

        if(pieceColor.equals("Black"))
            pieceSymbol = changeTextColorOnBlack + pieceType.charAt(0) + changeTextColorToWhite;
        else
            pieceSymbol = changeTextColorToWhite + pieceType.charAt(0);
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getPieceSymbol() {
        return pieceSymbol;
    }

    protected int getMoveInHorizontal(){
        return fieldsToMove.get(fieldsToMove.size()-1).getHorizontalPosition() - fieldsToMove.get(0).getHorizontalPosition();
    }

    protected int getMoveInVertical(){
        return fieldsToMove.get(fieldsToMove.size()-1).getVerticalPosition() - fieldsToMove.get(0).getVerticalPosition();
    }

    public static void setChessboard(Board board){
        chessboard = board;
    }

    public boolean haveMovedBefore() {
        return haveMovedBefore;
    }

    public void setHaveMovedBefore(boolean haveMovedBefore) {
        this.haveMovedBefore = haveMovedBefore;
    }

    public void setMyPosition(Field myPosition) {
        this.myPosition = myPosition;
    }

    public boolean canMove(List<Field> fieldsToMove, boolean isItCheckmateTest) {
        if(fieldsToMove.get(fieldsToMove.size()-1) == null){
            return false;
        }
        else if(fieldsToMove.get(fieldsToMove.size()-1).getPieceAtField() != null){
            if(fieldsToMove.get(fieldsToMove.size()-1).getPieceAtField().getPieceColor() == getPieceColor())
                return false;
        }
        this.fieldsToMove = fieldsToMove;

        return isMoveCorrect();
    }

    protected boolean isMoveCorrect(){
        return isWayClear();
    }

    protected boolean isWayClear(){
        for(int i = 1; i < fieldsToMove.size()-1; i++){
            if(fieldsToMove.get(i).getPieceAtField() != null)
                return false;
        }
        return true;
    }

    protected boolean checkIfFieldExists(int horizontal, int vertical){
        if(chessboard.findField(horizontal, vertical) != null) {
            chessboard.findField(horizontal, vertical).setPieceCheckingMe(this);
            return true;
        }
        return false;
    }

    protected void checkFields(){}
}