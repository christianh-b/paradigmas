module org.tp {
    requires javafx.controls;
    requires javafx.fxml;
    exports org.tp;

    opens org.tp.controlador to javafx.fxml;
    opens org.tp.vista to javafx.fxml;
}
