package edu.wsu;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    public ColorPicker snakeHeadColor;

    @FXML
    public ColorPicker snakeBodyColor;

    @FXML
    public ComboBox<Integer> numFruits;

    @FXML
    public ColorPicker snakeFruitColor;

    @FXML
    public ComboBox<Integer> numLives;

    @FXML
    TextField playerName;

    @FXML
    ComboBox<Integer> snakeLength;

    public void handleButtonAction(ActionEvent actionEvent) {
        Node src = (Node) actionEvent.getSource();
        Stage stage = (Stage) src.getScene().getWindow();

        SnakeGameInfo gameInfo = new SnakeGameInfo(this.playerName.getText(), numLives.getValue(), numFruits.getValue(),
                snakeFruitColor.getValue(), this.snakeLength.getValue(), snakeHeadColor.getValue(), snakeBodyColor.getValue());
        SnakePane sp = new SnakePane(gameInfo);
        Scene scene = new Scene(new StackPane(sp), 600, 400);
        stage.setScene(scene);
        sp.startGame();
    }

    public void initialize() {
        for(int i = 1; i < 40; i++) {
            snakeLength.getItems().add(i);
        }
        snakeLength.setValue(6);
        snakeBodyColor.setValue(Color.GREEN);
        snakeHeadColor.setValue(Color.RED);
        for(int i = 1; i < 10; i++) {
            numFruits.getItems().add(i);
        }
        numFruits.setValue(2);
        snakeFruitColor.setValue(Color.PURPLE);
        for(int i = 1; i < 10; i++) {
            numLives.getItems().add(i);
        }
        numLives.setValue(1);
    }
}
