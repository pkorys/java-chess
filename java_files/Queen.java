import java.util.ArrayList;

public class Queen extends Piece {
    ArrayList<Field> fieldsToMove;

    public Queen(String color, Field myPosition){
        super("queen", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        if(isQueenMovingDiagonally() || isQueenMovingInAStraightLine())
            return isWayClear();
        else return false;
    }

    private boolean isQueenMovingDiagonally(){
        if(Math.abs(moveInHorizontal()) != Math.abs(moveInVertical()))
            return false;
        else return true;
    }

    private boolean isQueenMovingInAStraightLine(){
        if(moveInHorizontal() != 0 && moveInVertical() != 0)
            return false;
        else return true;
    }
}
