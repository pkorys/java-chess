import java.util.ArrayList;

public class Knight extends Figure{
    public Knight(String color){
        super("knight", color);
    }

    public boolean canMove(ArrayList<Field> fields) {
        int verticalMove = fields.get(0).getPos()[1] - fields.get(fields.size() - 1).getPos()[1]; //Distance knight want to travel vertical
        int horizontalMove = fields.get(0).getPos()[0] - fields.get(fields.size() - 1).getPos()[0]; //Distance knight want to travel horizontal

        if((Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 1 )||(Math.abs(verticalMove) == 1 && Math.abs(horizontalMove) == 2))
            return true;
        else
            return false;
    }
}
