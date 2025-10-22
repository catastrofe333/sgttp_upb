package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality; // Asegúrate de importar esto
import javafx.stage.Stage;     // Asegúrate de importar esto
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AdminViajesConsultarController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private Label labelMensaje;
    @FXML private VBox vboxResultado;
    @FXML private Label labelTituloViaje;
    @FXML private Label labelVisibilidad;
    @FXML private Label labelIdRuta;
    @FXML private Label labelOrigen;
    @FXML private Label labelDestino;
    @FXML private Label labelIdTren;
    @FXML private Label labelFechas;
    @FXML private Label labelEstado;
    @FXML private Label labelValorBase;
    @FXML private Button btnCamino;

    private final Sistema sistema = Aplicacion.getSistema();
    private Ruta rutaActual; // Guardar la ruta del viaje consultado

    // Formateadores
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);
        ocultarMensaje();
        cargarViajesDisponibles();
    }

    private void cargarViajesDisponibles() {
        comboBoxIdViaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes();
        if (viajes != null) {
            for (Viaje viaje : viajes) {
                if (viaje != null) {
                    comboBoxIdViaje.getItems().add(viaje.getIdViaje());
                }
            }
        }
    }

    @FXML
    void onViajeSeleccionado(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        ocultarMensaje();
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);
        this.rutaActual = null;

        if (idViaje == null) return;

        Viaje viaje = sistema.getGestorViajes().buscarViaje(idViaje);
        if (viaje == null) {
            mostrarError("Viaje no encontrado.");
            return;
        }

        Ruta ruta = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta());
        if (ruta == null) {
            mostrarError("Error: La Ruta " + viaje.getIdRuta() + " asociada a este viaje no existe.");
            return;
        }

        this.rutaActual = ruta; // Guardar para el botón "Camino"
        mostrarDetallesViaje(viaje, ruta);
    }

    private void mostrarDetallesViaje(Viaje viaje, Ruta ruta) {
        labelTituloViaje.setText("VIAJE " + viaje.getIdViaje());
        labelVisibilidad.setText(viaje.isVisible() ? "VISIBLE" : "OCULTO");
        labelIdRuta.setText(viaje.getIdRuta());
        labelOrigen.setText(ruta.getOrigen().toString());
        labelDestino.setText(ruta.getDestino().toString());
        labelIdTren.setText(ruta.getIdTren());
        labelFechas.setText(sdf.format(viaje.getFechaSalida()) + " - " + sdf.format(viaje.getFechaLlegada()));
        labelEstado.setText(viaje.getEstado().toString());
        labelValorBase.setText(currencyFormatter.format(ruta.getValorBase()));

        vboxResultado.setVisible(true);
        vboxResultado.setManaged(true);
    }

    @FXML
    void onCaminoClick(ActionEvent event) {
        if (rutaActual == null) {
            mostrarError("No hay ruta seleccionada para mostrar el camino.");
            return;
        }

        // Construir el string del camino
        StringBuilder sb = new StringBuilder();
        Estacion[] camino = rutaActual.getCamino();
        for (int i = 0; i < camino.length; i++) {
            sb.append(i + 1).append(". ").append(camino[i].toString());
            if (i < camino.length - 1) {
                sb.append("\n -> ");
            }
        }

        // Crear el Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Camino de la Ruta");
        alert.setHeaderText("Detalle del camino para: " + rutaActual.getIdRuta());

        // Usar un TextArea para que sea scrollable y se vea bien
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);

        // --- LÍNEAS CLAVE PARA EVITAR MINIMIZAR ---
        // 1. Obtener el Stage (ventana) actual desde el botón
        Stage stage = (Stage) btnCamino.getScene().getWindow();

        // 2. Asignar el "dueño" (owner) de la alerta
        alert.initOwner(stage);

        // 3. (Opcional pero recomendado) Hacerlo modal a la ventana
        alert.initModality(Modality.WINDOW_MODAL);
        // ------------------------------------------

        alert.showAndWait();
    }

    // Métodos auxiliares
    private void mostrarInfo(String mensaje) {
        labelMensaje.setText(mensaje);
        labelMensaje.getStyleClass().setAll("infoLabel");
        labelMensaje.setVisible(true);
        labelMensaje.setManaged(true);
    }
    private void mostrarError(String mensaje) {
        labelMensaje.setText("❌ " + mensaje);
        labelMensaje.getStyleClass().setAll("errorLabel");
        labelMensaje.setVisible(true);
        labelMensaje.setManaged(true);
    }
    private void ocultarMensaje() {
        labelMensaje.setText("");
        labelMensaje.setVisible(false);
        labelMensaje.setManaged(false);
    }
}