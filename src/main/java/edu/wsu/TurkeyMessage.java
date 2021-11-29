package edu.wsu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TurkeyMessage extends Application {
    private static final double RADIUS = 10;
    private Color[] colors = {
            Color.ORANGE, Color.CORAL, Color.BROWN
    };

    /**
     * Draw circles along the pane's borders with a radius of 10.
     * The circles should have alternating ORANGE, CORAL, or BROWN colors
     *
     * @param pane the pane whose border needs to be decorated. The pane will have
     *             width and height that are multiple of 20.
     */
    private void drawBorder(AnchorPane pane) {
        // TODO: provide your implementation here
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 260);
        stage.setScene(scene);
        stage.show();

        // create a happy thanksgiving message with a  border
        showMessage(pane);
        drawBorder(pane);
    }

    /**
     * Show the message "Happy Thanksgiving!" at the center of the pane
     * @param pane the pane where the message should be added
     */
    private void showMessage(AnchorPane pane) {
        Text text = new Text("Happy Thanksgiving!");
        text.setFont(Font.font("Arial", FontPosture.ITALIC, 40));
        pane.getChildren().add(text);

        text.setX((pane.getWidth() - text.getBoundsInParent().getWidth())/2);
        text.setY((pane.getHeight() + text.getBoundsInParent().getHeight())/2);
    }
}