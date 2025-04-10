package guis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Test method for javaFX
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
//        HomeWindow home = new HomeWindow();
//        home.show();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeWindowController.class.getResource("HomeWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 520, 400);
            primaryStage.setTitle("Takeaway Application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main (String[] args) {
        try {
            launch();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
