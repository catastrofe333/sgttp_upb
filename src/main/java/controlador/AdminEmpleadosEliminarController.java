package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import modelo.entidades.Administrador;
import modelo.entidades.Empleado;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminEmpleadosEliminarController {

    @FXML private ComboBox<String> comboBoxIdEmpleado;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarEmpleados();
    }

    private void cargarEmpleados() {
        comboBoxIdEmpleado.getItems().clear();
        Empleado[] empleados = sistema.getGestorUsuarios().getEmpleados(); //
        if (empleados != null) {
            for (Empleado emp : empleados) {
                if (emp != null) {
                    comboBoxIdEmpleado.getItems().add(emp.getId()); //
                }
            }
        }
    }

    @FXML
    void onEliminarClick(ActionEvent event) {
        String idEmpleado = comboBoxIdEmpleado.getValue();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idEmpleado == null) {
            mostrarError("Por favor, seleccione un empleado.");
            return;
        }

        boolean exito = sistema.getGestorUsuarios().eliminarEmpleado(idEmpleado, admin); //
        if (exito) {
            mostrarExito("Empleado " + idEmpleado + " eliminado.");
            cargarEmpleados(); // Recargar lista
            comboBoxIdEmpleado.setValue(null);
        } else {
            mostrarError("Error: Empleado no encontrado.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}