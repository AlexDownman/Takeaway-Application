module personal.projects.group.takeawayapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens personal.projects.group.takeawayapplication to javafx.fxml;
    exports personal.projects.group.takeawayapplication;
}