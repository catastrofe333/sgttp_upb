package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;

import java.io.IOException;

public class AdminViajesController {

    @FXML private Button btnCrearViaje;
    @FXML private Button btnEliminarViaje;
    @FXML private Button btnModificarVisibilidad;
    @FXML private Button btnModificarRuta;
    @FXML private Button btnModificarHorario;
    @FXML private Button btnConsultarViaje;

    @FXML
    public void initialize() {
        System.out.println("Controlador AdminViajesController inicializado.");
    }

    @FXML void onCrearViajeClick(ActionEvent event) { loadSubContent("/admin_viajes_agregar.fxml", event); }
    @FXML void onEliminarViajeClick(ActionEvent event) { loadSubContent("/admin_viajes_eliminar.fxml", event); }
    @FXML void onModificarVisibilidadClick(ActionEvent event) { loadSubContent("/admin_viajes_visibilidad.fxml", event); }
    @FXML void onModificarRutaClick(ActionEvent event) { loadSubContent("/admin_viajes_modificar_ruta.fxml", event); }
    @FXML void onModificarHorarioClick(ActionEvent event) { loadSubContent("/admin_viajes_modificar_horario.fxml", event); }
    @FXML void onConsultarViajeClick(ActionEvent event) { loadSubContent("/admin_viajes_consultar.fxml", event); }

    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            // Navega hacia arriba para encontrar el StackPane 'contentArea'
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