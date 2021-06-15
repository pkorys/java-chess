package pl.piotrkorys.chess;

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
        return (Math.abs(getMoveInVertical()) == 2 && Math.abs(getMoveInHorizontal()) == 1);
    }

    private boolean isItProperKnightHorizontalMove(){
       return (Math.abs(getMoveInHorizontal()) == 2 && Math.abs(getMoveInVertical()) == 1);
    }

    @Override
    protected void checkFields() {
        int[] ones = new int[]{-1, 1};
        int[] twos = new int[]{-2, 2};

        for(int i = 0; i < ones.length; i++){
            for(int j=0; j< twos.length; j++)
                checkIfFieldExists(myPosition.getHorizontalPosition()+ones[i], myPosition.getVerticalPosition()+twos[j]);
        }

        for(int i = 0; i < ones.length; i++){
            for(int j=0; j< twos.length; j++){
                checkIfFieldExists(myPosition.getHorizontalPosition()+twos[i], myPosition.getVerticalPosition()+ones[j]);
            }
        }
    }
}