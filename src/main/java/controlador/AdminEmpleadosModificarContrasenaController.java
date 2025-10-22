package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import modelo.entidades.Administrador;
import modelo.entidades.Empleado;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminEmpleadosModificarContrasenaController {

    @FXML private ComboBox<String> comboBoxIdEmpleado;
    @FXML private PasswordField fieldNuevaContrasena;
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
                if (emp != null) comboBoxIdEmpleado.getItems().add(emp.getId()); //
            }
        }
    }

    @FXML
    void onModificarClick(ActionEvent event) {
        String idEmpleado = comboBoxIdEmpleado.getValue();
        String nuevaContrasena = fieldNuevaContrasena.getText(); // No trim
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idEmpleado == null || nuevaContrasena.isEmpty()) {
            mostrarError("Seleccione un empleado e ingrese la nueva contraseña.");
            return;
        }

        boolean exito = sistema.getGestorUsuarios().modificarContrasenaEmpleado(idEmpleado, nuevaContrasena, admin); //
        if (exito) {
            mostrarExito("Contraseña del empleado " + idEmpleado + " actualizada.");
            fieldNuevaContrasena.clear();
        } else {
            mostrarError("Error: Empleado no encontrado.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}