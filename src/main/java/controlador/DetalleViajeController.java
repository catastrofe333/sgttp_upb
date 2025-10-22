package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;

import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.CategoriaBoleto;
import modelo.enums.Estacion; // Asegúrate que esté importado
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date; // Asegúrate que esté importado
import java.util.Locale;

public class DetalleViajeController {

    @FXML private Label labelIdViaje;
    @FXML private VBox cardPremium;
    @FXML private Label labelPrecioPremium;
    @FXML private Label labelDisponiblesPremium;
    @FXML private VBox cardEjecutivo;
    @FXML private Label labelPrecioEjecutivo;
    @FXML private Label labelDisponiblesEjecutivo;
    @FXML private VBox cardEstandar;
    @FXML private Label labelPrecioEstandar;
    @FXML private Label labelDisponiblesEstandar;

    private final Sistema sistema = Aplicacion.getSistema();
    private Viaje viajeActual;
    private Ruta rutaActual;
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // Variables para guardar la búsqueda original
    private Estacion origenBusqueda;
    private Estacion destinoBusqueda;
    private Date fechaBusqueda;
    private boolean esBusquedaDirectaGlobal;

    /**
     * Método llamado desde TarjetaViajeController.
     * Guarda el viaje y los parámetros de búsqueda.
     */
    public void inicializarDatos(Viaje viaje, Estacion origen, Estacion destino, Date fecha, boolean esBusquedaDirecta) {
        // Guarda el viaje actual y busca su ruta
        this.viajeActual = viaje;
        this.rutaActual = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta()); //

        // --- Guarda los parámetros de la búsqueda original ---
        this.origenBusqueda = origen;
        this.destinoBusqueda = destino;
        this.fechaBusqueda = fecha;
        this.esBusquedaDirectaGlobal = esBusquedaDirecta;
        // ----------------------------------------------------

        // Verifica si la ruta se encontró
        if (rutaActual == null) {
            System.err.println("Error: No se encontró la ruta " + viaje.getIdRuta() + " para el viaje " + viaje.getIdViaje());
            labelIdViaje.setText("Error - Ruta no encontrada");
            cardPremium.setDisable(true);
            cardEjecutivo.setDisable(true);
            cardEstandar.setDisable(true);
            return;
        }

        // Muestra la información del viaje en la UI
        labelIdViaje.setText(viaje.getIdViaje());
        double precioBase = rutaActual.getValorBase(); //

        // Configura la tarjeta Premium
        labelPrecioPremium.setText(currencyFormatter.format(precioBase * CategoriaBoleto.PREMIUM.getIncrementoPrecio())); //
        labelDisponiblesPremium.setText(String.valueOf(viaje.getDisponiblesPremium())); //
        cardPremium.setDisable(viaje.getDisponiblesPremium() <= 0);
        cardPremium.setStyle(viaje.getDisponiblesPremium() <= 0 ? "-fx-opacity: 0.5;" : "");

        // Configura la tarjeta Ejecutiva
        labelPrecioEjecutivo.setText(currencyFormatter.format(precioBase * CategoriaBoleto.EJECUTIVA.getIncrementoPrecio())); //
        labelDisponiblesEjecutivo.setText(String.valueOf(viaje.getDisponiblesEjecutiva())); //
        cardEjecutivo.setDisable(viaje.getDisponiblesEjecutiva() <= 0);
        cardEjecutivo.setStyle(viaje.getDisponiblesEjecutiva() <= 0 ? "-fx-opacity: 0.5;" : "");

