package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node; // Importante para obtener la escena actual
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import vista.Aplicacion; // Necesario para cargar el FXML

import java.io.IOException; // Necesario para manejar errores de carga FXML
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion; // Asegúrate que esté
import vista.Aplicacion;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TarjetaViajeController {

    @FXML
    private Label labelHoraSalida;
    @FXML
    private Label labelOrigen;
    @FXML
    private Label labelHoraLlegada;
    @FXML
    private Label labelDestino;
    @FXML
    private Button btnVer;
    @FXML
    private Label labelRecomendado;

    private final SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    private Viaje viajeActual;

    private Estacion origenBusqueda;
    private Estacion destinoBusqueda;
    private Date fechaBusqueda;
    private boolean esBusquedaDirectaGlobal;

    /**
     * Rellena los labels de la tarjeta con la info del viaje y la ruta.
     * Es llamado por el controlador 'Resultados'.
     */
    public void setDatos(Viaje viaje, Ruta ruta) {
        this.viajeActual = viaje;

        setRecomendadoVisible(false);
        // --- Pequeña validación por si la ruta no se encontró ---
        if (ruta == null) {
            System.err.println("Error: Ruta es null para el viaje " + (viaje != null ? viaje.getIdViaje() : "desconocido"));
            // Intenta mostrar algo útil aunque la ruta falte
            labelOrigen.setText("Origen desc.");
            labelDestino.setText("Destino desc.");
            if (viaje != null) {
                labelHoraSalida.setText(sdfHora.format(viaje.getFechaSalida()));
                labelHoraLlegada.setText(sdfHora.format(viaje.getFechaLlegada()));
            } else {
                // Si incluso el viaje es nulo, muestra placeholders
                labelHoraSalida.setText("--:--");
                labelHoraLlegada.setText("--:--");
            }
            btnVer.setDisable(true); // Deshabilitar botón si hay error
            return; // Salir del método si la ruta es nula
        }
        // --- Fin validación ---

        // Si la ruta existe, rellena los datos normalmente
        labelHoraSalida.setText(sdfHora.format(viaje.getFechaSalida()));
        labelHoraLlegada.setText(sdfHora.format(viaje.getFechaLlegada()));
        labelOrigen.setText(ruta.getOrigen().toString());
        labelDestino.setText(ruta.getDestino().toString());
        btnVer.setDisable(false); // Asegurarse de que el botón esté habilitado si todo está bien
    }

    public void setParametrosBusqueda(Estacion origen, Estacion destino, Date fecha, boolean esBusquedaDirecta) {
        this.origenBusqueda = origen;
        this.destinoBusqueda = destino;
        this.fechaBusqueda = fecha;
        this.esBusquedaDirectaGlobal = esBusquedaDirecta;
    }

    public void setRecomendadoVisible(boolean visible) {
        if (labelRecomendado != null) {
            labelRecomendado.setVisible(visible);
            labelRecomendado.setManaged(visible);
        }
    }

    @FXML
    void onVerClick(ActionEvent event) {
        // --- ⬇️ ESTA ES LA LÓGICA NUEVA PARA CAMBIAR DE VISTA ⬇️ ---
        if (viajeActual == null) {
            System.err.println("Error: No se puede navegar porque viajeActual es nulo.");
            return; // No hacer nada si no hay viaje
        }

        try {
            // 1. Cargar el FXML de la vista de detalle
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/detalle_viaje.fxml"));
            Parent root = loader.load(); // Carga el VBox raíz de la nueva vista

            // 2. Obtener el controlador de la nueva vista
            DetalleViajeController controllerDetalle = loader.getController();

            // 3. Pasar el objeto 'viajeActual' al nuevo controlador
            controllerDetalle.inicializarDatos(viajeActual, origenBusqueda, destinoBusqueda, fechaBusqueda, esBusquedaDirectaGlobal);

            // 4. Obtener la escena actual y cambiar su contenido (transición suave)
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);

        } catch (IOException e) {
            System.err.println("Error al cargar detalle_viaje.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error al obtener la escena o nodo: " + e.getMessage());
            e.printStackTrace();
        }
        // --- ⬆️ FIN DE LA LÓGICA NUEVA ⬆️ ---
    }
}