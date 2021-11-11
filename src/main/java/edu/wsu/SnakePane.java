package edu.wsu;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

public class SnakePane extends AnchorPane {

    private static final double RADIUS = 10;
    private int snakeLength;
    private Color bodyColor, headColor;
    private List<Circle> snake;
    private double dx, dy; // movement delta in x and y direction;
    private Timeline timeline;

    public SnakePane(int snakeLength, Color bodyColor, Color headColor) {
        this.snakeLength = snakeLength;
        this.bodyColor = bodyColor;
        this.headColor = headColor;

        // set movement to right
        this.dx = 2*RADIUS;
        this.dy = 0;

        snake = new LinkedList<>();
    }

    private  void moveSnake() {
        Circle head = snake.get(0);
        Circle lastSegment = snake.remove(snake.size()-1);

        lastSegment.setCenterX((head.getCenterX()+getWidth()+dx) % getWidth());
        lastSegment.setCenterY((head.getCenterY()+getHeight()+dy) % getHeight());
        lastSegment.setFill(this.headColor);

        head.setFill(this.bodyColor);

        snake.add(0, lastSegment);
    }

    private void createSnake() {
        double centerX = getWidth()/2;
        double centerY = getHeight()/2;
        Circle head = new Circle(centerX, centerY, RADIUS, this.headColor);
        snake.add(head);
        for (int i = 1; i <= snakeLength; i++) {
            Circle bodySegment = new Circle(centerX - i * 2 *RADIUS, centerY, RADIUS, this.bodyColor);
            snake.add(bodySegment);
        }

    }
    public void startGame() {
        createSnake();
        getChildren().addAll(snake);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(30),
                event -> moveSnake()));
        timeline.play();

        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    this.timeline.pause();
                    this.dx = 0; this.dy = -2 * RADIUS;
                    moveSnake();
                    break;
                case DOWN:
                    this.timeline.pause();
                    this.dx = 0; this.dy = 2 * RADIUS;
                    moveSnake();
                    break;
                case RIGHT:
                    this.timeline.pause();
                    this.dx = 2 * RADIUS; this.dy = 0;
                    moveSnake();
                    break;
                case LEFT:
                    this.timeline.pause();
                    this.dx = -2 * RADIUS; this.dy = 0;
                    moveSnake();
                    break;
                case ESCAPE:
                    this.timeline.play();
                    break;
            }
        });
        this.requestFocus();

    }
}