        // Configura la tarjeta Estándar
        labelPrecioEstandar.setText(currencyFormatter.format(precioBase * CategoriaBoleto.ESTANDAR.getIncrementoPrecio())); //
        labelDisponiblesEstandar.setText(String.valueOf(viaje.getDisponiblesEstandar())); //
        cardEstandar.setDisable(viaje.getDisponiblesEstandar() <= 0);
        cardEstandar.setStyle(viaje.getDisponiblesEstandar() <= 0 ? "-fx-opacity: 0.5;" : "");
    }

    // --- Métodos para manejar el clic en cada tarjeta ---
    @FXML
    void onCardPremiumClick(MouseEvent event) {
        if (!cardPremium.isDisabled()) {
            System.out.println("Clic en Premium");
            navegarSiguientePantalla(CategoriaBoleto.PREMIUM);
        }
    }

    @FXML
    void onCardEjecutivoClick(MouseEvent event) {
        if (!cardEjecutivo.isDisabled()) {
            System.out.println("Clic en Ejecutivo");
            navegarSiguientePantalla(CategoriaBoleto.EJECUTIVA);
        }
    }

    @FXML
    void onCardEstandarClick(MouseEvent event) {
        if (!cardEstandar.isDisabled()) {
            System.out.println("Clic en Estándar");
            navegarSiguientePantalla(CategoriaBoleto.ESTANDAR);
        }
    }

    /**
     * Método auxiliar para cargar la siguiente pantalla (ej. formulario pasajero).
     */
    private void navegarSiguientePantalla(CategoriaBoleto categoriaSeleccionada) {
        System.out.println("Navegando a formulario pasajero con Viaje: " + viajeActual.getIdViaje() + " y Categoría: " + categoriaSeleccionada); //

        // --- CÓDIGO ACTUALIZADO ---
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/formulario_pasajero.fxml")); //
            Parent root = loader.load();
            FormularioPasajeroController controller = loader.getController();

            // Llama al inicializador del nuevo controlador
            controller.inicializarDatos(viajeActual, categoriaSeleccionada);

            // Obtiene la escena desde CUALQUIERA de las tarjetas (ej. cardPremium)
            // Necesitamos subir al padre (HBox) y luego a la escena.
            Scene scene = cardPremium.getScene(); // Obtener escena actual
            if (scene != null) {
                scene.setRoot(root); // Cambiar contenido
            } else {
                System.err.println("Error: No se pudo obtener la escena actual.");
            }

        } catch (IOException e) {
            System.err.println("Error al cargar formulario_pasajero.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        // --- FIN CÓDIGO ACTUALIZADO ---
    }

    /**
     * Acción del botón 'Atrás'. Vuelve a la pantalla de resultados
     * recreando la búsqueda original.
     */
    @FXML
    void onBackClick(ActionEvent event) {
        try {
            Viaje[] viajesAMostrar;
            String tituloResultados;

            // --- LÓGICA MODIFICADA PARA VOLVER ATRÁS ---
            if (origenBusqueda == null && destinoBusqueda == null) {
                // Cargar TODOS los viajes visibles
                viajesAMostrar = sistema.getGestorViajes().buscarViajesVisibles(); //
                tituloResultados = "Todos los Viajes Disponibles";
            } else {
                // Recrea la búsqueda específica original

                // !!!!! LÍNEA MODIFICADA PARA EVITAR NULLPOINTER !!!!!
                // Comprueba que origenBusqueda y destinoBusqueda no sean null antes de usarlos para el título
                if (origenBusqueda != null && destinoBusqueda != null && fechaBusqueda != null) {
                    tituloResultados = origenBusqueda.toString() + " a " + destinoBusqueda.toString() + ", " + new SimpleDateFormat("dd MMM yyyy").format(fechaBusqueda);
                } else {
                    // Fallback por si acaso algo es nulo (no debería pasar si no es "Todos los Viajes")
                    tituloResultados = "Resultados de Búsqueda";
                    System.err.println("Advertencia en onBackClick: origen, destino o fecha eran null inesperadamente.");
                }


                Ruta[] rutasDirectas = sistema.getGestorRuta().buscarRutasPorOrigenDestino(origenBusqueda, destinoBusqueda); //
                Viaje[] viajesDirectos = sistema.getGestorViajes().buscarViajesPorRuta(rutasDirectas, fechaBusqueda); //

                if (viajesDirectos != null && viajesDirectos.length > 0) {
                    viajesAMostrar = viajesDirectos;
                } else {
                    // Solo busca indirectos si la búsqueda original los incluyó (o si origen/destino no son null)
                    if(origenBusqueda != null && destinoBusqueda != null) {
                        Ruta[] rutasOrigen = sistema.getGestorRuta().buscarRutasPorOrigen(origenBusqueda); //
                        Viaje[] viajesOrigen = sistema.getGestorViajes().buscarViajesPorRuta(rutasOrigen, fechaBusqueda); //

                        Ruta[] rutasDestino = sistema.getGestorRuta().buscarRutasPorDestino(destinoBusqueda); //
                        Viaje[] viajesDestino = sistema.getGestorViajes().buscarViajesPorRuta(rutasDestino, fechaBusqueda); //

                        viajesAMostrar = sistema.getGestorViajes().concatenarViajes(viajesOrigen, viajesDestino); //
                    } else {
                        // Si origen o destino eran null aquí (no debería pasar), devuelve array vacío
                        viajesAMostrar = new Viaje[0];
                    }
                }
            }
            // ----- FIN DE LA LÓGICA MODIFICADA -----

            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/resultados_busqueda_rutas.fxml")); //
            Parent root = loader.load();
            ResultadosController controllerResultadosController = loader.getController();

            controllerResultadosController.inicializarDatos(viajesAMostrar, origenBusqueda, destinoBusqueda, fechaBusqueda, esBusquedaDirectaGlobal);
            controllerResultadosController.setTituloBusqueda(tituloResultados);

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}