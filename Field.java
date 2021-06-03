public class Field {
    private Figure figureAtField;
    final private int xPos;
    final private int yPos;

    public Field(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        figureAtField = null;
    }

    public Figure getFigureAtField() {
        return figureAtField;
    }

    public void setFigureAtField(Figure figureAtField) {
        this.figureAtField = figureAtField;
    }

    public void printFigure(){ //Print colored figure symbol or space if field is empty
        if(figureAtField == null)
            System.out.print(" ");
        else
            System.out.print(figureAtField.getSymbol());
    }

    public int[] getPos(){
        return new int[]{xPos, yPos};
    }

}
