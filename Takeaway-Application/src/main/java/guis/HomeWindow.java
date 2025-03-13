package guis;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

// Home/Launch Stage. First Window that opens to the user
public class HomeWindow extends Window {
    public HomeWindow() {
        super("HomeWindow");

        // Button creation which hides this Stage/Window and opens the new Stage/Window
        Button button = new Button("Next Window");
        button.setOnAction(e -> {
            this.hide();
            new SecondTestWindow().show();
        });

        // Create a new pane and add the button to the scene
        root = new StackPane(button);
        scene.setRoot(root);
    }
}
