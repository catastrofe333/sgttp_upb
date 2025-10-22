package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.entidades.*;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class EmpleadoBoletosValidarController {

    @FXML private ComboBox<String> comboBoxIdBoleto;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarBoletosNoValidados();
    }

    private void cargarBoletosNoValidados() {
        comboBoxIdBoleto.getItems().clear();
        Boleto[] boletos = sistema.getGestorBoletos().getBoletos(); //
        if (boletos != null) {
            for (Boleto b : boletos) {
                // Solo mostrar boletos NO validados
                if (b != null && !b.isValidado()) { //
                    comboBoxIdBoleto.getItems().add(b.getIdBoleto()); //
                }
            }
        }
    }

    @FXML
    void onValidarClick(ActionEvent event) {
        String idBoleto = comboBoxIdBoleto.getValue();
        Empleado empleado = (Empleado) sistema.getSesionActual();

        if (idBoleto == null) {
            mostrarError("Seleccione un boleto a validar.");
            return;
        }

        boolean exito = sistema.getGestorBoletos().validarBoleto(idBoleto, empleado); //

        if (exito) {
            mostrarExito("Boleto " + idBoleto + " validado con éxito.");
            // Quitar el boleto validado de la lista
            comboBoxIdBoleto.getItems().remove(idBoleto);
            comboBoxIdBoleto.setValue(null);
        } else {
            // Esto no debería pasar si la lista se carga bien, pero por si acaso
            mostrarError("Error: Boleto no encontrado o ya estaba validado.");
            cargarBoletosNoValidados(); // Recargar por si acaso
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}