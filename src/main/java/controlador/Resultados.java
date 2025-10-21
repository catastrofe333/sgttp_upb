package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML; // ⬅️ ¡IMPORTA EL @FXML!
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultados {
    @FXML // ⬅️ ANOTACIÓN FALTANTE
    public Button iniciar_sesion;
    @FXML // ⬅️ ANOTACIÓN FALTANTE
    public ScrollPane scrollPane;
    @FXML // ⬅️ ANOTACIÓN FALTANTE
    public VBox vbox_tarjetas;

    // --- ⬇️ ESTAS SON LAS LÍNEAS QUE FALTABAN Y CAUSARON EL ERROR ⬇️ ---
    @FXML
    private Label labelBusqueda; // Ahora esto se conectará con el FXML
    @FXML
    private VBox vbox_no_resultados; // Para el panel de "No hay viajes"
    @FXML
    private Button btnVerOtrasRutas;
    // --- ⬆️ FIN DE LÍNEAS FALTANTES ⬆️ ---

    private final Sistema sistema = Aplicacion.getSistema();
    private final SimpleDateFormat sdfFechaTitulo = new SimpleDateFormat("dd MMM yyyy");

    /**
     * Este método es llamado por 'Inicio.java' para pasar los datos.
     */
    public void inicializarDatos(Viaje[] viajes, Estacion origen, Estacion destino, Date fecha) {

        // 1. Actualizar el título
        // ¡Esta línea ya no dará error porque labelBusqueda SÍ estará conectado!
        labelBusqueda.setText(
                origen.toString() + " a " + destino.toString() + ", " + sdfFechaTitulo.format(fecha)
        );

        // 2. Lógica para mostrar tarjetas O el mensaje de "no hay viajes"
        if (viajes == null || viajes.length == 0) {
            // NO HAY VIAJES:
            vbox_tarjetas.setVisible(false); // Oculta el VBox de tarjetas
            vbox_no_resultados.setVisible(true); // Muestra el VBox azul
        } else {
            // SÍ HAY VIAJES:
            vbox_tarjetas.setVisible(true); // Muestra el VBox de tarjetas
            vbox_no_resultados.setVisible(false); // Oculta el VBox azul

            // Limpiar las tarjetas de ejemplo (esto lo pediste)
            vbox_tarjetas.getChildren().clear();

            // 3. Crear y añadir las tarjetas
            for (Viaje viaje : viajes) {
                try {
                    FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/tarjeta_viaje.fxml"));
                    HBox tarjetaNode = loader.load();
                    TarjetaViajeController tarjetaController = loader.getController();

                    // Asegúrate de que GestorRuta tiene 'buscarRutaPorId'
                    Ruta rutaDelViaje = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta());

                    if (rutaDelViaje != null) {
                        tarjetaController.setDatos(viaje, rutaDelViaje);
                        vbox_tarjetas.getChildren().add(tarjetaNode);
                    } else {
                        System.err.println("No se encontró la ruta con ID: " + viaje.getIdRuta());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    public void onIniciarClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onBackClick(ActionEvent actionEvent) {
        // Volver a la pantalla de inicio
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/inicio.fxml"));
            Parent root = fxmlLoader.load(); // Carga el VBox raíz del inicio

            // --- ⬇️ APLICA LA MISMA SOLUCIÓN AQUÍ ⬇️ ---
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root);

            // Ya NO necesitas stage.setScene() ni stage.setFullScreen()

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODO PARA EL NUEVO BOTÓN "VER" ---
    @FXML
    void onVerOtrasRutasClick(ActionEvent event) {
        System.out.println("Botón 'VER OTRAS RUTAS' presionado.");
        // Aquí puedes añadir lógica para buscar otras rutas
    }
}