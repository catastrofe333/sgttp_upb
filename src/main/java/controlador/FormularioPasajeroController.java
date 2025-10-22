package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.*;
import modelo.enums.CategoriaBoleto;
import modelo.enums.TipoId;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional; // Para el Alert de confirmación

public class FormularioPasajeroController {

    // --- FXML Fields ---
    @FXML private Label labelTituloFormulario;
    @FXML private ComboBox<TipoId> comboBoxTipoId;
    @FXML private TextField fieldNumeroId;
    @FXML private TextField fieldNombres;
    @FXML private TextField fieldApellidos;
    @FXML private TextField fieldDireccion;
    @FXML private TextField fieldTelefono;
    @FXML private TextField fieldContactoNombres;
    @FXML private TextField fieldContactoApellidos;
    @FXML private TextField fieldContactoTelefono;
    @FXML private Label labelMensaje;

    // --- Variables de estado ---
    private Sistema sistema = Aplicacion.getSistema();
    private Viaje viajeSeleccionado;
    private Ruta rutaDelViaje;
    private CategoriaBoleto categoriaSeleccionada;
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * Llamado desde DetalleViajeController para pasar la información necesaria.
     */
    public void inicializarDatos(Viaje viaje, CategoriaBoleto categoria) {
        this.viajeSeleccionado = viaje;
        this.categoriaSeleccionada = categoria;
        this.rutaDelViaje = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta()); //

        if (rutaDelViaje == null) {
            mostrarError("Error crítico: No se encontró la ruta del viaje.");
            // Podrías deshabilitar el formulario aquí
            return;
        }

