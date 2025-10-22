package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import vista.Aplicacion;

import java.io.IOException;

public class AdminEmpleadosController {

    @FXML private Button btnAgregarEmpleado;
    @FXML private Button btnEliminarEmpleado;
    @FXML private Button btnModificarCargo;
    @FXML private Button btnModificarUsuario;
    @FXML private Button btnModificarContrasena;
    @FXML private Button btnConsultarEmpleado;

    @FXML public void initialize() { System.out.println("AdminEmpleadosController inicializado."); }

    @FXML void onAgregarEmpleadoClick(ActionEvent event) { loadSubContent("/admin_empleados_agregar.fxml", event); }
    @FXML void onEliminarEmpleadoClick(ActionEvent event) { loadSubContent("/admin_empleados_eliminar.fxml", event); }
    @FXML void onModificarCargoClick(ActionEvent event) { loadSubContent("/admin_empleados_modificar_cargo.fxml", event); }
    @FXML void onModificarUsuarioClick(ActionEvent event) { loadSubContent("/admin_empleados_modificar_usuario.fxml", event); }
    @FXML void onModificarContrasenaClick(ActionEvent event) { loadSubContent("/admin_empleados_modificar_contrasena.fxml", event); }
    @FXML void onConsultarEmpleadoClick(ActionEvent event) { loadSubContent("/admin_empleados_consultar.fxml", event); }

    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            StackPane contentArea = (StackPane) ((Parent) ((Button) event.getSource()).getParent().getParent().getParent()).getScene().lookup("#contentArea");
            if (contentArea != null) {
                Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath));
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else { System.err.println("Error: No se encontró StackPane 'contentArea'."); }
        } catch (IOException e) { System.err.println("Error al cargar sub-vista: " + fxmlPath); e.printStackTrace();
        } catch (Exception e) { System.err.println("Error general en loadSubContent: " + e.getMessage()); e.printStackTrace(); }
    }
}