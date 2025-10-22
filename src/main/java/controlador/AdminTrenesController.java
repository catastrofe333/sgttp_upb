// Archivo: src/main/java/controlador/AdminTrenesController.java

package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane; // Importante para cargar contenido
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;

public class AdminTrenesController {

    // Los botones ya están definidos aquí
    @FXML private Button btnAgregarTren;
    @FXML private Button btnEliminarTren;
    @FXML private Button btnAgregarVagon;
    @FXML private Button btnEliminarVagon;
    @FXML private Button btnConsultarTren;

    // Nuevo: Necesitas el StackPane del PanelAdminController para cargar la sub-vista
    // Sin embargo, por la estructura, el StackPane 'contentArea' está en PanelAdminController.
    // Una forma simple es asumir que el padre de este controlador (AdminTrenesController)
    // es un nodo que está dentro de contentArea. Como esta vista no debe cargar otra sub-vista,
    // vamos a usar el método más robusto de obtener el padre (PanelAdminController)
    // o, más simple, cargar directamente el FXML en el StackPane (que es lo que ya hace PanelAdminController).

    private final Sistema sistema = Aplicacion.getSistema();

    /**
     * Se llama al cargar esta vista. Puedes usarlo para inicializar
     * datos si es necesario (ej. cargar lista de trenes en una tabla).
     */
    @FXML
    public void initialize() {
        System.out.println("Controlador AdminTrenesController inicializado.");
        // Opcional: Podrías cargar la vista de consulta por defecto aquí
        // loadSubContent("/admin_trenes_consultar.fxml", btnConsultar);
    }

    // --- Métodos para cada botón, ahora cargan una nueva sub-vista ---

    @FXML
    void onAgregarTrenClick(ActionEvent event) {
        loadSubContent("/admin_trenes_agregar.fxml", event);
    }

    @FXML
    void onEliminarTrenClick(ActionEvent event) {
        loadSubContent("/admin_trenes_eliminar.fxml", event);
    }

    @FXML
    void onAgregarVagonClick(ActionEvent event) {
        loadSubContent("/admin_trenes_agregar_vagon.fxml", event);
    }

    @FXML
    void onEliminarVagonClick(ActionEvent event) {
        loadSubContent("/admin_trenes_eliminar_vagon.fxml", event);
    }

    @FXML
    void onConsultarTrenClick(ActionEvent event) {
        loadSubContent("/admin_trenes_consultar.fxml", event);
    }

    /**
     * Carga un archivo FXML *dentro del PanelAdminController* (no dentro de esta sub-vista).
     * Nota: Necesitas que el nodo raíz de esta vista (admin_trenes.fxml) esté dentro del
     * StackPane (contentArea) del PanelAdminController.
     * Esto requiere un patrón de comunicación entre controladores.
     * La solución más simple para este flujo (que es un menú dentro de otro menú) es:
     * 1. Conseguir la referencia al controlador principal (PanelAdminController).
     * 2. Llamar al método `loadContent` del controlador principal.
     */
    private void loadSubContent(String fxmlPath, ActionEvent event) {
        try {
            // 1. Encontrar el StackPane (contentArea) que es el padre de esta vista.
            // La forma más fácil es navegar hacia arriba en el árbol de nodos
            // hasta encontrar el nodo raíz del PanelAdminController (BorderPane).

            // Subir dos niveles para encontrar el StackPane (contentArea) del PanelAdminController
            // (Esta sub-vista está dentro de admin_trenes.fxml, que a su vez está en contentArea)
            // Asumiendo la jerarquía: Button -> VBox(admin_trenes.fxml) -> StackPane (contentArea)
            StackPane contentArea = (StackPane) ((Parent) ((Button) event.getSource()).getParent().getParent().getParent()).getScene().lookup("#contentArea");

            if (contentArea != null) {
                // Cargar el FXML hijo
                Parent subVista = FXMLLoader.load(Aplicacion.class.getResource(fxmlPath));

                // Limpiar el área de contenido y añadir la nueva vista
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);

                // Opcional: Podrías querer volver a cargar admin_trenes.fxml primero
                // para que el StackPane se vea limpio antes de la nueva sub-vista.
                // Pero para simplificar, cargamos la sub-vista directamente.
            } else {
                System.err.println("Error: No se encontró el StackPane 'contentArea'.");
            }

        } catch (IOException e) {
            System.err.println("Error al cargar la sub-vista: " + fxmlPath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error general en loadSubContent: " + e.getMessage());
            e.printStackTrace();
        }
    }
}