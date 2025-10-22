package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.entidades.Administrador;
import modelo.entidades.Empleado;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminEmpleadosModificarCargoController {

    @FXML private ComboBox<String> comboBoxIdEmpleado;
    @FXML private TextField fieldNuevoCargo;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarEmpleados();
        fieldNuevoCargo.setDisable(true);
    }

    private void cargarEmpleados() {
        comboBoxIdEmpleado.getItems().clear();
        Empleado[] empleados = sistema.getGestorUsuarios().getEmpleados(); //
        if (empleados != null) {
            for (Empleado emp : empleados) {
                if (emp != null) comboBoxIdEmpleado.getItems().add(emp.getId()); //
            }
        }
    }

    @FXML
    void onEmpleadoSeleccionado(ActionEvent event) {
        String idEmpleado = comboBoxIdEmpleado.getValue();
        if (idEmpleado == null) return;
        Empleado emp = sistema.getGestorUsuarios().buscarEmpleado(idEmpleado); //
        if (emp != null) {
            fieldNuevoCargo.setText(emp.getCargo()); //
            fieldNuevoCargo.setDisable(false);
        } else {
            fieldNuevoCargo.clear();
            fieldNuevoCargo.setDisable(true);
        }
    }

    @FXML
    void onModificarClick(ActionEvent event) {
        String idEmpleado = comboBoxIdEmpleado.getValue();
        String nuevoCargo = fieldNuevoCargo.getText().trim();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idEmpleado == null || nuevoCargo.isEmpty()) {
            mostrarError("Seleccione un empleado e ingrese el nuevo cargo.");
            return;
        }

        boolean exito = sistema.getGestorUsuarios().modificarCargoEmpleado(idEmpleado, nuevoCargo, admin); //
        if (exito) {
            mostrarExito("Cargo del empleado " + idEmpleado + " actualizado.");
        } else {
            mostrarError("Error: Empleado no encontrado.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}