// Matthew Reynolds
// 4/23/2025
// Class: Comp 167 Section: 003
// Description: implement the matching game


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GamePane root = new GamePane();
        Scene scene = new Scene(root, 800, 900); // adjust the scene if needed
        primaryStage.setTitle("Concentration Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

