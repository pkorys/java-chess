import java.util.ArrayList;

public class King extends Piece {
    boolean haveMoved = false; //Did King moved before?

    public King(String color, Field myPosition){
        super("King", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect() {
        return (Math.abs(moveInVertical()) <= 1 && Math.abs(moveInHorizontal()) <= 1);
    }
}
