package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.entidades.Administrador;
import modelo.entidades.Empleado;
import modelo.enums.TipoId;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminEmpleadosAgregarController {

    @FXML private ComboBox<TipoId> comboBoxTipoId;
    @FXML private TextField fieldNumeroId;
    @FXML private TextField fieldNombres;
    @FXML private TextField fieldApellidos;
    @FXML private TextField fieldCargo;
    @FXML private TextField fieldUsuario;
    @FXML private PasswordField fieldContrasena;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        comboBoxTipoId.getItems().setAll(TipoId.values());
    }

    @FXML
    void onCrearClick(ActionEvent event) {
        TipoId tipoId = comboBoxTipoId.getValue();
        String numeroId = fieldNumeroId.getText().trim();
        String nombres = fieldNombres.getText().trim();
        String apellidos = fieldApellidos.getText().trim();
        String cargo = fieldCargo.getText().trim();
        String usuario = fieldUsuario.getText().trim();
        String contrasena = fieldContrasena.getText(); // No trim password
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (tipoId == null || numeroId.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() ||
                cargo.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        // Validar si el ID ya existe
        if (sistema.getGestorUsuarios().buscarEmpleado(numeroId) != null) {
            mostrarError("Ya existe un empleado con el ID " + numeroId + ".");
            return;
        }
        // TODO: Podrías añadir validación para que el nombre de usuario no se repita

        try {
            Empleado nuevoEmpleado = new Empleado(tipoId, numeroId, nombres, apellidos, cargo, usuario, contrasena);
            sistema.getGestorUsuarios().agregarEmpleado(nuevoEmpleado, admin); //
            mostrarExito("Empleado " + nombres + " " + apellidos + " agregado con éxito.");
            limpiarCampos();

        } catch (Exception e) {
            mostrarError("Error al guardar el empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        comboBoxTipoId.setValue(null);
        fieldNumeroId.clear();
        fieldNombres.clear();
        fieldApellidos.clear();
        fieldCargo.clear();
        fieldUsuario.clear();
        fieldContrasena.clear();
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}