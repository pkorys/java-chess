import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(String color, Field myPosition){
        super("knight", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect(){
        if(isItProperKnightHorizontalMove() || isItProperKnightVerticalMove())
            return true;
        else return false;
    }

    private boolean isItProperKnightVerticalMove(){
        return (Math.abs(moveInVertical()) == 2 && Math.abs(moveInHorizontal()) == 1);
    }

    private boolean isItProperKnightHorizontalMove(){
       return (Math.abs(moveInHorizontal()) == 2 && Math.abs(moveInVertical()) == 1);
    }
}
