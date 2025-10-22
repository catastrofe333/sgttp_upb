package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelo.entidades.Administrador; // Para verificar el login
import modelo.entidades.Empleado;      // Para verificar el login
import modelo.entidades.Usuario;       // Tipo genérico
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;

public class LoginController {

    @FXML private TextField textFieldUsuario;
    @FXML private PasswordField passwordFieldContrasena;
    @FXML private ComboBox<String> comboBoxRol; // Usaremos Strings para "Administrador" y "Empleado"
    @FXML private Label labelError;
    @FXML private Button btnIngresar;

    private Sistema sistema = Aplicacion.getSistema();

    /**
     * Se llama automáticamente después de cargar el FXML.
     * Aquí poblamos el ComboBox de Roles.
     */
    @FXML
    public void initialize() {
        comboBoxRol.getItems().addAll("Administrador", "Empleado");
    }

    @FXML
    void onIngresarClick(ActionEvent event) {
        String usuario = textFieldUsuario.getText();
        String contrasena = passwordFieldContrasena.getText();
        String rolSeleccionado = comboBoxRol.getValue();

        // Validaciones básicas
        if (usuario.isEmpty() || contrasena.isEmpty() || rolSeleccionado == null) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        Usuario usuarioAutenticado = null;

        // Intentar autenticar según el rol
        if (rolSeleccionado.equals("Administrador")) {
            usuarioAutenticado = sistema.getGestorUsuarios().iniciarSesionAdministrador(usuario, contrasena);
        } else if (rolSeleccionado.equals("Empleado")) {
            usuarioAutenticado = sistema.getGestorUsuarios().iniciarSesionEmpleado(usuario, contrasena);
        }

        // Verificar resultado
        if (usuarioAutenticado != null) {
            System.out.println("Inicio de sesión exitoso como: " + rolSeleccionado);
            ocultarError();

            // Guardar la sesión actual en el Sistema
            sistema.setSesionActual(usuarioAutenticado);

            // TODO: Navegar a la pantalla correspondiente según el rol
            if (usuarioAutenticado instanceof Administrador) {
                System.out.println("Navegando al panel de Administrador...");
                cargarVista("/panel_admin.fxml", event);
            } else {
                System.out.println("Navegando al panel de Empleado...");
                cargarVista("/panel_empleado.fxml", event);
            }

        } else {
            System.out.println("Error de inicio de sesión.");
            mostrarError("Usuario, contraseña o rol incorrectos.");
            // Limpiar campo de contraseña por seguridad
            passwordFieldContrasena.clear();
        }
    }

    @FXML
    void onBackClick(ActionEvent event) {
        // Volver a la pantalla de inicio
        cargarVista("/inicio.fxml", event);
    }

    // --- Métodos Auxiliares ---

    private void mostrarError(String mensaje) {
        labelError.setText(mensaje);
        labelError.setVisible(true);
        labelError.setManaged(true); // Asegura que ocupe espacio
    }

    private void ocultarError() {
        labelError.setVisible(false);
        labelError.setManaged(false); // Asegura que no ocupe espacio
    }

    /**
     * Método genérico para cambiar de vista usando setRoot (transición suave).
     */
    private void cargarVista(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar la vista: " + fxmlPath);
        }
    }
}