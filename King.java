import java.util.ArrayList;

public class King extends Piece{
    boolean haveMoved = false; //Did King moved before?

    public King(String color){
        super("King", color);
    }

    @Override
    public boolean canMove(ArrayList<Field> fields) {
        int verticalMove = fields.get(0).getPos()[1] - fields.get(fields.size()-1).getPos()[1]; //Distance King want to travel vertical
        int horizontalMove = fields.get(0).getPos()[0] - fields.get(fields.size()-1).getPos()[0]; //Distance King want to travel horizontal

        if((Math.abs(horizontalMove) == 1 && Math.abs(verticalMove) == 0) || (Math.abs(horizontalMove) == 0 && Math.abs(verticalMove) == 1)){
            haveMoved = true;
            return true;
        }

        else return false;
    }


}
