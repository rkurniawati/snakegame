package edu.wsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SnakeApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(600, 400);
        Circle head = new Circle(300, 200, 10, Color.RED);
        pane.getChildren().add(head);
        int numCircles = 10;
        for (int i = 1; i <= numCircles; i++) {
            Circle bodySegment = new Circle(300 - i * 20, 200, 10, Color.LIMEGREEN);
            pane.getChildren().add(bodySegment);
        }

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake Game");
        primaryStage.show();
    }
}
