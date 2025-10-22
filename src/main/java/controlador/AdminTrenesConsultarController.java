package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox; // Importar ComboBox
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import modelo.entidades.Tren;
import modelo.entidades.VagonCarga;
import modelo.entidades.VagonPasajeros;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class AdminTrenesConsultarController {

    @FXML private ComboBox<String> comboBoxIdTren; // CAMBIO: TextField -> ComboBox
    @FXML private Button btnConsultar;
    @FXML private Label labelMensaje;
    @FXML private VBox vboxResultado;
    @FXML private Label labelTituloTren;
    @FXML private Label labelTipo;
    @FXML private Label labelKilometraje;
    @FXML private Button btnVagonesPasajeros;
    @FXML private Button btnVagonesCarga;

    private final Sistema sistema = Aplicacion.getSistema();
    private Tren trenActual;

    @FXML
    public void initialize() {
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);
        ocultarMensaje();
        // NUEVO: Cargar los IDs de los trenes existentes
        cargarTrenesDisponibles();
    }

    @FXML
    void onConsultarClick(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue(); // CAMBIO: getText() -> getValue()
        ocultarMensaje();
        vboxResultado.setVisible(false);
        vboxResultado.setManaged(false);

        if (idTren == null || idTren.isEmpty()) { // CAMBIO: validación
            mostrarError("Por favor, seleccione el ID del tren a consultar.");
            return;
        }

        trenActual = sistema.getGestorTrenes().buscarTren(idTren);

        if (trenActual != null) {
            mostrarDetallesTren(trenActual);
        } else {
            mostrarError("Tren con ID " + idTren + " no encontrado.");
        }
    }

    // NUEVO: Método auxiliar para cargar trenes
    private void cargarTrenesDisponibles() {
        comboBoxIdTren.getItems().clear();
        Tren[] trenes = sistema.getGestorTrenes().getTrenes();
        if (trenes != null) {
            for (Tren tren : trenes) {
                if (tren != null) {
                    comboBoxIdTren.getItems().add(tren.getIdTren());
                }
            }
        }
    }

    // --- Métodos mostrarDetallesTren, onVagones... y auxiliares (sin cambios) ---

    private void mostrarDetallesTren(Tren tren) {
        labelTituloTren.setText("TREN " + tren.getIdTren());
        labelTipo.setText(tren.getTipo().toString());
        labelKilometraje.setText(String.format("%.2f km", tren.getKilometraje()));

        btnVagonesPasajeros.setText(String.format("Vagones Pasajeros (%d / %d)",
                tren.getNumVagonesPasajeros(), tren.getTipo().getCapacidadMaxVagonesPasajeros()));

        btnVagonesCarga.setText(String.format("Vagones Carga (%d / %d)",
                tren.getNumVagonesCarga(), tren.getTipo().getCapacidadMaxVagonesCarga()));

        vboxResultado.setVisible(true);
        vboxResultado.setManaged(true);
    }

    @FXML
    void onVagonesPasajerosClick(ActionEvent event) {
        StringBuilder sb = new StringBuilder("Vagones de Pasajeros:\n");
        for (int i = 0; i < trenActual.getNumVagonesPasajeros(); i++) {
            VagonPasajeros vagon = trenActual.getVagonesPasajeros()[i];
            if (vagon != null) {
                sb.append("  - ID: ").append(vagon.getIdVagon()).append("\n");
            }
        }
        mostrarExito(sb.toString());
    }

    @FXML
    void onVagonesCargaClick(ActionEvent event) {
        StringBuilder sb = new StringBuilder("Vagones de Carga:\n");
        for (int i = 0; i < trenActual.getNumVagonesCarga(); i++) {
            VagonCarga vagon = trenActual.getVagonesCarga()[i];
            if (vagon != null) {
                sb.append("  - ID: ").append(vagon.getIdVagon()).append("\n");
            }
        }
        mostrarExito(sb.toString());
    }

    private void mostrarExito(String mensaje) {
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