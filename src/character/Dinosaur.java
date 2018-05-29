package character;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Game;
import obstacles.Obstacles;


public class Dinosaur extends Pane {
    public static final int DINO_SPEED = 5;
    public Point2D velocity;
    private int floorPos;
    private boolean duck;
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
                }
            }
            if (duck)
                floorPos = 155;
            else
                floorPos = 150;
            if (getTranslateY() > floorPos)
                setTranslateY(floorPos);
            if (getTranslateY() < floorPos - 40)
                setTranslateY(floorPos - 40);
            this.setTranslateY((getTranslateY() + (moveUpOrDown ? 1 : -1)));
        }
    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            Obstacles o = Game.obst.get(Game.counter - 1);
            setTranslateX(getTranslateX() + 1);
            int dinoX = (int)getTranslateX();
            int oX = (int)o.getTranslateX();
            oX -= oX % dinoX;
            System.out.println("s " + dinoX + " " + oX);
            if (this.getBoundsInParent().intersects(o.getBoundsInParent()) && dinoX == oX) {
                    setTranslateX(getTranslateX() - 1);
                    return;
            }
            if (oX == 0) {
                Game.score += o.getCount();
                return;
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
