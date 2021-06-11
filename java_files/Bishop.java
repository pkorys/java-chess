import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(String color, Field myPosition){
        super("bishop", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        if(isBishopMovingDiagonally())
            return isWayClear();
        else return false;
    }

    private boolean isBishopMovingDiagonally(){
        if(Math.abs(moveInHorizontal()) != Math.abs(moveInVertical()))
            return false;
        else return true;
    }
}
