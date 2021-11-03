package edu.wsu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    TextField playerName;

    @FXML
    ComboBox<Integer> snakeLength;

    @FXML
    ColorPicker snakeColorPicker;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        System.out.println(playerName.getText());
    }
}
