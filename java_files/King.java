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
            setHaveMovedBefore(true);
            if(moveInHorizontal() == -2){
                ArrayList<Field> temp = new ArrayList<>();
                Rook rook =(Rook) chessboard.findField(1, myPosition.getVerticalPosition()).getPieceAtField();
                temp.add(rook.myPosition);
                temp.add(chessboard.findField(myPosition.getHorizontalPosition()-1, myPosition.getVerticalPosition()));
                chessboard.swapPieces(temp);
            }
            else if(moveInHorizontal() == 2){
                ArrayList<Field> temp = new ArrayList<>();
                Rook rook =(Rook) chessboard.findField(8, myPosition.getVerticalPosition()).getPieceAtField();
                temp.add(rook.myPosition);
                temp.add(chessboard.findField(myPosition.getHorizontalPosition()+1, myPosition.getVerticalPosition()));
                chessboard.swapPieces(temp);
            }
            return true;
        }
        else  if(canMakeNormalMove()){
            setHaveMovedBefore(true);
            return true;
        }
        else return false;
    }

    private boolean canMakeNormalMove(){
        return (Math.abs(moveInVertical()) <= 1 && Math.abs(moveInHorizontal()) <= 1);
    }

    private boolean canMakeCastling(){
        if(Math.abs(moveInHorizontal())==2){
            for(int i = 0; i < fieldsToMove.size(); i++){
                if(fieldsToMove.get(i).isEnemyChecking(getPieceColor()))
                    return false;
            }
            if(moveInHorizontal() == -2){
                if(chessboard.findField(2, myPosition.getVerticalPosition()).getPieceAtField() != null)
                    return false;
                Piece findRook = chessboard.findField(1, myPosition.getVerticalPosition()).getPieceAtField();
                if(findRook != null){
                    if(findRook.getPieceType().equals("rook")){
                        if(!findRook.isHaveMovedBefore()){
                            return true;
                        }
                    }
                }
                return false;
            }
            else if(moveInHorizontal() == 2){
                Piece findRook = chessboard.findField(8, myPosition.getVerticalPosition()).getPieceAtField();
                if(findRook != null){
                    if(findRook.getPieceType().equals("rook")){
                        if(!findRook.isHaveMovedBefore()){
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }
        else
        return false;
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
