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
                    } else if (direction == Direction.L && head.getCenterX() <= RADIUS) {
                        direction = Direction.D;
                    } else if (direction == Direction.U && head.getCenterY() <= RADIUS) {
                        direction = Direction.L;
                    } else if (direction == Direction.D && head.getCenterY() >= 400-RADIUS) {
                        direction = Direction.R;
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

        //MyHandler handler = new MyHandler(head, timeline);
        pane.setOnKeyPressed(event -> processKey(event, head, timeline));

        timeline.play();
    }

    class MyHandler implements EventHandler<KeyEvent> {
        Circle head;
        Timeline timeline;

        public MyHandler(Circle head, Timeline timeline) {
            this.head = head;
            this.timeline = timeline;
        }

        @Override
        public void handle(KeyEvent event) {
            double x = head.getCenterX();
            double y = head.getCenterY();
            switch (event.getCode()) {
                case UP:
                    timeline.pause();
                    head.setCenterY(Math.max(0, y - 2 * RADIUS));
                    break;
                case DOWN:
                    timeline.pause();
                    head.setCenterY(Math.min(400, y + 2 * RADIUS));
                    break;
                case LEFT:
                    timeline.pause();
                    head.setCenterX(Math.max(0, x - 2 * RADIUS));
                    break;
                case RIGHT:
                    timeline.pause();
                    head.setCenterX(Math.min(400, x + 2 * RADIUS));
                    break;
                case ESCAPE:
                    timeline.play();
                    break;
            }
        }
    }

    private void processKey(KeyEvent event, Circle head, Timeline timeline) {
            double x = head.getCenterX();
            double y = head.getCenterY();
            switch (event.getCode()) {
                case UP:
                    timeline.pause();
                    head.setCenterY(Math.max(0, y - 2 * RADIUS));
                    break;
                case DOWN:
                    timeline.pause();
                    head.setCenterY(Math.min(400, y + 2 * RADIUS));
                    break;
                case LEFT:
                    timeline.pause();
                    head.setCenterX(Math.max(0, x - 2 * RADIUS));
                    break;
                case RIGHT:
                    timeline.pause();
                    head.setCenterX(Math.min(400, x + 2 * RADIUS));
                    break;
                case ESCAPE:
                    timeline.play();
                    break;
            }
    }
}
