package controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import modelo.entidades.Administrador;
import modelo.entidades.Ruta;
import modelo.entidades.Tren;
import modelo.enums.Estacion;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdminRutasAgregarController {

    @FXML private ComboBox<String> comboBoxIdTren;
    @FXML private ComboBox<Estacion> comboBoxOrigen;
    @FXML private ComboBox<Estacion> comboBoxDestino;
    @FXML private Button btnAgregar;
    @FXML private Label labelMensaje;

    // --- Nuevos elementos FXML ---
    @FXML private ComboBox<Estacion> comboBoxEstacionIntermedia;
    @FXML private Button btnAddEstacion;
    @FXML private ListView<Estacion> listViewEstacionesOrdenadas;
    @FXML private Button btnSubir;
    @FXML private Button btnBajar;
    @FXML private Button btnEliminarEstacion;

    private final Sistema sistema = Aplicacion.getSistema();

    // Lista de todas las estaciones para filtrar
    private final List<Estacion> todasLasEstaciones = Arrays.asList(Estacion.values());

    @FXML
    public void initialize() {
        ocultarMensaje();

        // Cargar ComboBox de Trenes
        comboBoxIdTren.getItems().clear();
        Tren[] trenes = sistema.getGestorTrenes().getTrenes();
        if (trenes != null) {
            for (Tren tren : trenes) {
                if (tren != null) {
                    comboBoxIdTren.getItems().add(tren.getIdTren());
                }
            }
        }

        // Cargar ComboBox de Estaciones Principales
        comboBoxOrigen.getItems().setAll(Estacion.values());
        comboBoxDestino.getItems().setAll(Estacion.values());

        // Inicializar la lista de paradas ordenadas
        listViewEstacionesOrdenadas.setItems(FXCollections.observableArrayList());

        // Cargar el ComboBox de paradas intermedias
        actualizarParadasDisponibles();
    }

    /**
     * Se llama cuando Origen o Destino cambian.
     * Actualiza la lista de paradas intermedias disponibles.
     */
    @FXML
    void onDatosPrincipalesCambiados(ActionEvent event) {
        actualizarParadasDisponibles();

        // Validar la lista existente por si acaso
        Estacion origen = comboBoxOrigen.getValue();
        Estacion destino = comboBoxDestino.getValue();

        if (origen != null) {
            listViewEstacionesOrdenadas.getItems().remove(origen);
        }
        if (destino != null) {
            listViewEstacionesOrdenadas.getItems().remove(destino);
        }
    }

    /**
     * Filtra el ComboBox de paradas intermedias para no mostrar
     * ni el origen, ni el destino, ni las paradas ya añadidas.
     */
    private void actualizarParadasDisponibles() {
        Estacion origen = comboBoxOrigen.getValue();
        Estacion destino = comboBoxDestino.getValue();
        ObservableList<Estacion> paradasActuales = listViewEstacionesOrdenadas.getItems();

        // Filtra la lista completa de estaciones
        List<Estacion> disponibles = todasLasEstaciones.stream()
                .filter(e -> e != origen)       // No es origen
                .filter(e -> e != destino)      // No es destino
                .filter(e -> !paradasActuales.contains(e)) // No está ya en la lista
                .collect(Collectors.toList());

        comboBoxEstacionIntermedia.setItems(FXCollections.observableArrayList(disponibles));
    }

    /**
     * Añade la estación seleccionada a la lista ordenada.
     */
    @FXML
    void onAddEstacionClick(ActionEvent event) {
        Estacion paradaSeleccionada = comboBoxEstacionIntermedia.getValue();
        if (paradaSeleccionada != null) {
            listViewEstacionesOrdenadas.getItems().add(paradaSeleccionada);
            // Refrescar el combobox para que ya no muestre la que acabamos de añadir
            actualizarParadasDisponibles();
            comboBoxEstacionIntermedia.setValue(null);
        }
    }

    /**
     * Elimina la estación seleccionada de la lista ordenada.
     */
    @FXML
    void onEliminarEstacionClick(ActionEvent event) {
        Estacion paradaSeleccionada = listViewEstacionesOrdenadas.getSelectionModel().getSelectedItem();
        if (paradaSeleccionada != null) {
            listViewEstacionesOrdenadas.getItems().remove(paradaSeleccionada);
            // Refrescar el combobox para que vuelva a estar disponible
            actualizarParadasDisponibles();
        }
    }

    /**
     * Sube la estación seleccionada un puesto en la lista.
     */
    @FXML
    void onSubirClick(ActionEvent event) {
        int index = listViewEstacionesOrdenadas.getSelectionModel().getSelectedIndex();
        if (index > 0) { // Si no es el primero
            // Intercambiar con el anterior
            Estacion item = listViewEstacionesOrdenadas.getItems().remove(index);
            listViewEstacionesOrdenadas.getItems().add(index - 1, item);
            listViewEstacionesOrdenadas.getSelectionModel().select(index - 1); // Mantener selección
        }
    }

    /**
     * Baja la estación seleccionada un puesto en la lista.
     */
    @FXML
    void onBajarClick(ActionEvent event) {
        int index = listViewEstacionesOrdenadas.getSelectionModel().getSelectedIndex();
        ObservableList<Estacion> items = listViewEstacionesOrdenadas.getItems();

        if (index != -1 && index < items.size() - 1) { // Si no es el último
            // Intercambiar con el siguiente
            Estacion item = items.remove(index);
            items.add(index + 1, item);
            listViewEstacionesOrdenadas.getSelectionModel().select(index + 1); // Mantener selección
        }
    }

    /**
     * Lógica principal para crear la Ruta.
     */
    @FXML
    void onAgregarClick(ActionEvent event) {
        String idTren = comboBoxIdTren.getValue();
        Estacion origen = comboBoxOrigen.getValue();
        Estacion destino = comboBoxDestino.getValue();
        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idTren == null || origen == null || destino == null) {
            mostrarError("Por favor, complete ID Tren, Origen y Destino.");
            return;
        }

        if (origen == destino) {
            mostrarError("El Origen y el Destino no pueden ser iguales.");
            return;
        }

        // --- LÓGICA DE ORDEN MODIFICADA ---
        // Recolectar estaciones intermedias en el orden de la lista
        List<Estacion> intermedias = new ArrayList<>(listViewEstacionesOrdenadas.getItems());

        Estacion[] estacionesVisitar = intermedias.isEmpty() ? null : intermedias.toArray(new Estacion[0]);
        // --- FIN DE LÓGICA MODIFICADA ---

        // Generar el camino (Esta función ya acepta el array ordenado)
        Estacion[] camino = sistema.getGestorRuta().generarCamino(origen, destino, estacionesVisitar);

        if (camino == null) {
            mostrarError("No se pudo generar una ruta válida. Verifique las estaciones o la conectividad.");
            return;
        }

        // Crear y guardar la ruta
        try {
            String nuevoIdRuta = sistema.getGestorIds().generarIdRuta();
            Ruta nuevaRuta = new Ruta(nuevoIdRuta, idTren, origen, destino, camino);

            sistema.getGestorRuta().agregarRuta(nuevaRuta, admin);

            mostrarExito("Ruta creada con éxito. ID: " + nuevoIdRuta + ". Kilómetros: " + nuevaRuta.getKilometros());

            // Limpiar campos
            limpiarFormulario();

        } catch (Exception e) {
            mostrarError("Error al guardar la ruta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarFormulario() {
        comboBoxIdTren.setValue(null);
        comboBoxOrigen.setValue(null);
        comboBoxDestino.setValue(null);
        listViewEstacionesOrdenadas.getItems().clear();
        actualizarParadasDisponibles();
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) {
        labelMensaje.setText("✅ " + mensaje);
        labelMensaje.getStyleClass().setAll("successLabel");
    }

    private void mostrarError(String mensaje) {
        labelMensaje.setText("❌ " + mensaje);
        labelMensaje.getStyleClass().setAll("errorLabel");
    }

    private void ocultarMensaje() {
        labelMensaje.setText("");
        labelMensaje.getStyleClass().clear();
    }
}