package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelo.entidades.Empleado;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminEmpleadosConsultarController {

    @FXML private ComboBox<String> comboBoxIdEmpleado;
    @FXML private Label labelMensaje;
    @FXML private VBox vboxResultado;
    @FXML private Label labelTituloEmpleado;
    @FXML private Label labelTipoId;
    @FXML private Label labelNumeroId;
    @FXML private Label labelNombres;
    @FXML private Label labelApellidos;
    @FXML private Label labelCargo;
    @FXML private Label labelUsuario;
    @FXML private Label labelContrasena;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);
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
    void onEmpleadoSeleccionado(ActionEvent event) {
        String idEmpleado = comboBoxIdEmpleado.getValue();
        ocultarMensaje();
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);

        if (idEmpleado == null) return;

        Empleado emp = sistema.getGestorUsuarios().buscarEmpleado(idEmpleado); //
        if (emp != null) {
            mostrarDetallesEmpleado(emp);
        } else {
            mostrarError("Empleado no encontrado.");
        }
    }

    private void mostrarDetallesEmpleado(Empleado emp) {
        labelTituloEmpleado.setText("EMPLEADO " + emp.getId()); //
        labelTipoId.setText(emp.getTipoId().toString()); //
        labelNumeroId.setText(emp.getId()); //
        labelNombres.setText(emp.getNombres()); //
        labelApellidos.setText(emp.getApellidos()); //
        labelCargo.setText(emp.getCargo()); //
        labelUsuario.setText(emp.getUsuario()); //
        // Por seguridad, no mostramos la contraseña real
        labelContrasena.setText("********");

        vboxResultado.setVisible(true);
        vboxResultado.setManaged(true);
    }

    // Métodos auxiliares
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); labelMensaje.setVisible(true); labelMensaje.setManaged(true); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.setVisible(false); labelMensaje.setManaged(false); }
}