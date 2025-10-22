package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;
import java.io.IOException;

public class EmpleadoEquipajesController {
    // Los FXML de los botones no son necesarios aquí si solo navegan

    @FXML void onAgregarClick(ActionEvent event) { loadSubContent("/empleado_equipajes_agregar.fxml", event); }
    @FXML void onEntregarClick(ActionEvent event) { loadSubContent("/empleado_equipajes_entregar.fxml", event); }
    @FXML void onConsultarClick(ActionEvent event) { loadSubContent("/empleado_equipajes_consultar.fxml", event); }

    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            StackPane contentArea = (StackPane) ((Parent)((Button)event.getSource()).getParent().getParent().getParent()).getScene().lookup("#contentAreaEmpleado"); // Busca el StackPane del empleado
            if (contentArea != null) {
                Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath)); //
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else { System.err.println("Error: No se encontró StackPane 'contentAreaEmpleado'."); }
        } catch (IOException | NullPointerException e) { // Captura ambos
            String errorMsg = (e instanceof NullPointerException) ? "No se encontró FXML: " : "Error al cargar: ";
            System.err.println(errorMsg + fxmlPath); e.printStackTrace();
        }
    }
}