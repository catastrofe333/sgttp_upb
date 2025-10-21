package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane; // Importante para el área de contenido
import javafx.stage.Stage;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;

public class PanelAdminController {

    @FXML private Button btnTrenes;
    @FXML private Button btnRutas;
    @FXML private Button btnViajes;
    @FXML private Button btnEmpleados;
    @FXML private Button btnHistorial;
    @FXML private StackPane contentArea; // El panel central donde se carga el contenido

    private Sistema sistema = Aplicacion.getSistema();

    // Variable para mantener el botón seleccionado resaltado
    private Button botonSeleccionadoActual = null;

    /**
     * Se llama al iniciar. Puedes cargar una vista por defecto aquí si quieres.
     */
    @FXML
    public void initialize() {
        // Opcional: Cargar la vista de "Trenes" por defecto al iniciar
        // loadContent("/admin_trenes.fxml");
        // seleccionarBoton(btnTrenes); // Resaltar el botón Trenes
    }

    // --- Métodos para los botones del menú lateral ---

    @FXML
    void onTrenesClick(ActionEvent event) {
        System.out.println("Cargando gestión de Trenes...");
        loadContent("/admin_trenes.fxml"); // Asume que tienes un FXML para gestionar trenes
        seleccionarBoton(btnTrenes);
    }

    @FXML
    void onRutasClick(ActionEvent event) {
        System.out.println("Cargando gestión de Rutas...");
        loadContent("/admin_rutas.fxml");   // Asume que tienes un FXML para gestionar rutas
        seleccionarBoton(btnRutas);
    }

    @FXML
    void onViajesClick(ActionEvent event) {
        System.out.println("Cargando gestión de Viajes...");
        loadContent("/admin_viajes.fxml");  // Asume que tienes un FXML para gestionar viajes
        seleccionarBoton(btnViajes);
    }

    @FXML
    void onEmpleadosClick(ActionEvent event) {
        System.out.println("Cargando gestión de Empleados...");
        loadContent("/admin_empleados.fxml"); // Asume que tienes un FXML para gestionar empleados
        seleccionarBoton(btnEmpleados);
    }

    @FXML
    void onHistorialClick(ActionEvent event) {
        System.out.println("Cargando Historial...");
        loadContent("/admin_historial.fxml"); // Asume que tienes un FXML para ver el historial
        seleccionarBoton(btnHistorial);
    }

    // --- Método para el botón de Logout/Atrás ---

    @FXML
    void onLogoutClick(ActionEvent event) {
        System.out.println("Cerrando sesión de Administrador...");
        // Limpiar la sesión actual en el Sistema
        sistema.setSesionActual(null);

        // Volver a la pantalla de inicio
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/inicio.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos Auxiliares ---

    /**
     * Carga un archivo FXML dentro del StackPane central (contentArea).
     * Reemplaza cualquier contenido anterior.
     */
    private void loadContent(String fxmlPath) {
        try {
            // Cargar el FXML hijo
            Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath));

            // Limpiar el área de contenido y añadir la nueva vista
            contentArea.getChildren().clear();
            contentArea.getChildren().add(subVista);

        } catch (IOException e) {
            System.err.println("Error al cargar la sub-vista: " + fxmlPath);
            e.printStackTrace();
            // Mostrar un mensaje de error en el contentArea
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new Label("Error al cargar: " + fxmlPath));
        } catch (NullPointerException e) {
            System.err.println("Error: No se encontró el archivo FXML: " + fxmlPath);
            contentArea.getChildren().clear();
            contentArea.getChildren().add(new Label("No se encontró: " + fxmlPath));
        }
    }

    /**
     * Resalta visualmente el botón seleccionado y quita el resaltado del anterior.
     */
    private void seleccionarBoton(Button nuevoBotonSeleccionado) {
        // Quitar estilo del botón anterior si existe
        if (botonSeleccionadoActual != null) {
            botonSeleccionadoActual.getStyleClass().remove("adminMenuButton-selected");
        }

        // Añadir estilo al nuevo botón
        if (nuevoBotonSeleccionado != null) {
            nuevoBotonSeleccionado.getStyleClass().add("adminMenuButton-selected");
        }

        // Actualizar la referencia
        botonSeleccionadoActual = nuevoBotonSeleccionado;
    }
}