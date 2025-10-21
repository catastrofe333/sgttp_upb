package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea; // Opcional para mostrar resultados
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminTrenesController {

    @FXML private Button btnAgregarTren;
    @FXML private Button btnEliminarTren;
    @FXML private Button btnAgregarVagon;
    @FXML private Button btnEliminarVagon;
    @FXML private Button btnConsultarTren;

    // Opcional: Si quieres mostrar info en un TextArea
    // @FXML private TextArea textAreaResultados;

    private Sistema sistema = Aplicacion.getSistema();

    /**
     * Se llama al cargar esta vista. Puedes usarlo para inicializar
     * datos si es necesario (ej. cargar lista de trenes en una tabla).
     */
    @FXML
    public void initialize() {
        System.out.println("Controlador AdminTrenesController inicializado.");
        // Aquí podrías, por ejemplo, cargar los trenes existentes en una tabla
        // o limpiar el área de resultados.
    }

    // --- Métodos para cada botón ---

    @FXML
    void onAgregarTrenClick(ActionEvent event) {
        System.out.println("Botón 'Agregar Tren' presionado.");
        // Aquí abrirías una nueva ventana/diálogo para ingresar los datos del nuevo tren
        // o cargarías un formulario en esta misma área.
        // Ejemplo: cargarFormulario("/admin_trenes_agregar.fxml");
    }

    @FXML
    void onEliminarTrenClick(ActionEvent event) {
        System.out.println("Botón 'Eliminar Tren' presionado.");
        // Aquí pedirías el ID del tren a eliminar y llamarías a:
        // sistema.getGestorTrenes().eliminarTren(idTren, (Administrador) sistema.getSesionActual());
        // (Necesitarás obtener el ID de alguna forma, ej. un TextField o seleccionando de una tabla)
    }

    @FXML
    void onAgregarVagonClick(ActionEvent event) {
        System.out.println("Botón 'Agregar Vagón' presionado.");
        // Similar a agregar tren, pedirías ID del tren y datos del vagón.
        // Luego llamarías a sistema.getGestorTrenes().agregarVagonPasajeros(...) o agregarVagonCarga(...)
    }

    @FXML
    void onEliminarVagonClick(ActionEvent event) {
        System.out.println("Botón 'Eliminar Vagón' presionado.");
        // Pedirías ID del tren e ID del vagón a eliminar.
        // Llamarías a sistema.getGestorTrenes().eliminarVagonPasajeros(...) o eliminarVagonCarga(...)
    }

    @FXML
    void onConsultarTrenClick(ActionEvent event) {
        System.out.println("Botón 'Consultar Tren' presionado.");
        // Pedirías el ID del tren a consultar.
        // Tren tren = sistema.getGestorTrenes().buscarTren(idTren);
        // if (tren != null) {
        //     textAreaResultados.setText(tren.toString()); // Mostrar info en el TextArea
        // } else {
        //     textAreaResultados.setText("Tren con ID " + idTren + " no encontrado.");
        // }
    }

    // --- Método auxiliar (ejemplo) para cargar formularios ---
    /*
    private void cargarFormulario(String fxmlPath) {
        try {
            // Asumiendo que quieres cargar el formulario en el 'contentArea' del PanelAdminController
            // Esto requiere obtener una referencia a ese controlador, lo cual puede ser complejo.
            // Una alternativa más simple es abrir una NUEVA VENTANA (Stage) para los formularios.

            // Ejemplo abriendo nueva ventana:
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Tren"); // O el título correspondiente
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
            stage.showAndWait(); // Espera a que se cierre la ventana

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}