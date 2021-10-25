module edu.wdu {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.wdu to javafx.fxml;
    exports edu.wdu;
}
