package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;
import java.io.IOException;

public class EmpleadoBoletosController {
    // Botones no necesitan @FXML aquí

    @FXML void onValidarClick(ActionEvent event) { loadSubContent("/empleado_boletos_validar.fxml", event); }
    @FXML void onConsultarClick(ActionEvent event) { loadSubContent("/empleado_boletos_consultar_opciones.fxml", event); } // Carga las opciones primero

    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            StackPane contentArea = (StackPane) ((Parent)((Button)event.getSource()).getParent().getParent().getParent()).getScene().lookup("#contentAreaEmpleado");
            if (contentArea != null) {
                Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath)); //
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else { System.err.println("Error: No se encontró StackPane 'contentAreaEmpleado'."); }
        } catch (IOException | NullPointerException e) {
            String errorMsg = (e instanceof NullPointerException) ? "No se encontró FXML: " : "Error al cargar: ";
            System.err.println(errorMsg + fxmlPath); e.printStackTrace();
        }
    }
}