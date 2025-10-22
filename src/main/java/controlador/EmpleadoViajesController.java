package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;
import java.io.IOException;

public class EmpleadoViajesController {

    @FXML void onEstadoClick(ActionEvent event) { loadSubContent("/empleado_viajes_estado.fxml", event); }
    @FXML void onAbordajeClick(ActionEvent event) { loadSubContent("/empleado_viajes_abordaje_inicio.fxml", event); } // Carga la pantalla de inicio del abordaje

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