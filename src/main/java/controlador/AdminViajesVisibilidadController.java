package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Viaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminViajesVisibilidadController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private ComboBox<String> comboBoxVisibilidad;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();
    private final String VISIBLE = "VISIBLE";
    private final String OCULTO = "OCULTO";

    @FXML
    public void initialize() {
        ocultarMensaje();
        comboBoxVisibilidad.getItems().addAll(VISIBLE, OCULTO);
        comboBoxVisibilidad.setDisable(true);
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
    void onViajeSeleccionado(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        if (idViaje == null) return;

        Viaje viaje = sistema.getGestorViajes().buscarViaje(idViaje);
        if (viaje != null) {
            comboBoxVisibilidad.setValue(viaje.isVisible() ? VISIBLE : OCULTO);
            comboBoxVisibilidad.setDisable(false);
        }
    }

    @FXML
    void onActualizarClick(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        String visibilidad = comboBoxVisibilidad.getValue();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idViaje == null || visibilidad == null) {
            mostrarError("Por favor, seleccione un viaje y un estado de visibilidad.");
            return;
        }

        boolean exito = false;
        if (visibilidad.equals(VISIBLE)) {
            exito = sistema.getGestorViajes().hacerVisible(idViaje, admin);
        } else if (visibilidad.equals(OCULTO)) {
            // Usamos el método que añadimos en el Paso 1
            exito = sistema.getGestorViajes().hacerOculto(idViaje, admin);
        }

        if (exito) {
            mostrarExito("Visibilidad del viaje " + idViaje + " actualizada a " + visibilidad + ".");
        } else {
            mostrarError("Error: El viaje no se encontró o ya estaba en ese estado.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) { labelMensaje.setText("✅ " + mensaje); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String mensaje) { labelMensaje.setText("❌ " + mensaje); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}