package muehle;

import muehle.enums.RectanglePosition;
import muehle.enums.XPosition;
import muehle.enums.YPosition;

import java.util.ArrayList;
import java.util.List;

import static muehle.enums.XPosition.LEFT;
import static muehle.enums.XPosition.MIDDLE;
import static muehle.enums.XPosition.RIGHT;
import static muehle.enums.YPosition.BOTTOM;
import static muehle.enums.YPosition.TOP;

public class Board {
    private static Board ourInstance = new Board();
    private List<Field> allFields;

    public static Board getInstance() {
        return ourInstance;
    }

    private Board() {
        allFields = new ArrayList<>();
        allFields.add(new Field(RectanglePosition.OUTSIDE, LEFT, TOP));
        allFields.add(new Field(RectanglePosition.OUTSIDE, XPosition.MIDDLE, TOP));
        allFields.add(new Field(RectanglePosition.OUTSIDE, RIGHT, TOP));
        allFields.add(new Field(RectanglePosition.OUTSIDE, LEFT, BOTTOM));
        allFields.add(new Field(RectanglePosition.OUTSIDE, MIDDLE, BOTTOM));
        allFields.add(new Field(RectanglePosition.OUTSIDE, RIGHT, BOTTOM));
        allFields.add(new Field(RectanglePosition.OUTSIDE, LEFT, YPosition.MIDDLE));
        allFields.add(new Field(RectanglePosition.OUTSIDE, RIGHT, YPosition.MIDDLE));

        allFields.add(new Field(RectanglePosition.MIDDLE, LEFT, TOP));
        allFields.add(new Field(RectanglePosition.MIDDLE, MIDDLE, TOP));
        allFields.add(new Field(RectanglePosition.MIDDLE, RIGHT, TOP));
        allFields.add(new Field(RectanglePosition.MIDDLE, LEFT, BOTTOM));
        allFields.add(new Field(RectanglePosition.MIDDLE, MIDDLE, BOTTOM));
        allFields.add(new Field(RectanglePosition.MIDDLE, RIGHT, BOTTOM));
        allFields.add(new Field(RectanglePosition.MIDDLE, LEFT, YPosition.MIDDLE));
        allFields.add(new Field(RectanglePosition.MIDDLE, RIGHT, YPosition.MIDDLE));

        allFields.add(new Field(RectanglePosition.INSIDE, LEFT, TOP));
        allFields.add(new Field(RectanglePosition.INSIDE, MIDDLE, TOP));
        allFields.add(new Field(RectanglePosition.INSIDE, RIGHT, TOP));
        allFields.add(new Field(RectanglePosition.INSIDE, LEFT, BOTTOM));
        allFields.add(new Field(RectanglePosition.INSIDE, MIDDLE, BOTTOM));
        allFields.add(new Field(RectanglePosition.INSIDE, RIGHT, BOTTOM));
        allFields.add(new Field(RectanglePosition.INSIDE, LEFT, YPosition.MIDDLE));
        allFields.add(new Field(RectanglePosition.INSIDE, RIGHT, YPosition.MIDDLE));
    }

    public List<Field> getAllFields() {
        return allFields;
    }
}
