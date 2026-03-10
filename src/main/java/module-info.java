module fr.mission4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens fr.mission4.controllers to javafx.fxml;
    exports fr.mission4;
}
