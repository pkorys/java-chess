import java.util.ArrayList;

public class Bishop extends Figure{
    public Bishop(String color){
        super("bishop", color);
    }

    public boolean canMove(ArrayList<Field> fields) {
        int verticalMove = fields.get(0).getPos()[1] - fields.get(fields.size() - 1).getPos()[1]; //Distance bishop want to travel vertical
        int horizontalMove = fields.get(0).getPos()[0] - fields.get(fields.size() - 1).getPos()[0]; //Distance bishop want to travel horizontal

        if(Math.abs(verticalMove) != Math.abs(horizontalMove))
            return false;

        else {
            for (int i = 1; i < fields.size()-1; i++){
                if(fields.get(i).getFigureAtField() != null)
                    return false;
            }
            return true;
        }
    }
}
