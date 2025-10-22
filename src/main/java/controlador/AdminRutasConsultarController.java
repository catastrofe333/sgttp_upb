package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelo.entidades.Ruta;
import modelo.enums.Estacion;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.text.NumberFormat;
import java.util.Locale;

public class AdminRutasConsultarController {

    @FXML private ComboBox<String> comboBoxIdRuta;
    @FXML private Label labelMensaje;
    @FXML private VBox vboxResultado;
    @FXML private Label labelTituloRuta;
    @FXML private Label labelIdTren;
    @FXML private Label labelOrigen;
    @FXML private Label labelDestino;
    @FXML private Label labelKms;
    @FXML private Label labelValorBase;
    @FXML private Button btnCamino;

    private final Sistema sistema = Aplicacion.getSistema();
    private Ruta rutaActual; // Guardar la ruta consultada

    // Formateador de moneda
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    @FXML
    public void initialize() {
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);
        ocultarMensaje();

        // Cargar ComboBox de Rutas
        cargarRutasDisponibles();
    }

    private void cargarRutasDisponibles() {
        comboBoxIdRuta.getItems().clear();
        Ruta[] rutas = sistema.getGestorRuta().getRutas();
        if (rutas != null) {
            for (Ruta ruta : rutas) {
                if (ruta != null) {
                    comboBoxIdRuta.getItems().add(ruta.getIdRuta());
                }
            }
        }
    }

    /**
     * Se llama cuando el usuario selecciona una ruta del ComboBox.
     */
    @FXML
    void onRutaSeleccionada(ActionEvent event) {
        String idRuta = comboBoxIdRuta.getValue();
        ocultarMensaje();
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);

        if (idRuta == null) {
            return;
        }

        rutaActual = sistema.getGestorRuta().buscarRutaPorId(idRuta); //

        if (rutaActual != null) {
            mostrarDetallesRuta(rutaActual);
        } else {
            mostrarError("Ruta con ID " + idRuta + " no encontrada.");
        }
    }

    private void mostrarDetallesRuta(Ruta ruta) {
        labelTituloRuta.setText("RUTA " + ruta.getIdRuta());
        labelIdTren.setText(ruta.getIdTren());
        labelOrigen.setText(ruta.getOrigen().toString());
        labelDestino.setText(ruta.getDestino().toString());
        labelKms.setText(String.format("%d km", ruta.getKilometros()));
        labelValorBase.setText(currencyFormatter.format(ruta.getValorBase())); //

        vboxResultado.setVisible(true);
        vboxResultado.setManaged(true);
    }

    @FXML
    void onCaminoClick(ActionEvent event) {
        if (rutaActual == null) return;

        StringBuilder sb = new StringBuilder("Camino de la Ruta:\n");
        Estacion[] camino = rutaActual.getCamino(); //
        for (int i = 0; i < camino.length; i++) {
            sb.append(camino[i].toString());
            if (i < camino.length - 1) {
                sb.append(" -> ");
            }
        }
        mostrarInfo(sb.toString()); // Usamos el label de mensaje para mostrar el camino
    }

    // Métodos auxiliares
    private void mostrarInfo(String mensaje) {
        labelMensaje.setText(mensaje);
        labelMensaje.getStyleClass().setAll("infoLabel");
        labelMensaje.setVisible(true);
        labelMensaje.setManaged(true);
    }

    private void mostrarError(String mensaje) {
        labelMensaje.setText(mensaje);
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