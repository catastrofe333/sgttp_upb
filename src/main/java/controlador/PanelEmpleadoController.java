package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import modelo.entidades.Empleado;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;

public class PanelEmpleadoController {

    @FXML private Label labelTituloEmpleado;
    @FXML private Button btnEquipajes;
    @FXML private Button btnBoletos;
    @FXML private Button btnViajes;
    @FXML private StackPane contentAreaEmpleado;
    @FXML private Label labelBienvenida;

    private final Sistema sistema = Aplicacion.getSistema();
    private Button botonSeleccionadoActual = null;

    @FXML
    public void initialize() {
        // Mostrar nombre del empleado logueado
        Empleado empleado = (Empleado) sistema.getSesionActual();
        if (empleado != null) {
            labelTituloEmpleado.setText("EMPLEADO: " + empleado.getNombres().toUpperCase()); //
            labelBienvenida.setText("Bienvenido, " + empleado.getNombres()); //
        } else {
            labelTituloEmpleado.setText("EMPLEADO (SESIÓN INVÁLIDA)");
            labelBienvenida.setText("Error al cargar sesión");
        }
        System.out.println("PanelEmpleadoController inicializado.");
    }

    @FXML
    void onEquipajesClick(ActionEvent event) {
        System.out.println("Cargando gestión de Equipajes...");
        loadContent("/empleado_equipajes.fxml");
        seleccionarBoton(btnEquipajes);
    }

    @FXML
    void onBoletosClick(ActionEvent event) {
        System.out.println("Cargando gestión de Boletos...");
        loadContent("/empleado_boletos.fxml");
        seleccionarBoton(btnBoletos);
    }

    @FXML
    void onViajesClick(ActionEvent event) {
        System.out.println("Cargando gestión de Viajes...");
        loadContent("/empleado_viajes.fxml");
        seleccionarBoton(btnViajes);
    }

    @FXML
    void onLogoutClick(ActionEvent event) {
        System.out.println("Cerrando sesión de Empleado...");
        sistema.setSesionActual(null); //

        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/inicio.fxml")); //
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath)); //
            contentAreaEmpleado.getChildren().clear();
            contentAreaEmpleado.getChildren().add(subVista);
        } catch (IOException | NullPointerException e) { // Captura ambos errores
            String errorMsg = (e instanceof NullPointerException) ? "No se encontró el archivo FXML: " : "Error al cargar la sub-vista: ";
            System.err.println(errorMsg + fxmlPath);
            e.printStackTrace();
            contentAreaEmpleado.getChildren().clear();
            contentAreaEmpleado.getChildren().add(new Label("Error al cargar: " + fxmlPath.substring(1))); // Muestra sin el / inicial
        }
    }

    private void seleccionarBoton(Button nuevoBotonSeleccionado) {
        if (botonSeleccionadoActual != null) {
            botonSeleccionadoActual.getStyleClass().remove("adminMenuButton-selected"); //
        }
        if (nuevoBotonSeleccionado != null) {
            nuevoBotonSeleccionado.getStyleClass().add("adminMenuButton-selected"); //
        }
        botonSeleccionadoActual = nuevoBotonSeleccionado;
    }
}