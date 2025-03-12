package personal.projects.group.takeawayapplication;

import SQLConnectionHandler.ConnectionHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXML file contains details about the styling of the scene/s
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Scene(FXML style, height, width)
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        //adds scene object to stage object
        stage.setScene(scene);
        stage.show();
        String test;
    }

    public static void main(String[] args) {
        //launch calls start method
//        launch();
        new ConnectionHandler();
    }
}