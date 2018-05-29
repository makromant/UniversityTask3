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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import obstacles.Obstacles;

import java.util.ArrayList;


public class Game extends Application {
    public static int score = 0;
    AnimationTimer timer;
    public static ArrayList<Obstacles> obst = new ArrayList<>();
    private static Pane end = new Pane();
    private static boolean endOfGame = false;
    private static Pane appRoot = new Pane();
    private static Pane gameRoot = new Pane();
    Dinosaur dino = new Dinosaur();
    private Stage prStage;
    private Scene sc;
    int endX;
    private Label scoreLabel = new Label("Score:" + score);

    public static void main(String[] args) {
        launch(args);
    }

    public Parent createContent() {
        gameRoot.setPrefSize(300, 200);
        Pane floor = new Pane();
        floor.getChildren().add(new Rectangle(1752500, 10, Color.BLACK));
        floor.setLayoutX(0);
        floor.setLayoutY(160);
        endX = 0;
        for (int i = 0; i < 10; i++) {
            Obstacles obstacle = new Obstacles(i);
            obst.add(obstacle);
            obstacle.setTranslateX(i * 350 + 200);
            obstacle.setTranslateY(150);
            gameRoot.getChildren().add(obstacle);
            endX = i * 350 + 200;
        }
        System.out.println(endX);
        end.getChildren().add(new Rectangle(10, 200, Color.GRAY));
        end.setLayoutX(endX);
        end.setLayoutY(0);
        gameRoot.getChildren().addAll(floor, dino, end);
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
//            if(newValue.intValue() % 6000 == 0) {
//                Pane floor = new Pane();
//                floor.getChildren().add(new Rectangle(4000, 10, Color.BLACK));
//                floor.setLayoutX(dino.getTranslateX() + 900);
//                floor.setLayoutY(160);
//                gameRoot.getChildren().add(floor);
//
//                for (int i = 0; i < 2; i++) {
//                    Obstacles obstacle = new Obstacles(score);
//                    obst.add(obstacle);
//                    obstacle.setTranslateX(dino.getTranslateX() + 300);
//                    obstacle.setTranslateY(150);
//                    gameRoot.getChildren().add(obstacle);
//                }
//            }
        });
        if(dino.getTranslateX() >= endX){
            Label congrat = new  Label("Cngratulation!\nPress ENTER to leave!");
            sc.setOnKeyReleased(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    prStage.close();
                }
            });
            congrat.setAlignment(Pos.BASELINE_CENTER);
            appRoot.getChildren().removeAll();
            appRoot.getChildren().add(congrat);
            timer.stop();
        }
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

}