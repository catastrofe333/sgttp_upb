package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Tren;
import modelo.enums.TipoTren;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminTrenesAgregarController {

    @FXML private ComboBox<TipoTren> comboBoxTipoTren;
    @FXML private Button btnAgregar;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        comboBoxTipoTren.getItems().setAll(TipoTren.values());
        ocultarMensaje();
    }

    @FXML
    void onAgregarClick(ActionEvent event) {
        TipoTren tipoTren = comboBoxTipoTren.getValue();
        Administrador administrador = (Administrador) sistema.getSesionActual();

        if (tipoTren == null) {
            mostrarError("Por favor, seleccione un tipo de tren.");
            return;
        }

        // Generar el ID y crear el objeto Tren
        String nuevoId = sistema.getGestorIds().generarIdTren();
        Tren nuevoTren = new Tren(nuevoId, tipoTren);

        // Llamar a la lógica para agregar el tren
        try {
            sistema.getGestorTrenes().agregarTren(nuevoTren, administrador);
            mostrarExito("Tren agregado con éxito. ID: " + nuevoId);
        } catch (Exception e) {
            mostrarError("Error al guardar el tren: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) {
        labelMensaje.setText("✅ " + mensaje);
        labelMensaje.getStyleClass().setAll("successLabel"); // Asumiendo que tienes un estilo 'successLabel'
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