package edu.wsu;

import javafx.scene.paint.Color;

public class SnakeGameInfo {
    private int score;
    private int livesRemaining;
    private String playerName;
    private int numFruit;
    private Color fruitColor;

    private int snakeLength;
    private Color headColor;
    private Color bodyColor;

    public SnakeGameInfo( String playerName, int livesRemaining, int numFruit, Color fruitColor, int snakeLength, Color headColor, Color bodyColor) {
        this.livesRemaining = livesRemaining;
        this.playerName = playerName;
        this.numFruit = numFruit;
        this.fruitColor = fruitColor;
        this.snakeLength = snakeLength;
        this.headColor = headColor;
        this.bodyColor = bodyColor;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLivesRemaining(int livesRemaining) {
        this.livesRemaining = livesRemaining;
    }

    public int getScore() {
        return score;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getNumFruit() {
        return numFruit;
    }

    public Color getFruitColor() {
        return fruitColor;
    }

    public int getSnakeLength() {
        return snakeLength;
    }

    public Color getHeadColor() {
        return headColor;
    }

    public Color getBodyColor() {
        return bodyColor;
    }
}
