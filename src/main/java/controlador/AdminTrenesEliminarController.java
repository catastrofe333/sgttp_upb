package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox; // Importar ComboBox
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Tren; // Importar Tren
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminTrenesEliminarController {

    @FXML private ComboBox<String> comboBoxIdTren; // CAMBIO: TextField -> ComboBox
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        // NUEVO: Cargar los IDs de los trenes existentes
        cargarTrenesDisponibles();
    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue(); // CAMBIO: getText() -> getValue()
        Administrador administrador = (Administrador) sistema.getSesionActual();

        if (idTren == null || idTren.isEmpty()) { // CAMBIO: validación
            mostrarError("Por favor, seleccione el ID del tren a eliminar.");
            return;
        }

        boolean exito = sistema.getGestorTrenes().eliminarTren(idTren, administrador);

        if (exito) {
            mostrarExito("Tren con ID " + idTren + " eliminado con éxito.");
            // Actualizar la lista del ComboBox
            comboBoxIdTren.getItems().remove(idTren);
            comboBoxIdTren.setValue(null);
        } else {
            mostrarError("No se encontró el tren con ID " + idTren + " o no se pudo eliminar.");
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