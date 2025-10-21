package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent; // Importante para el clic en las tarjetas
import javafx.scene.layout.VBox;

import modelo.entidades.Ruta; // Necesitas la Ruta para calcular precios
import modelo.entidades.Viaje;
import modelo.enums.CategoriaBoleto; // Para saber qué categoría se eligió
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;
import java.text.NumberFormat; // Para formatear el precio
import java.util.Locale;

public class DetalleViajeController {

    @FXML private Button iniciar_sesion;
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

    private Sistema sistema = Aplicacion.getSistema();
    private Viaje viajeActual; // Guardaremos el viaje que se está viendo
    private Ruta rutaActual;   // Y su ruta

    // Formateador de moneda (ej: $150.000)
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * Método llamado desde el controlador anterior (TarjetaViajeController)
     * para pasar el Viaje seleccionado.
     */
    public void inicializarDatos(Viaje viaje) {
        this.viajeActual = viaje;
        this.rutaActual = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta());

        if (rutaActual == null) {
            // Manejar error si la ruta no se encuentra
            System.err.println("Error: No se encontró la ruta " + viaje.getIdRuta() + " para el viaje " + viaje.getIdViaje());
            // Podrías mostrar un mensaje de error o volver atrás
            return;
        }

        // 1. Mostrar ID del Viaje
        labelIdViaje.setText(viaje.getIdViaje());

        // 2. Calcular y mostrar precios y disponibilidad

        // Premium
        double precioBase = rutaActual.getValorBase();
        labelPrecioPremium.setText(currencyFormatter.format(precioBase * CategoriaBoleto.PREMIUM.getIncrementoPrecio()));
        labelDisponiblesPremium.setText(String.valueOf(viaje.getDisponiblesPremium()));
        // Deshabilitar tarjeta si no hay asientos
        if (viaje.getDisponiblesPremium() <= 0) {
            cardPremium.setDisable(true);
            cardPremium.setStyle("-fx-opacity: 0.5;"); // Hacerla semitransparente
        }

        // Ejecutivo
        labelPrecioEjecutivo.setText(currencyFormatter.format(precioBase * CategoriaBoleto.EJECUTIVA.getIncrementoPrecio()));
        labelDisponiblesEjecutivo.setText(String.valueOf(viaje.getDisponiblesEjecutiva()));
        if (viaje.getDisponiblesEjecutiva() <= 0) {
            cardEjecutivo.setDisable(true);
            cardEjecutivo.setStyle("-fx-opacity: 0.5;");
        }

        // Estándar
        labelPrecioEstandar.setText(currencyFormatter.format(precioBase * CategoriaBoleto.ESTANDAR.getIncrementoPrecio()));
        labelDisponiblesEstandar.setText(String.valueOf(viaje.getDisponiblesEstandar()));
        if (viaje.getDisponiblesEstandar() <= 0) {
            cardEstandar.setDisable(true);
            cardEstandar.setStyle("-fx-opacity: 0.5;");
        }
    }

    // --- Métodos para manejar el clic en cada tarjeta ---

    @FXML
    void onCardPremiumClick(MouseEvent event) {
        System.out.println("Clic en Premium");
        navegarSiguientePantalla(CategoriaBoleto.PREMIUM);
    }

    @FXML
    void onCardEjecutivoClick(MouseEvent event) {
        System.out.println("Clic en Ejecutivo");
        navegarSiguientePantalla(CategoriaBoleto.EJECUTIVA);
    }

    @FXML
    void onCardEstandarClick(MouseEvent event) {
        System.out.println("Clic en Estándar");
        navegarSiguientePantalla(CategoriaBoleto.ESTANDAR);
    }

    /**
     * Método auxiliar para cargar la siguiente pantalla (probablemente el formulario de pasajero)
     * pasándole el viaje y la categoría seleccionada.
     */
    private void navegarSiguientePantalla(CategoriaBoleto categoriaSeleccionada) {
        System.out.println("Navegando a la siguiente pantalla con Viaje: " + viajeActual.getIdViaje() + " y Categoría: " + categoriaSeleccionada);
        // Aquí cargarías el FXML de la siguiente vista (ej. formulario_pasajero.fxml)
        // y le pasarías 'viajeActual' y 'categoriaSeleccionada' a su controlador.
        /*
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/formulario_pasajero.fxml"));
            Parent root = loader.load();
            FormularioPasajeroController controller = loader.getController();
            controller.inicializarDatos(viajeActual, categoriaSeleccionada); // Necesitarás crear este método

            Scene scene = ((Node) cardPremium.getScene().getWindow().getScene()); // Obtener escena actual
            scene.setRoot(root); // Cambiar contenido

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }


    // --- Métodos de la barra superior ---

    @FXML
    void onBackClick(ActionEvent event) {
        // Volver a la pantalla de resultados de búsqueda
        try {
            // Necesitamos volver a buscar los viajes para pasárselos a la vista anterior
            Ruta[] rutas = { rutaActual }; // Asumimos que solo hay una ruta para este viaje
            Viaje[] viajes = sistema.getGestorViajes().buscarViajesPorRuta(rutas, viajeActual.getFechaSalida());

            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/resultados_busqueda_rutas.fxml"));
            Parent root = loader.load();
            ResultadosController controllerResultadosController = loader.getController();
            // Re-inicializamos la vista anterior con los datos originales
            controllerResultadosController.inicializarDatos(viajes, rutaActual.getOrigen(), rutaActual.getDestino(), viajeActual.getFechaSalida());

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onIniciarClick(ActionEvent event) {
        // Lógica para iniciar sesión
    }
}