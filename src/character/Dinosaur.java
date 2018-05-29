package character;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Game;
import obstacles.Obstacles;


public class Dinosaur extends Pane {
    private int floorPos;
    private final int DINO_SPEED = 15;
    private boolean duck;
    public Point2D velocity;
    private Rectangle rect;

    public Dinosaur() {
        rect = new Rectangle(10, 10, Color.AQUAMARINE);
        velocity = new Point2D(0, 0);
        setTranslateX(50);
        setTranslateY(150);
        getChildren().add(rect);
    }

    public void moveY(int value) {
        boolean moveUpOrDown = value > 0 ? true : false;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Obstacles o : Game.obst) {
                if (this.getBoundsInParent().intersects(o.getBoundsInParent())) {
                    if (moveUpOrDown) {
                        setTranslateY(getTranslateY() + 1);//?
                        return;
                    }
                    else{
                        setTranslateY(getTranslateY() - 1);
                        return;
                    }
                }
            }
            if (duck)
                floorPos = 155;
            else
                floorPos = 150;
            if(getTranslateY() > floorPos)
                setTranslateY(floorPos);
            if(getTranslateY() < floorPos - 40)
                setTranslateY(floorPos - 40);
            this.setTranslateY((getTranslateY() + (moveUpOrDown ? 1 : -1)));
        }
    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            setTranslateX(getTranslateX() + 1);
            for (Obstacles o : Game.obst) {
                if (this.getBoundsInParent().intersects(o.getBoundsInParent())) {
                    if (getTranslateX() + 10 == o.getTranslateX()) {
                        setTranslateX(getTranslateX() - 1);
                        return;
                    }
                }
                if (getTranslateX() == o.getTranslateX()) {

                    Game.score += o.getCount();
                    return;
                }
            }
        }
    }

    public void jump() {
        velocity = new Point2D(DINO_SPEED, -10);
    }
    public void duck() {
        duck = true;
        getChildren().remove(rect);
        rect = new Rectangle(10, 5, Color.GOLD);
        getChildren().add(rect);
    }

    public void normal() {
        duck = false;
        getChildren().remove(rect);
        rect = new Rectangle(10, 10, Color.AQUAMARINE);
        getChildren().add(rect);
    }
}
