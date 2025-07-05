// Matthew Reynolds
// 4/23/2025
// Class: Comp 167 Section: 003
// Description: implement the matching game


import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;

public class GamePane extends BorderPane {
    private CardGridPane cardGridPane;
    private HBox commandPane;
    private HBox statusPane;
    private Button exitButton, newGameButton;
    private ComboBox<String> levelCombo;
    private Label turnsLabel, timerLabel;
    private int rows, cols;
    private int numClicks = 0;
    private int numMatched = 0;
    private int turns = 0;
    private Card clickedCardOne, clickedCardTwo;
    private long lastClickTime = 0;
    private AnimationTimer timer;
    private long startTime;
    private boolean timerRunning = false;
    private AudioClip matchSound = null;
    private AudioClip noMatchSound = null;

    public GamePane() {
        this(64);
    }

    public GamePane(int cardSize) {
        rows = 2; cols = 3; // Default level
        cardGridPane = new CardGridPane(cardSize);
        setCenter(cardGridPane);

        // Status Pane
        statusPane = new HBox(20);
        statusPane.setPadding(new Insets(10));
        statusPane.setAlignment(Pos.CENTER_LEFT);
        turnsLabel = new Label("Turns: 0");
        timerLabel = new Label("Time: 0s");
        statusPane.getChildren().addAll(turnsLabel, timerLabel);

        // Command Pane
        commandPane = new HBox(10);
        commandPane.setPadding(new Insets(10));
        commandPane.setAlignment(Pos.CENTER);
        exitButton = new Button("Exit");
        newGameButton = new Button("New Game");
        levelCombo = new ComboBox<>();
        levelCombo.getItems().addAll(
                "Level 1 (2x3)", "Level 2 (2x4)", "Level 3 (4x4)", "Level 4 (4x6)", "Level 5 (6x6)", "Level 6 (8x8)"
        );
        levelCombo.getSelectionModel().select(0);
        commandPane.getChildren().addAll(levelCombo, newGameButton, exitButton);

        setTop(statusPane);
        setBottom(commandPane);

        // Listeners
        exitButton.setOnAction(e -> System.exit(0));
        newGameButton.setOnAction(e -> newGame());
        levelCombo.setOnAction(e -> {
            int idx = levelCombo.getSelectionModel().getSelectedIndex();
            int[][] levels = {{2,3},{2,4},{4,4},{4,6},{6,6},{8,8}};
            rows = levels[idx][0];
            cols = levels[idx][1];
            newGame();
        });

        newGame();
    }

    public void newGame() {
        cardGridPane.initCards(rows, cols);
        numClicks = 0;
        numMatched = 0;
        turns = 0;
        turnsLabel.setText("Turns: 0");
        timerLabel.setText("Time: 0s");
        clickedCardOne = null;
        clickedCardTwo = null;
        lastClickTime = 0;
        if (timer != null) timer.stop();
        timerRunning = false;
        registerCardListeners();
    }

    public void registerCardListeners() {
        for (int i = 0; i < cardGridPane.getCurrentRows(); i++) {
            for (int j = 0; j < cardGridPane.getCurrentCols(); j++) {
                Card card = cardGridPane.getCard(i, j);
                card.setOnMousePressed(e -> handleCardClick(card));
            }
        }
    }

    private void handleCardClick(Card card) {
        if (card.isFlipped() || card.isMatched() || numClicks == 2) return;
        card.setFlipped(true);
        card.flipCard();
        if (!timerRunning) {
            startTime = System.currentTimeMillis();
            timerRunning = true;
            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                    timerLabel.setText("Time: " + elapsed + "s");
                    if (numClicks == 2 && System.currentTimeMillis() - lastClickTime > 800) {
                        checkMatch();
                    }
                }
            };
            timer.start();
        }
        if (numClicks == 0) {
            clickedCardOne = card;
            numClicks = 1;
        } else if (numClicks == 1 && card != clickedCardOne) {
            clickedCardTwo = card;
            numClicks = 2;
            lastClickTime = System.currentTimeMillis();
        }
    }

    private void checkMatch() {
        if (clickedCardOne != null && clickedCardTwo != null) {
            turns++;
            turnsLabel.setText("Turns: " + turns);
            boolean isMatch = clickedCardOne.getPath().equals(clickedCardTwo.getPath());
            if (isMatch) {
                clickedCardOne.setMatched();
                clickedCardTwo.setMatched();
                numMatched++;
                if (matchSound != null) matchSound.play();
            } else {
                clickedCardOne.setFlipped(false);
                clickedCardTwo.setFlipped(false);
                clickedCardOne.flipCard();
                clickedCardTwo.flipCard();
                if (noMatchSound != null) noMatchSound.play();
            }
            clickedCardOne = null;
            clickedCardTwo = null;
            numClicks = 0;
            if (numMatched == (rows * cols) / 2) {
                timer.stop();
                timerRunning = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Congratulations! Game over in " + turns + " turns.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }
}
