module personal.projects.group.takeawayapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.xerial.sqlitejdbc;
    requires java.net.http;
    requires json.simple;
    requires io.github.cdimascio.dotenv.java;


    opens personal.projects.group.takeawayapplication to javafx.fxml;
    exports personal.projects.group.takeawayapplication;

    opens guis to javafx.graphics;
    exports guis to javafx.fxml, guis;
}