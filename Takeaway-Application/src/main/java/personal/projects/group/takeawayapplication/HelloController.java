package personal.projects.group.takeawayapplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    //Similar to Javascript DOM

    //@FXML adds it to part of the FXML file
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}