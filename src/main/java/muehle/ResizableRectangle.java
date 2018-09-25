package muehle;

import javafx.scene.shape.Rectangle;
import muehle.enums.RectanglePosition;

public class ResizableRectangle extends Rectangle {


    private RectanglePosition position;

    public ResizableRectangle(RectanglePosition position) {
        super();
        this.position = position;
    }

    public ResizableRectangle() {
        super();
    }

    @Override
        public boolean isResizable() {
            return true;
        }

    public RectanglePosition getPosition() {
        return position;
    }
}
