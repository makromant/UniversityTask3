package obstacles.types;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Cactus {
    private final static int CACTUS_COUNT_MAX = 4;
    private final static int CACTUS_COUNT_MIN = 1;
    private final int HEIGHT = 10;
    private final int WEIGHT = 10;
    Rectangle rect;
    int count;
    public Cactus() {
        count = genCactusCount();
        rect = new Rectangle(WEIGHT*count, HEIGHT, Color.FORESTGREEN);
    }

    public int getCount() {
        return count;
    }

    public static int genCactusCount() {
        int range = CACTUS_COUNT_MAX - CACTUS_COUNT_MIN;
        return (int) ((Math.random() * range + CACTUS_COUNT_MIN));
    }

    public Rectangle getRect() {
        return rect;
    }
}
