module core {
    requires javafx.controls;
    requires javafx.fxml;

    exports vista;
    exports controlador;
    exports modelo;

    opens vista to javafx.fxml;
    opens controlador to javafx.fxml;
}