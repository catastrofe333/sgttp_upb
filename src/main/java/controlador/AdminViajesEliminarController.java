package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Viaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminViajesEliminarController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarViajesDisponibles();
    }

    private void cargarViajesDisponibles() {
        comboBoxIdViaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes();
        if (viajes != null) {
            for (Viaje viaje : viajes) {
                if (viaje != null) {
                    comboBoxIdViaje.getItems().add(viaje.getIdViaje());
                }
            }
        }
    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idViaje == null) {
            mostrarError("Por favor, seleccione un ID de Viaje.");
            return;
        }

        boolean exito = sistema.getGestorViajes().eliminarViaje(idViaje, admin);

        if (exito) {
            mostrarExito("Viaje " + idViaje + " eliminado con éxito.");
            cargarViajesDisponibles(); // Recargar la lista
            comboBoxIdViaje.setValue(null);
        } else {
            mostrarError("Error: Viaje " + idViaje + " no encontrado o no se pudo eliminar.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) { labelMensaje.setText("✅ " + mensaje); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String mensaje) { labelMensaje.setText("❌ " + mensaje); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}