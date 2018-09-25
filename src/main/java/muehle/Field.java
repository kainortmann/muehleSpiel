package muehle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import muehle.enums.RectanglePosition;
import muehle.enums.XPosition;
import muehle.enums.YPosition;

import java.util.Objects;

public class Field extends ImageView {
    private int xPositionOnImaginaryGrid;
    private int yPositionOnImaginaryGrid;
    XPosition xPosition;
    YPosition yPosition;
    Figure figure;

    RectanglePosition parentRectangle;

    public Field(RectanglePosition rectanglePosition, XPosition xPosition, YPosition yPosition) {
        super(Field.class.getResource("emptyField.png").toString());
        this.xPositionOnImaginaryGrid = rectanglePosition.ordinal()+(3-rectanglePosition.ordinal())*xPosition.ordinal();
        this.yPositionOnImaginaryGrid = rectanglePosition.ordinal()+(3-rectanglePosition.ordinal())*yPosition.ordinal();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.parentRectangle = rectanglePosition;
        //System.out.println("Field : x=" + xPositionOnImaginaryGrid +" y=" + yPositionOnImaginaryGrid);
    }

    public int getXPositionOnImaginaryGrid() {
        return xPositionOnImaginaryGrid;
    }

    public int getYPositionOnImaginaryGrid() {
        return yPositionOnImaginaryGrid;
    }

    public RectanglePosition getParentRectangle() {
        return parentRectangle;
    }

    public void setFigure(Figure figure){
        this.figure = figure;
        if(figure == null){
            this.setImage(new Image(this.getClass().getResource("emptyField.png").toString()));
        }
        else {
            this.setImage(new Image (this.getClass().getResource((figure.getColor() == MuehleColor.BLACK) ? "dame_schwarz.png" : "dame_weiss.png").toString()));
    }
    }
    public void removeFigure(){
        figure = null;
        this.setImage(new Image(this.getClass().getResource("emptyField.png").toString()));
    }


    public boolean isEmpty() {
        return this.figure == null;
    }

    public XPosition getXPosition() {
        return xPosition;
    }

    public YPosition getYPosition() {
        return yPosition;
    }

    public void moveFigureTo(Field otherField){
        otherField.setFigure(this.figure);
        this.setFigure(null);
    }

    @Override
    public boolean equals(Object otherField) {
        if (this == otherField) return true;
        if (otherField == null || getClass() != otherField.getClass()) return false;
        Field field = (Field) otherField;
        return xPositionOnImaginaryGrid == field.xPositionOnImaginaryGrid &&
                yPositionOnImaginaryGrid == field.yPositionOnImaginaryGrid &&
                xPosition == field.xPosition &&
                yPosition == field.yPosition &&
                parentRectangle == field.parentRectangle;
    }
}
