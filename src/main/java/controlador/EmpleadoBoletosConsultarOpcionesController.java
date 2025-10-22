package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;
import java.io.IOException;

public class EmpleadoBoletosConsultarOpcionesController {

    // Botones no necesitan @FXML si solo navegan

    @FXML
    void onPorPasajeroClick(ActionEvent event) {
        loadSubContent("/empleado_boletos_consultar_por_pasajero.fxml", event);
    }

    @FXML
    void onPorBoletoClick(ActionEvent event) {
        loadSubContent("/empleado_boletos_consultar_por_id.fxml", event);
    }

    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            // Busca el StackPane correcto subiendo desde el botón -> HBox -> VBox
            StackPane contentArea = (StackPane) ((Parent)((Button)event.getSource()).getParent().getParent()).getScene().lookup("#contentAreaEmpleado");
            if (contentArea != null) {
                FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource(fxmlPath)); //
                Parent subVista = loader.load();
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else { System.err.println("Error: No se encontró StackPane 'contentAreaEmpleado'."); }
        } catch (IOException | NullPointerException e) {
            String errorMsg = (e instanceof NullPointerException) ? "No se encontró FXML: " : "Error al cargar: ";
            System.err.println(errorMsg + fxmlPath); e.printStackTrace();
        }
    }
}