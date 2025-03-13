package guis;

import javafx.application.Application;
import javafx.stage.Stage;

// Test method for javaFX
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        HomeWindow home = new HomeWindow();
        home.show();
    }

    public static void main (String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
