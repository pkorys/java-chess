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
