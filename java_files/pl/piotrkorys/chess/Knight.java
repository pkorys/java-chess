package pl.piotrkorys.chess;

public class Knight extends Piece {
    public Knight(String color, Field myPosition){
        super("knight", color, myPosition);
    }

    @Override
    protected boolean isMoveCorrect(){
        return isItProperKnightHorizontalMove() || isItProperKnightVerticalMove();
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

        for (int i : ones){
            for (int j : twos){
                checkIfFieldExists(myPosition.getHorizontalPosition() + j, myPosition.getVerticalPosition() + i);
                checkIfFieldExists(myPosition.getHorizontalPosition() + i, myPosition.getVerticalPosition() + j);
            }
        }

    }
}