        // Configurar título y ComboBox de Tipo ID
        labelTituloFormulario.setText(categoria.toString() + " - " + viaje.getIdViaje());
        comboBoxTipoId.getItems().setAll(TipoId.values()); //
        ocultarMensaje();
    }

    @FXML
    void onComprarClick(ActionEvent event) {
        ocultarMensaje(); // Limpiar mensajes anteriores

        // --- 1. Validar Campos ---
        TipoId tipoId = comboBoxTipoId.getValue();
        String numeroId = fieldNumeroId.getText().trim();
        String nombres = fieldNombres.getText().trim();
        String apellidos = fieldApellidos.getText().trim();
        String direccion = fieldDireccion.getText().trim();
        String telefono = fieldTelefono.getText().trim();
        String contactoNombres = fieldContactoNombres.getText().trim();
        String contactoApellidos = fieldContactoApellidos.getText().trim();
        String contactoTelefono = fieldContactoTelefono.getText().trim();

        if (tipoId == null || numeroId.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() ||
                direccion.isEmpty() || telefono.isEmpty() || contactoNombres.isEmpty() ||
                contactoApellidos.isEmpty() || contactoTelefono.isEmpty()) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        // --- 2. Mostrar Pop-up de Confirmación (Basado en image_45b301.png) ---
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmar Compra");
        confirmationAlert.setHeaderText("Confirma tu compra");
        // Construir el mensaje de confirmación
        String mensaje = "Destino: " + rutaDelViaje.getDestino() + " - Origen: " + rutaDelViaje.getOrigen() + "\n" + //
                "Fecha Salida: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(viajeSeleccionado.getFechaSalida()) + "\n" + //
                "Categoría: " + categoriaSeleccionada + "\n" +
                "Valor: " + currencyFormatter.format(rutaDelViaje.getValorBase() * categoriaSeleccionada.getIncrementoPrecio()); //

        confirmationAlert.setContentText(mensaje);

        // Cambiar texto de botones (Opcional)
        ButtonType buttonTypeConfirmar = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(buttonTypeConfirmar, buttonTypeCancelar);

        // Anclar a la ventana actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        confirmationAlert.initOwner(stage);
        confirmationAlert.initModality(Modality.WINDOW_MODAL);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // --- 3. Si el usuario confirma, proceder con la compra ---
        if (result.isPresent() && result.get() == buttonTypeConfirmar) {
            procesarCompra(tipoId, numeroId, nombres, apellidos, direccion, telefono,
                    contactoNombres, contactoApellidos, contactoTelefono, event);
        } else {
            System.out.println("Compra cancelada por el usuario.");
        }
    }

    private void procesarCompra(TipoId tipoId, String numeroId, String nombres, String apellidos,
                                String direccion, String telefono, String contactoNombres,
                                String contactoApellidos, String contactoTelefono, ActionEvent event) {

        // --- 3a. Asignar Asiento ---
        int asientoAsignado = sistema.getGestorTrenes().asignarAsiento(rutaDelViaje.getIdTren(), categoriaSeleccionada); //

        if (asientoAsignado == -1) {
            mostrarError("No hay asientos disponibles en la categoría " + categoriaSeleccionada + ". Intente otra categoría o viaje.");
            // Podrías ofrecer volver a la pantalla anterior
            return;
        }

        // --- 3b. Descontar Asiento del Viaje ---
        boolean descontado = sistema.getGestorViajes().descontarAsiento(viajeSeleccionado.getIdViaje(), categoriaSeleccionada); //
        if (!descontado) {
            // Esto es un error grave si asignarAsiento funcionó pero descontar no.
            mostrarError("Error interno al actualizar disponibilidad. Contacte soporte.");
            // Idealmente, aquí se debería revertir la asignación del asiento en el tren, pero es complejo.
            return;
        }

        // --- 3c. Crear el Objeto Boleto ---
        String nuevoIdBoleto = sistema.getGestorIds().generarIdBoleto(); //
        double valorFinal = rutaDelViaje.getValorBase() * categoriaSeleccionada.getIncrementoPrecio(); //

        Boleto nuevoBoleto = new Boleto(
                nuevoIdBoleto, categoriaSeleccionada, asientoAsignado, valorFinal, viajeSeleccionado.getIdViaje(), //
                tipoId, numeroId, nombres, apellidos, direccion, telefono,
                contactoNombres, contactoApellidos, contactoTelefono
        );

        // --- 3d. Guardar el Boleto ---
        sistema.getGestorBoletos().agregarBoleto(nuevoBoleto); //

        // --- 4. Mostrar Mensaje de Éxito y Navegar a Inicio ---
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Compra Exitosa");
        successAlert.setHeaderText("¡Tu boleto ha sido comprado con éxito!");
        successAlert.setContentText("ID del Boleto: " + nuevoIdBoleto + "\nAsiento: " + asientoAsignado);
        successAlert.initOwner((Stage) ((Node) event.getSource()).getScene().getWindow());
        successAlert.initModality(Modality.WINDOW_MODAL);
        successAlert.showAndWait();

        // Navegar a la pantalla de inicio
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/inicio.fxml")); //
            Parent root = loader.load();
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al volver a la pantalla de inicio.");
        }
    }

    @FXML
    void onBackClick(ActionEvent event) {
        // Volver a la pantalla de detalle del viaje
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/detalle_viaje.fxml")); //
            Parent root = loader.load();
            DetalleViajeController controller = loader.getController();

            // ¡IMPORTANTE! Re-inicializar la vista anterior con los datos guardados
            // Necesitamos los parámetros de búsqueda originales que DetalleViajeController guardó
            // TODO: Ajustar cómo se pasan estos datos si es necesario
            // Si DetalleViajeController no guarda origen/destino/fecha/directa, necesitarás pasarlos aquí
            controller.inicializarDatos(viajeSeleccionado, null, null, null, false); // Valores placeholder, ajustar si es necesario

            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(root); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Métodos Auxiliares ---
    private void mostrarError(String mensaje) {
        labelMensaje.setText("❌ " + mensaje);
        labelMensaje.setVisible(true);
        labelMensaje.setManaged(true);
    }

    private void ocultarMensaje() {
        labelMensaje.setVisible(false);
        labelMensaje.setManaged(false);
    }
}