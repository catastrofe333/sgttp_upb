package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Tren; // Importar Tren
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminVagonesAgregarController {

    @FXML private ComboBox<String> comboBoxIdTren; // CAMBIO: TextField -> ComboBox
    @FXML private ComboBox<String> comboBoxTipoVagon;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        comboBoxTipoVagon.getItems().addAll("PASAJEROS", "CARGA");
        ocultarMensaje();
        // NUEVO: Cargar los IDs de los trenes existentes
        cargarTrenesDisponibles();
    }

    @FXML
    void onAgregarClick(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue(); // CAMBIO: getText() -> getValue()
        String tipoVagon = comboBoxTipoVagon.getValue();
        Administrador administrador = (Administrador) sistema.getSesionActual();

        if (idTren == null || tipoVagon == null) { // CAMBIO: validación
            mostrarError("Por favor, complete el ID del tren y el tipo de vagón.");
            return;
        }

        String nuevoIdVagon;
        boolean exito = false;

        if (tipoVagon.equals("PASAJEROS")) {
            nuevoIdVagon = sistema.getGestorIds().generarIdVagonPasajeros();
            exito = sistema.getGestorTrenes().agregarVagonPasajeros(idTren, nuevoIdVagon, administrador);
        } else if (tipoVagon.equals("CARGA")) {
            nuevoIdVagon = sistema.getGestorIds().generarIdVagonCarga();
            exito = sistema.getGestorTrenes().agregarVagonCarga(idTren, nuevoIdVagon, administrador);
        } else {
            mostrarError("Tipo de vagón no válido.");
            return;
        }

        if (exito) {
            mostrarExito("Vagón de " + tipoVagon + " agregado al tren " + idTren + ". ID Vagón: " + nuevoIdVagon);
        } else {
            mostrarError("Error: Tren no encontrado o se superó la capacidad máxima de vagones de " + tipoVagon + " para ese tipo de tren.");
        }
    }

    // NUEVO: Método auxiliar para cargar trenes
    private void cargarTrenesDisponibles() {
        comboBoxIdTren.getItems().clear();
        Tren[] trenes = sistema.getGestorTrenes().getTrenes();
        if (trenes != null) {
            for (Tren tren : trenes) {
                if (tren != null) {
                    comboBoxIdTren.getItems().add(tren.getIdTren());
                }
            }
        }
    }

    // Métodos auxiliares (sin cambios)
    private void mostrarExito(String mensaje) {
        labelMensaje.setText("✅ " + mensaje);
        labelMensaje.getStyleClass().setAll("successLabel");
    }

    private void mostrarError(String mensaje) {
        labelMensaje.setText("❌ " + mensaje);
        labelMensaje.getStyleClass().setAll("errorLabel");
    }

    private void ocultarMensaje() {
        labelMensaje.setText("");
        labelMensaje.getStyleClass().clear();
    }
}