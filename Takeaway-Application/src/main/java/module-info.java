module personal.projects.group.takeawayapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens personal.projects.group.takeawayapplication to javafx.fxml;
    exports personal.projects.group.takeawayapplication;
}