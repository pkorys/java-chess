import java.util.ArrayList;

public class Piece {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";

    private String type;
    private String color;
    private String symbol;

    public Piece(String type, String color) {
        this.type = type;
        this.color = color;

        //Setting piece symbol
        if(color == "Black")
            symbol = ANSI_BLACK + type.charAt(0) + ANSI_RESET;
        else
            symbol = ANSI_RESET + type.charAt(0);
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean canMove(ArrayList<Field> fields){
        return false;
    }
}
