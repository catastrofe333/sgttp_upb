package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import vista.Aplicacion;
import java.io.IOException;

public class MisBoletosOpcionesController {

    @FXML
    void onBackClick(ActionEvent event) {
        cargarVista("/inicio.fxml", event);
    }

    @FXML
    void onPorPasajeroClick(ActionEvent event) {
        cargarSubVistaConsulta(true, event); // true indica buscar por pasajero
    }

    @FXML
    void onPorBoletoClick(ActionEvent event) {
        cargarSubVistaConsulta(false, event); // false indica buscar por ID de boleto
    }

    private void cargarSubVistaConsulta(boolean buscarPorPasajero, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/mis_boletos_consultar.fxml")); //
            Parent root = loader.load();

            // Pasa el modo de búsqueda al controlador de consulta
            MisBoletosConsultarController consultarController = loader.getController();
            consultarController.setModoBusqueda(buscarPorPasajero);

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //

        } catch (IOException e) {
            System.err.println("Error al cargar mis_boletos_consultar.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método auxiliar para cargar vistas completas (como volver a inicio)
    private void cargarVista(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource(fxmlPath)); //
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}