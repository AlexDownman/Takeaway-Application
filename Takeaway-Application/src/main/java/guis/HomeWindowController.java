package guis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeWindowController {
    //Similar to Javascript DOM

    //@FXML adds it to part of the FXML file
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
