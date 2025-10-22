package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminViajesModificarRutaController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private ComboBox<String> comboBoxIdRuta;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();

        // Cargar Viajes PENDIENTES
        comboBoxIdViaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes();
        if (viajes != null) {
            for (Viaje viaje : viajes) {
                // Solo se pueden modificar viajes PENDIENTES
                if (viaje != null && viaje.getEstado() == modelo.enums.EstadoTrayecto.PENDIENTE) {
                    comboBoxIdViaje.getItems().add(viaje.getIdViaje());
                }
            }
        }

        // Cargar Rutas
        comboBoxIdRuta.getItems().clear();
        Ruta[] rutas = sistema.getGestorRuta().getRutas();
        if (rutas != null) {
            for (Ruta ruta : rutas) {
                if (ruta != null) {
                    comboBoxIdRuta.getItems().add(ruta.getIdRuta());
                }
            }
        }
    }

    @FXML
    void onModificarClick(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        String idRuta = comboBoxIdRuta.getValue();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idViaje == null || idRuta == null) {
            mostrarError("Por favor, seleccione un viaje y una nueva ruta.");
            return;
        }

        boolean exito = sistema.getGestorViajes().modificarRuta(idViaje, idRuta, admin);

        if (exito) {
            mostrarExito("Ruta del viaje " + idViaje + " actualizada a " + idRuta + ".");
            // Opcional: recargar la lista de viajes por si cambia algo
        } else {
            mostrarError("Error: El viaje no se encontró o no está en estado 'PENDIENTE'.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) { labelMensaje.setText("✅ " + mensaje); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String mensaje) { labelMensaje.setText("❌ " + mensaje); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}