package edu.wsu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    TextField playerName;

    @FXML
    ComboBox<Integer> snakeLength;

    @FXML
    ColorPicker snakeColorPicker;

    public void handleButtonAction(ActionEvent actionEvent) {

        System.out.println(playerName.getText());
    }

    public void initialize() {
        for(int i = 1; i < 10; i++) {
            snakeLength.getItems().add(i);
        }
        snakeLength.setValue(6);
        snakeColorPicker.setValue(Color.GREEN);
    }
}
