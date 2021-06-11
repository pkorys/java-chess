import java.util.ArrayList;

public class Rook extends Piece {
    boolean haveRookMovedBefore = false;

    ArrayList<Field> fieldsToMove;

    public Rook(String color, Field myPosition){
        super("rook", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect(){
        if(isRookMovingInAStraightLine())
            return isWayClear();
        else return false;
    }

    @Override
    protected boolean isWayClear() {
        if(super.isWayClear() && !haveRookMovedBefore)
            haveRookMovedBefore = true;
        return super.isWayClear();
    }

    private boolean isRookMovingInAStraightLine(){
        if(moveInHorizontal() != 0 && moveInVertical() != 0)
            return false;
        else return true;
    }
}
