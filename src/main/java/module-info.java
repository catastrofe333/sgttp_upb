open module core {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires javafx.graphics;

    exports controlador;
    exports modelo.entidades;
    exports modelo.enums;
    exports modelo.estructuras;
    exports modelo.logica;
    exports modelo.persistencia;
    exports vista;
}