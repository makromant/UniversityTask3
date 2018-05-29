package main;


import character.Dinosaur;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import obstacles.Obstacles;

import java.util.ArrayList;


public class Game extends Application {
    public static int score = 0;
    public static ArrayList<Obstacles> obst = new ArrayList<>();
    public static int counter;
    static AnimationTimer timer;
    private static Pane appRoot;
    private static Pane gameRoot = new Pane();
    private static Stage gameStage = new Stage();
    Dinosaur dino = new Dinosaur();
    private Pane floor = new Pane();
    private Label scoreLabel = new Label("Score:" + score);

    public static void main(String[] args) {
        launch(args);
    }

    public void lose() {
        Stage loseStage = new Stage();
        loseStage.setResizable(false);
        HBox loseContent = new HBox();
        loseContent.setPrefSize(300, 200);
        loseContent.setAlignment(Pos.CENTER);
        Label loseLabel = new Label("You lose!\nPress ENTER to exit!");
        loseContent.getChildren().add(loseLabel);
        Scene loseScene = new Scene(loseContent);
        loseScene.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                loseStage.close();
            }
        });
        loseStage.setScene(loseScene);
        loseStage.setTitle("Chrome dinosaur-like game");
        timer.stop();
        gameStage.close();
        loseStage.show();
    }

    public Parent createContent() {
        floor.getChildren().add(new Rectangle(1000, 10, Color.BLACK));
        floor.setLayoutX(0);
        floor.setLayoutY(160);
        addObstacle();
        Pane appRoot = new Pane();
        gameRoot.setPrefSize(300, 200);
        gameRoot.getChildren().addAll(floor, dino);
        appRoot.getChildren().addAll(gameRoot, scoreLabel);
        return appRoot;
    }

    private void update() {
        if (dino.velocity.getY() < 5)//gravitation
            dino.velocity = dino.velocity.add(0, 1);

        dino.moveX((int) dino.velocity.getX());
        dino.moveY((int) dino.velocity.getY());
        scoreLabel.setText("Score:" + score);
        dino.translateXProperty().addListener((obs, old, newValue) -> {
            gameRoot.setLayoutX(-dino.getTranslateX() + 50);
            floor.setLayoutX(dino.getTranslateX() - 50);
        });
        int dinoX = (int) dino.getTranslateX();
        int oX = (int) obst.get(0).getTranslateX();
        oX -= oX % dinoX;
        if (oX == 0) {
            addObstacle();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        HBox startContent = new HBox();
        startContent.setPrefSize(300, 200);
        startContent.setAlignment(Pos.CENTER);
        Label startLabel =
                new Label("Chrome dinosaur-like game\n\n\nTo start game press ENTER!");
        startContent.getChildren().add(startLabel);
        Scene startScene = new Scene(startContent);
        startScene.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                primaryStage.hide();
                game();
            }
        });
        primaryStage.setTitle("Chrome dinosaur-like game");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void game() {
        counter = 0;
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(key -> {
            if (key.getCode().equals(KeyCode.SPACE) ||
                    key.getCode().equals(KeyCode.UP) ||
                    key.getCode().equals(KeyCode.W)) {
                dino.jump();
            }
            if (key.getCode().equals(KeyCode.DOWN) ||
                    key.getCode().equals(KeyCode.S)) {
                dino.duck();
            }
        });
        scene.setOnKeyReleased(key -> {
            if (key.getCode().equals(KeyCode.DOWN) ||
                    key.getCode().equals(KeyCode.S)) {
                dino.normal();
            }
        });
        gameStage.setResizable(false);
        gameStage.setTitle("Chrome dinosaur-like game");
        gameStage.setScene(scene);
        gameStage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        dino.velocity = new Point2D(Dinosaur.DINO_SPEED, 0);
    }

    private void addObstacle() {
        Obstacles obstacle = new Obstacles(counter);
        counter++;
        obst.add(obstacle);
        double x = counter * 350 + (Math.random() * 50 + 250);
        obstacle.setTranslateX(counter * 350 + (Math.random() * 50 + 250));
        obstacle.setTranslateY(150);
        gameRoot.getChildren().add(obstacle);
        if (obst.size() == 2) {
            obst.remove(0);
        }
    }
}