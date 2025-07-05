// Matthew Reynolds
// 4/23/2025
// Class: Comp 167 Section: 003
// Description: implement the matching game


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Card extends StackPane {
    private boolean flipped;
    private boolean matched;
    private String path;
    private Image image;
    private ImageView imageView;
    private int row, col;
    private int numRows, numCols;

   // Initializes card as unflipped and unmatched.
    public Card() {
        this.flipped = false;
        this.matched = false;
        this.path = null;
        this.image = null;
        this.imageView = new ImageView();
        this.getChildren().add(imageView);
    }

    public Card(String path) {
        this();
        setPath(path);
    }

    // Flips the card and shows the image if flipped
    public void flipCard() {
        if (flipped) {
            // Shows the image
            if (image != null) {
                imageView.setImage(image);
            }
        } else {
            // Hides the image
            imageView.setImage(null);
        }
    }

    public void setCardAndImageSize(int width, int height) {
        this.setPrefSize(width, height);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    public void setPath(String path) {
        this.path = path;
        try {
            this.image = new Image(path);
            imageView.setImage(image);
        } catch (Exception e) {
            imageView.setImage(null);
        }
    }

    // Setter for matched
    public void setMatched() {
        setMatched(true);
    }

    // Setter for matches true and false
    public void setMatched(boolean matched) {
        this.matched = matched;
        if (matched) {
            setStyle("-fx-background-color: black;");
            getChildren().clear();
        } else {
            setStyle("-fx-background-color: lightgray;");
            setFlipped(false);
        }
    }

    public void setGridPos(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public void setGridSize(int nr, int nc) {
        this.numRows = nr;
        this.numCols = nc;
    }

    // Getters and setters
    public boolean isFlipped() { return flipped; }
    public void setFlipped(boolean flipped) { this.flipped = flipped; flipCard(); }
    public boolean isMatched() { return matched; }
    public String getPath() { return path; }
    public Image getImage() { return image; }
    public ImageView getImageView() { return imageView; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }
}
