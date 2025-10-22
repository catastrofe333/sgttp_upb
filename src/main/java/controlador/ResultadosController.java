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
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultadosController {
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

    private Estacion origenBusqueda;
    private Estacion destinoBusqueda;
    private Date fechaBusqueda;
    private boolean esBusquedaDirectaGlobal;

    /**
     * Este método es llamado por 'Inicio.java' para pasar los datos.
     */
    public void inicializarDatos(Viaje[] viajes, Estacion origen, Estacion destino, Date fecha, boolean esBusquedaDirecta) {

        // 1. Guardar los parámetros
        this.origenBusqueda = origen;
        this.destinoBusqueda = destino;
        this.fechaBusqueda = fecha;
        this.esBusquedaDirectaGlobal = esBusquedaDirecta;

        // 2. Actualizar el título
        if (labelBusqueda != null) { // Asegurarse que el label existe
            if (origen != null && destino != null) {
                // Caso normal: búsqueda con origen y destino
                labelBusqueda.setText(
                        origen + " a " + destino + ", " + sdfFechaTitulo.format(fecha)
                );
            } else {
                // Caso "Todos los Viajes": Poner un título temporal o vacío
                // (ya que se sobrescribirá desde InicioController con setTituloBusqueda)
                labelBusqueda.setText("Cargando viajes...");
            }
        }

        // 3. Lógica para mostrar tarjetas
        if (viajes == null || viajes.length == 0) {
            vbox_tarjetas.setVisible(false);
            vbox_no_resultados.setVisible(true);
        } else {
            vbox_tarjetas.setVisible(true);
            vbox_no_resultados.setVisible(false);
            vbox_tarjetas.getChildren().clear();

            // 4. Bucle para crear las tarjetas
            for (int i = 0; i < viajes.length; i++) {
                Viaje viaje = viajes[i];
                if (viaje == null) continue;

                try {
                    FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/tarjeta_viaje.fxml"));
                    // NOTA: Tu FXML puede ser un HBox o VBox, asegúrate que coincida
                    javafx.scene.layout.HBox tarjetaNode = loader.load();
                    TarjetaViajeController tarjetaController = loader.getController();

                    Ruta rutaDelViaje = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta());

                    if (rutaDelViaje != null) {
                        tarjetaController.setDatos(viaje, rutaDelViaje);

                        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        // !! ESTA ES LA LÍNEA QUE RESUELVE TU ERROR !!
                        // !! Asegúrate de tenerla en tu código:     !!
                        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        tarjetaController.setParametrosBusqueda(origenBusqueda, destinoBusqueda, fechaBusqueda, esBusquedaDirectaGlobal);


                        // Lógica "Recomendado"
                        if (i == 0 && esBusquedaDirecta) {
                            tarjetaController.setRecomendadoVisible(true);
                        }

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

    public void setTituloBusqueda(String texto) {
        if (labelBusqueda != null) {
            labelBusqueda.setText(texto);
        } else {
            System.err.println("Advertencia: Se intentó cambiar el título antes de que labelBusqueda estuviera inicializado.");
        }
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