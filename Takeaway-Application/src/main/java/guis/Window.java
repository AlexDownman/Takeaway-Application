package guis;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

// All Stages/Windows should inherit this class as it holds the base methods and values for each window
public class Window {
    // This list holds all currently opened Window/Stage Objects
    public static final ArrayList<Window>  OPENED_WINDOW_LIST = new ArrayList<>();

    //Base Height/Width. Is perfectly fine to change
    public static final int BASE_HEIGHT = 520;
    public static final int BASE_WIDTH = 400;

    /*
    Protected attributes for child classes to inherit
     - Stage is the Window
     - Scene is what is viewed on the Stage
     - Pane is the object list (I think)
     */
    protected Stage stage;
    protected Scene scene;
    protected Pane root;

    //When you create another Window/Form, you can inherit this window to
    public Window(String title) {
        this.stage = new Stage();
        this.root = new Pane();
        this.scene = new Scene(root, BASE_WIDTH, BASE_HEIGHT);

        this.stage.setTitle(title);
        this.stage.setScene(scene);
        OPENED_WINDOW_LIST.add(this);
    }

    public Stage getStage() {
        return stage;
    }

    public Pane getRoot() {
        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public void hide() {
        this.stage.hide();
    }

    public void show() {
        this.stage.show();
    }

    public void close() {
        this.stage.close();
    }
}
