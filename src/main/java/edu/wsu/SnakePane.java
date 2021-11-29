package edu.wsu;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakePane extends AnchorPane {

    private static final double RADIUS = 10;
    private final Random random;

    private SnakeGameInfo gameInfo;

    private List<Circle> snake;
    private List<Circle> fruitCollection;

    private double dx, dy; // movement delta in x and y direction;
    private double lastX, lastY; // last body segment location, for elongation of body

    private Timeline timeline;

    private StackPane restartMessage;

    public SnakePane(SnakeGameInfo gameInfo) {
        this.gameInfo = gameInfo;

        this.random = new Random(123);
    }

    boolean moveSnake() {
        Circle head = snake.get(0);
        double width = getWidth(), height = getHeight();
        // did we hit a wall?
        if (head.getCenterX()+dx >= width || head.getCenterX()+dx <= 0) {
            return false;
        }

        if (head.getCenterY()+dy >= height || head.getCenterY()+dy <= 0) {
            return false;
        }

        Circle lastSegment = snake.get(snake.size()-1);

        this.lastX = lastSegment.getCenterX();
        this.lastY = lastSegment.getCenterY();
        lastSegment.setCenterX((head.getCenterX()+width+dx) % width);
        lastSegment.setCenterY((head.getCenterY()+height+dy) % height);
        lastSegment.setFill(this.gameInfo.getHeadColor());

        head.setFill(this.gameInfo.getBodyColor());

        snake.remove(snake.size()-1);
        snake.add(0, lastSegment);

        // did we hit our own segment?
        for(int i = 1; i < snake.size(); i++) {
            if (Util.overlaps(lastSegment, snake.get(i))) {
                return false;
            }
        }
        return true;
    }

    // generate numFruit on a pane with width and height,
    void generateFruits() {
        this.fruitCollection = new LinkedList<>();

        // calculate grid width and height
        double gridSize = RADIUS * 2;
        double width = getWidth(), height = getHeight();

        assert width % gridSize <= Util.EPSILON;
        assert height % gridSize <= Util.EPSILON;

        for (int i = 0; i < gameInfo.getNumFruit(); i++) {
            Circle fruit = generateOneFruit();
            fruitCollection.add(fruit);
        }
    }

    Circle generateOneFruit() {
        int gridWidth = (int) (0.5 + getWidth() / (2*RADIUS));
        int gridHeight = (int) (0.5 + getHeight() / (2*RADIUS));

        while (true) {
            int gridX = random.nextInt(gridWidth);
            int gridY = random.nextInt(gridHeight);
            Circle fruit = new Circle(gridX * 2 * RADIUS + RADIUS,
                    gridY * 2 * RADIUS + RADIUS, RADIUS, gameInfo.getFruitColor());
            if (!Util.overlaps(snake, fruit) && !Util.overlaps(fruitCollection, fruit)) {
                return fruit;
            }
        }
    }

    private void createSnake() {
        int snakeLen = Math.max(this.gameInfo.getSnakeLength(), this.snake != null ? this.snake.size() : 0);
        this.snake = new LinkedList<>();

        int gridWidth = (int) (0.5 + getWidth() / (2*RADIUS));
        int gridHeight = (int) (0.5 + getHeight() / (2*RADIUS));

        // make it odd multiple of radius in the center of the screen
        double centerX = (gridWidth/2 * 2 + 1)*RADIUS; // note that the first division is an integer division
        double centerY = (gridHeight/2 * 2 + 1)*RADIUS;
        Circle head = new Circle(centerX, centerY, RADIUS, this.gameInfo.getHeadColor());
        snake.add(head);
        for (int i = 1; i <= snakeLen; i++) {
            Circle bodySegment = new Circle(centerX - i * 2 *RADIUS, centerY, RADIUS, this.gameInfo.getBodyColor());
            snake.add(bodySegment);
        }


    }


    public void startGame() {
        createSnake();
        getChildren().addAll(snake);

        // set movement to right
        this.dx = 2*RADIUS;
        this.dy = 0;

        generateFruits();
        getChildren().addAll(fruitCollection);

        drawGrid();
        gameInfo.setLivesRemaining(gameInfo.getLivesRemaining()-1);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150),
                e -> processGameEvents()));
        timeline.play();

        this.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    if (timeline.getStatus() == Animation.Status.PAUSED && restartMessage != null) {
                        ((StackPane) this.getParent()).getChildren().remove(restartMessage);
                        timeline.play();
                    }
                    break;
                case UP:
                    if (this.dy > 0) break; // shouldn't go up if currently moving down
                    this.dx = 0; this.dy = -2 * RADIUS;
                    processGameEvents();
                    break;
                case DOWN:
                    if (this.dy < 0) break; // shouldn't go down if currently moving up
                    this.dx = 0; this.dy = 2 * RADIUS;
                    processGameEvents();
                    break;
                case RIGHT:
                    if (this.dx < 0) break; // shouldn't go right if currently moving left
                    this.dx = 2 * RADIUS; this.dy = 0;
                    processGameEvents();
                    break;
                case LEFT:
                    if (this.dx > 0) break; // shouldn't go left if currently moving right
                    this.dx = -2 * RADIUS; this.dy = 0;
                    processGameEvents();
                    break;
            }
        });

    }

    private void processGameEvents() {

        if (!moveSnake()) {
            // we hit a wall
            if (this.gameInfo.getLivesRemaining() == 0) {
                // show game over screen
                timeline.stop();
                gameOver();
            } else {
                timeline.pause();
                restartGame();
            }
        } else {
            // check for fruits getting eaten
            if (fruitEaten()) {
                gameInfo.setScore(gameInfo.getScore()+1);
                this.getChildren().add(growSnake());
            }
        }
    }

    private boolean fruitEaten() {
        Circle head = snake.get(0);

        for (Circle current : fruitCollection) {
            if (Util.overlaps(head, current)) {
                Circle newFruit = generateOneFruit();
                current.setCenterX(newFruit.getCenterX());
                current.setCenterY(newFruit.getCenterY());
                return true;
            }
        }
        return false;
    }

    private Circle growSnake() {
        Circle bodySegment = new Circle(lastX, lastY, RADIUS, this.gameInfo.getBodyColor());
        snake.add(bodySegment);
        return bodySegment;
    }

    private void restartGame() {
        getChildren().clear();

        createSnake();
        getChildren().addAll(snake);

        // set movement to right
        this.dx = 2*RADIUS;
        this.dy = 0;

        generateFruits();
        getChildren().addAll(fruitCollection);

        drawGrid();
        if (restartMessage == null) {
            restartMessage = new StackPane();
        }
        Text text = new Text("Press space to continue. Number of lives remaining: " + gameInfo.getLivesRemaining());
        text.setFont(Font.font ("Verdana", 20));
        text.setFill(Color.BLACK);
        restartMessage.getChildren().clear();
        restartMessage.getChildren().add(text);
        ((StackPane) this.getParent()).getChildren().add(restartMessage);
        gameInfo.setLivesRemaining(gameInfo.getLivesRemaining()-1);
    }

    private void drawGrid() {
        // draw grid at 2*RADIUS
        double gridSize = 2 * RADIUS;

        for(int i = 1; i < getWidth()/gridSize; i++) {
            // vertical line
            Line line = new Line(i*gridSize, 0, i*gridSize, getHeight());
            line.setStroke(Color.CORNFLOWERBLUE);
            this.getChildren().add(line);
        }

        for(int i = 1; i < getHeight()/gridSize; i++) {
            // horizontal line
            Line line = new Line(0, i*gridSize, getWidth(), i*gridSize);
            line.setStroke(Color.CORNFLOWERBLUE);
            this.getChildren().add(line);
        }
    }

    private void gameOver() {

        StackPane parent = (StackPane) this.getScene().getRoot();
        StackPane pane = new StackPane();
        Text text = new Text("Game Over" + (gameInfo.getPlayerName().length() == 0 ? "" : " " + gameInfo.getPlayerName()) + ", score " + gameInfo.getScore());
        text.setFont(Font.font ("Verdana", 20));
        text.setFill(Color.RED);
        pane.getChildren().add(text);

        parent.getChildren().add(pane);
    }
}
