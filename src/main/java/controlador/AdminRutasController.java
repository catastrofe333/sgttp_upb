package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;

import java.io.IOException;

public class AdminRutasController {

    @FXML private Button btnCrearRuta;
    @FXML private Button btnConsultarRuta;

    @FXML
    public void initialize() {
        System.out.println("Controlador AdminRutasController inicializado.");
    }

    @FXML
    void onCrearRutaClick(ActionEvent event) {
        loadSubContent("/admin_rutas_agregar.fxml", event);
    }

    @FXML
    void onConsultarRutaClick(ActionEvent event) {
        loadSubContent("/admin_rutas_consultar.fxml", event);
    }

    /**
     * Carga la sub-vista (Agregar o Consultar) en el área de contenido principal.
     */
    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            // Navega hacia arriba para encontrar el StackPane 'contentArea' del PanelAdminController
            StackPane contentArea = (StackPane) ((Parent) ((Button) event.getSource()).getParent().getParent().getParent()).getScene().lookup("#contentArea");

            if (contentArea != null) {
                Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath));
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else {
                System.err.println("Error: No se encontró el StackPane 'contentArea'.");
            }

        } catch (IOException e) {
            System.err.println("Error al cargar la sub-vista: " + fxmlPath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general en loadSubContent: " + e.getMessage());
            e.printStackTrace();
        }
    }
}