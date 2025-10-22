package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Tren;
import modelo.entidades.VagonCarga;
import modelo.entidades.VagonPasajeros;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminVagonesEliminarController {

    @FXML private ComboBox<String> comboBoxIdTren;
    @FXML private ComboBox<String> comboBoxTipoVagon;
    @FXML private ComboBox<String> comboBoxIdVagon;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        comboBoxTipoVagon.getItems().addAll("PASAJEROS", "CARGA");
        ocultarMensaje();
        // NUEVO: Cargar los IDs de los trenes existentes
        cargarTrenesDisponibles();
        comboBoxIdVagon.setDisable(true); // Deshabilitar vagones hasta que se seleccione tren y tipo
    }

    /**
     * Se llama cuando se selecciona un Tren O un Tipo de Vagon.
     * Actualiza la lista de vagones disponibles.
     */
    @FXML
    void onDatosSeleccionados(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue();
        String tipoVagon = comboBoxTipoVagon.getValue();

        // Limpiar vagones anteriores y deshabilitar
        comboBoxIdVagon.getItems().clear();
        comboBoxIdVagon.setDisable(true);
        ocultarMensaje();

        if (idTren != null && tipoVagon != null) {
            Tren tren = sistema.getGestorTrenes().buscarTren(idTren);
            if (tren == null) {
                mostrarError("Error interno: Tren no encontrado.");
                return;
            }

            if (tipoVagon.equals("PASAJEROS")) {
                for (int i = 0; i < tren.getNumVagonesPasajeros(); i++) {
                    VagonPasajeros vagon = tren.getVagonesPasajeros()[i];
                    if (vagon != null) {
                        comboBoxIdVagon.getItems().add(vagon.getIdVagon());
                    }
                }
            } else if (tipoVagon.equals("CARGA")) {
                for (int i = 0; i < tren.getNumVagonesCarga(); i++) {
                    VagonCarga vagon = tren.getVagonesCarga()[i];
                    if (vagon != null) {
                        comboBoxIdVagon.getItems().add(vagon.getIdVagon());
                    }
                }
            }

            // Habilitar el ComboBox de Vagones si se encontraron
            if (!comboBoxIdVagon.getItems().isEmpty()) {
                comboBoxIdVagon.setDisable(false);
            } else {
                mostrarError("El tren " + idTren + " no tiene vagones de " + tipoVagon + ".");
            }
        }
    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue();
        String tipoVagon = comboBoxTipoVagon.getValue();
        String idVagon = comboBoxIdVagon.getValue();
        Administrador administrador = (Administrador) sistema.getSesionActual();

        if (tipoVagon == null || idTren == null || idVagon == null) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        boolean exito = false;

        if (tipoVagon.equals("PASAJEROS")) {
            exito = sistema.getGestorTrenes().eliminarVagonPasajeros(idTren, idVagon, administrador);
        } else if (tipoVagon.equals("CARGA")) {
            exito = sistema.getGestorTrenes().eliminarVagonCarga(idTren, idVagon, administrador);
        }

        if (exito) {
            mostrarExito("Vagón " + idVagon + " eliminado del tren " + idTren + " con éxito.");
            // Actualizar la lista al instante
            comboBoxIdVagon.getItems().remove(idVagon);
            comboBoxIdVagon.setValue(null);
            if (comboBoxIdVagon.getItems().isEmpty()) {
                comboBoxIdVagon.setDisable(true);
            }
        } else {
            mostrarError("Error: Tren o Vagón no encontrado.");
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