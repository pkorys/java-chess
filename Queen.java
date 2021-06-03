import java.util.ArrayList;

public class Queen extends Figure{
    public Queen(String color){
        super("queen", color);
    }

    @Override
    public boolean canMove(ArrayList<Field> fields) {
        int verticalMove = fields.get(0).getPos()[1] - fields.get(fields.size() - 1).getPos()[1]; //Distance queen want to travel vertical
        int horizontalMove = fields.get(0).getPos()[0] - fields.get(fields.size() - 1).getPos()[0]; //Distance queen want to travel horizontal

        if((horizontalMove == 0 && verticalMove != 0) || (horizontalMove != 0 && verticalMove == 0)){ //Move in line
            for(int i = 1; i < fields.size()-1; i++){ //Is way clear
                if(fields.get(i).getFigureAtField() != null)
                    return false;
            }
            return true;
        }

        else if(Math.abs(verticalMove) == Math.abs(horizontalMove)){ //Move diagonally
            for(int i = 1; i < fields.size()-1; i++){ //Is way clear
                if(fields.get(i).getFigureAtField() != null)
                    return false;
            }
            return true;
        }
        else
            return false;
    }
}
