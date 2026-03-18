


import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Collections;


public class CardGridPane extends GridPane {
    public static final int MAXROWS = 8;
    public static final int MAXCOLS = 8;
    private Card[][] cards;
    private ArrayList<String> cardList;
    private int currentRows, currentCols;
    private int cardSize;

    public CardGridPane() {
        this(64);
    }

    public CardGridPane(int cardSize) {
        this.cardSize = cardSize;
        cards = new Card[MAXROWS][MAXCOLS];
        cardList = new ArrayList<>();
        for (int i = 0; i < MAXROWS; i++) {
            for (int j = 0; j < MAXCOLS; j++) {
                cards[i][j] = new Card();
                cards[i][j].setCardAndImageSize(cardSize, cardSize);
                this.add(cards[i][j], j, i);
            }
        }
    }

    // Assigns images to cards for the current game
    public void setCardImages() {
        int idx = 0;
        for (int i = 0; i < currentRows; i++) {
            for (int j = 0; j < currentCols; j++) {
                String path = cardList.get(idx);
                cards[i][j].setPath(path);
                cards[i][j].setFlipped(false);
                cards[i][j].flipCard();
                idx++;
            }
        }
    }

    // Randomly shuffles the image
    public void shuffleImages() {
        Collections.shuffle(cardList);
    }

    public Card getCard(int r, int c) {
        return cards[r][c];
    }

    public void initCards(int rows, int cols) {
        this.currentRows = rows;
        this.currentCols = cols;
        for (int i = 0; i < MAXROWS; i++) {
            for (int j = 0; j < MAXCOLS; j++) {
                cards[i][j].setDisable(i >= rows || j >= cols);
                cards[i][j].setMatched(false);
                cards[i][j].setFlipped(false);
                cards[i][j].flipCard();
                if (i >= rows || j >= cols) {
                    cards[i][j].setStyle("-fx-background-color: gray;");
                } else {
                    cards[i][j].setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
                }
            }
        }
        createCardImageList(rows * cols / 2);
        shuffleImages();
        setCardImages();
    }

    public void createCardImageList(int size) {
        cardList.clear();
        int counter = 0;
        for (int i = 0; i < size; i++) {
            String path = "images/image_" + counter + ".png";
            cardList.add(path);
            cardList.add(path);
            counter++;
        }
    }
// getters and setters
    public int getCurrentRows() { return currentRows; }
    public void setCurrentRows(int rows) { this.currentRows = rows; }
    public int getCurrentCols() { return currentCols; }
    public void setCurrentCols(int cols) { this.currentCols = cols; }
    public int getCardSize() { return cardSize; }
}
