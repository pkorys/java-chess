package pl.piotrkorys.chess;
import java.util.ArrayList;

public class King extends Piece {
    public King(String color, Field myPosition){
        super("King", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        Field destinationField = fieldsToMove.get(fieldsToMove.size()-1);
        if(destinationField.isEnemyChecking(getPieceColor()))
            return false;
        if(canMakeCastling()){
            makeCastling();
            setHaveMovedBefore(true);
            return true;
        }
        else  if(canMakeNormalMove()){
            setHaveMovedBefore(true);
            return true;
        }
        else return false;
    }

    private boolean canMakeNormalMove(){
        return (Math.abs(getMoveInVertical()) <= 1 && Math.abs(getMoveInHorizontal()) <= 1);
    }

    private boolean canMakeCastling(){
        if(Math.abs(getMoveInHorizontal())==2){
            for(int i = 0; i < fieldsToMove.size(); i++){
                if(fieldsToMove.get(i).isEnemyChecking(getPieceColor()))
                    return false;
            }
            if(getMoveInHorizontal() == -2){
                if(chessboard.findField(2, myPosition.getVerticalPosition()).getPieceAtField() != null)
                    return false;
                return canMakeCastling(1);
            }
            else
                return canMakeCastling(8);
        }
        else
            return false;
    }

    private boolean canMakeCastling(int rookHorizontal){
        Piece nearbyRook = chessboard.findField(rookHorizontal, myPosition.getVerticalPosition()).getPieceAtField();
        if(nearbyRook != null){
            if(nearbyRook.getPieceType().equals("rook")){
                if(!nearbyRook.isHaveMovedBefore()){
                    return true;
                }
            }
        }
        return false;
    }
    private void makeCastling(){
        if(getMoveInHorizontal() == -2){
            makeCastling(1, -1);
        }
        else if(getMoveInHorizontal() == 2){
            makeCastling(8,1);
        }
    }

    private void makeCastling(int rookHorizontal, int rookNewHorizontal){
        ArrayList<Field> pathForRook = new ArrayList<>();
        Rook rook =(Rook) chessboard.findField(rookHorizontal, myPosition.getVerticalPosition()).getPieceAtField();
        pathForRook.add(rook.myPosition);
        pathForRook.add(chessboard.findField(myPosition.getHorizontalPosition()+rookNewHorizontal, myPosition.getVerticalPosition()));
        chessboard.swapPieces(pathForRook);
    }

    @Override
    protected void checkFields() {
        int myVerticalPosition = myPosition.getVerticalPosition();
        int myHorizontalPosition = myPosition.getHorizontalPosition();
        int[] ones = new int[]{-1,0,1};
        for (int i =0; i < ones.length; i++){
            for (int j = 0; j < ones.length; j++){
                if(ones[i] == 0 && ones[j] == 0)
                    continue;
                checkIfFieldExists(myHorizontalPosition+ones[i], myVerticalPosition+ones[j]);
            }
        }
    }
}