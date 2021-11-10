package edu.wsu;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TestApp extends Application {
    private static final double RADIUS = 10;
    enum Direction {
        L, R, U, D
    }
    private Direction direction = Direction.R;

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(600, 400);

        // top left circle
        Circle head = new Circle(RADIUS, 400-RADIUS, RADIUS, Color.RED);
        pane.getChildren().add(head);
        pane.setOnKeyPressed(event -> {
            double x = head.getCenterX();
            double y = head.getCenterY();
            switch (event.getCode()) {
                case UP:
                    head.setCenterY(y - 2 * RADIUS);
                    break;
                case DOWN:
                    head.setCenterY(y + 2 * RADIUS);
                    break;
                 case LEFT:
                    head.setCenterX(x - 2 * RADIUS);
                    break;
                case RIGHT:
                    head.setCenterX(x + 2 * RADIUS);
                    break;
            }
        });

        Scene scene = new Scene(pane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        pane.requestFocus();

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                event -> {
                    if (direction == Direction.R && head.getCenterX() >= 600 -RADIUS) {
                        direction = Direction.U;
                    }

                    switch (direction) {
                        case R:
                            head.setCenterX(head.getCenterX() + 2 * RADIUS);
                            break;
                        case L:
                            head.setCenterX(head.getCenterX() - 2 * RADIUS);
                            break;

                        case D:
                            head.setCenterY(head.getCenterY() + 2 * RADIUS);
                            break;
                        case U:
                            head.setCenterY(head.getCenterY() - 2 * RADIUS);
                            break;
                    }
                }));
        timeline.play();
    }
}
