import java.util.ArrayList;

public class Rook extends Figure{
    boolean haveMoved = false; //Did rook moved before?

    public Rook(String color){
        super("rook", color);
    }

    public boolean canMove(ArrayList<Field> fields) {
        int[] vector = new int[]{fields.get(0).getPos()[0] - fields.get(fields.size()-1).getPos()[0], fields.get(0).getPos()[1] - fields.get(fields.size()-1).getPos()[1]};

        if(vector[0] + vector[1] != vector[0] && vector[0] + vector[1] != vector[1])//If figure try to move diagonally
            return false;

        else{
            for(int i = 1; i < fields.size()-1; i++){ //Is way clear
                if(fields.get(i).getFigureAtField() != null)
                    return false;
            }
            haveMoved = true;
            return true;
            }
    }
}
