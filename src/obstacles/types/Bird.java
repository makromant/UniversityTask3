package obstacles.types;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bird {
    private final int HEIGHT = 5;
    private final int WEIGHT = 10;
    public Rectangle rect;

    public Bird() {
        rect = new Rectangle(WEIGHT, HEIGHT, Color.BROWN);
    }

    public Rectangle getRect() {
        return rect;
    }
}
