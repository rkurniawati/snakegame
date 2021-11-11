package edu.wsu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    TextField playerName;

    @FXML
    ComboBox<Integer> snakeLength;

    @FXML
    ColorPicker snakeColorPicker;

    public void handleButtonAction(ActionEvent actionEvent) {
        Node src = (Node) actionEvent.getSource();
        Stage stage = (Stage) src.getScene().getWindow();
        SnakePane sp = new SnakePane(snakeLength.getValue(), snakeColorPicker.getValue(),
                Color.RED);
        Scene scene = new Scene(sp, 600, 400);
        stage.setScene(scene);
        sp.startGame();
    }

    public void initialize() {
        for(int i = 1; i < 40; i++) {
            snakeLength.getItems().add(i);
        }
        snakeLength.setValue(6);
        snakeColorPicker.setValue(Color.GREEN);
    }
}
