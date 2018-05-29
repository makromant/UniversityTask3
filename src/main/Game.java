package main;


import character.Dinosaur;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import obstacles.Obstacles;

import java.util.ArrayList;


public class Game extends Application {
    public static int score = 0;
    public static ArrayList<Obstacles> obst = new ArrayList<>();
    public static int counter = 0;
    private static Pane appRoot = new Pane();
    private static Pane gameRoot = new Pane();
    AnimationTimer timer;
    Dinosaur dino = new Dinosaur();
    private Pane floor = new Pane();
    private Stage prStage;
    private Scene sc;
    private Label scoreLabel = new Label("Score:" + score);

    public static void main(String[] args) {
        launch(args);
    }

    public Parent createContent() {
        gameRoot.setPrefSize(300, 200);
        floor.getChildren().add(new Rectangle(1000, 10, Color.BLACK));
        floor.setLayoutX(0);
        floor.setLayoutY(160);
        addObstacle(false);
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
        int dinoX = (int)dino.getTranslateX();
        int oX = (int)obst.get(counter-1).getTranslateX();
        oX -= oX % dinoX;
        if (oX == 0) {
            addObstacle(true);
        }

//        if(dino.getTranslateX() >= endX){
//            Label congrat = new  Label("Cngratulation!\nPress ENTER to leave!");
//            sc.setOnKeyReleased(key -> {
//                if (key.getCode().equals(KeyCode.ENTER)) {
//                    prStage.close();
//                }
//            });
//            congrat.setAlignment(Pos.BASELINE_CENTER);
//            appRoot.getChildren().removeAll();
//            appRoot.getChildren().add(congrat);
//            timer.stop();
//        }
    }

    @Override
    public void start(Stage primaryStage) {
        prStage = primaryStage;
        Scene scene = new Scene(createContent());
        sc = scene;
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
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    private void addObstacle(boolean value) {
        if (value) {
//            obst.remove(0);
//            System.out.println("del");
        }

        Obstacles obstacle = new Obstacles(counter);
        counter++;
        obst.add(obstacle);
        double x = counter * 350 + (Math.random() * 50 + 250);
        System.out.println(dino.getTranslateX() + " " + x);
        obstacle.setTranslateX(counter * 350 + (Math.random() * 50 + 250));
        obstacle.setTranslateY(150);
        gameRoot.getChildren().add(obstacle);

    }

}