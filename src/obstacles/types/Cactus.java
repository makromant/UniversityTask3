package obstacles.types;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cactus {
    private final static int CACTUS_COUNT_MAX = 3;
    private final static int CACTUS_COUNT_MIN = 1;
    private final static Image cactusImg = new Image("file:resources/cactus.gif");
    private ImageView cactusView;
    private int count;

    public Cactus() {
        count = genCactusCount();
        cactusView = new ImageView(cactusImg);
    }

    private static int genCactusCount() {
        int range = CACTUS_COUNT_MAX - CACTUS_COUNT_MIN;
        return (int) ((Math.random() * range + CACTUS_COUNT_MIN));
    }

    public ImageView getCactusView() {
        return cactusView;
    }

    public int getCount() {
        return count;
    }

}
