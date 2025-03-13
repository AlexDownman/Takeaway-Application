package guis;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

// Second Window to test the Window Stack
public class SecondTestWindow extends Window {

    public SecondTestWindow() {
        super("Another Window");

        Label label = new Label("Hello World");
        Button button = new Button("Previous Window");
        button.setOnAction(e -> {
            this.hide();
            /*
            Opens the previous window object by getting the position of this object in the arraylist - 1
            and showing the object
             */
            OPENED_WINDOW_LIST.get(OPENED_WINDOW_LIST.indexOf(this) - 1).show();

            //If you want to remove this from stack do OPENED_WINDOW_LIST.remove(this); and this.close();
        });

        root = new StackPane(label, button);
        scene.setRoot(root);
    }
}
