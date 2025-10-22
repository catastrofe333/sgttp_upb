package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; // Importar TextField
import modelo.entidades.*;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.time.LocalDate;
import java.time.LocalDateTime; // Importar
import java.time.LocalTime; // Importar
import java.time.ZoneId;
import java.time.format.DateTimeParseException; // Importar
import java.util.Date;

public class AdminViajesAgregarController {

    @FXML private DatePicker datePickerSalida;
    @FXML private DatePicker datePickerLlegada;
    @FXML private ComboBox<String> comboBoxIdRuta;
    @FXML private Label labelMensaje;

    // --- NUEVOS FXML ---
    @FXML private TextField fieldSalidaHora;
    @FXML private TextField fieldLlegadaHora;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        datePickerSalida.setValue(LocalDate.now());
        fieldSalidaHora.setText("12:00"); // Hora por defecto

        datePickerLlegada.setValue(LocalDate.now());
        fieldLlegadaHora.setText("18:00"); // Hora por defecto

        // Cargar rutas disponibles
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

    @FXML
    void onCrearClick(ActionEvent event) {
        String idRuta = comboBoxIdRuta.getValue();
        LocalDate localDateSalida = datePickerSalida.getValue();
        LocalDate localDateLlegada = datePickerLlegada.getValue();
        String strHoraSalida = fieldSalidaHora.getText();
        String strHoraLlegada = fieldLlegadaHora.getText();

        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idRuta == null || localDateSalida == null || localDateLlegada == null ||
                strHoraSalida.isEmpty() || strHoraLlegada.isEmpty()) {
            mostrarError("Por favor, complete todos los campos (Fecha y Hora).");
            return;
        }

        try {
            // ----- LÓGICA DE HORA MODIFICADA -----
            LocalTime localTimeSalida = LocalTime.parse(strHoraSalida); // Parsea "HH:mm"
            LocalTime localTimeLlegada = LocalTime.parse(strHoraLlegada);

            LocalDateTime ldtSalida = LocalDateTime.of(localDateSalida, localTimeSalida);
            LocalDateTime ldtLlegada = LocalDateTime.of(localDateLlegada, localTimeLlegada);

            Date fechaSalida = Date.from(ldtSalida.atZone(ZoneId.systemDefault()).toInstant());
            Date fechaLlegada = Date.from(ldtLlegada.atZone(ZoneId.systemDefault()).toInstant());
            // ----- FIN LÓGICA DE HORA -----

            if (fechaLlegada.before(fechaSalida)) {
                mostrarError("La fecha/hora de llegada no puede ser anterior a la de salida.");
                return;
            }

            // --- Lógica para calcular asientos (sin cambios) ---
            Ruta ruta = sistema.getGestorRuta().buscarRutaPorId(idRuta);
            if (ruta == null) {
                mostrarError("Error: Ruta seleccionada no encontrada.");
                return;
            }
            Tren tren = sistema.getGestorTrenes().buscarTren(ruta.getIdTren());
            if (tren == null) {
                mostrarError("Error: El tren " + ruta.getIdTren() + " asignado a la ruta no existe.");
                return;
            }
            VagonPasajeros vagonTemporal = new VagonPasajeros("temp");
            int capPremium = vagonTemporal.getCapacidadPremium();
            int capEjecutiva = vagonTemporal.getCapacidadEjecutiva();
            int capEstandar = vagonTemporal.getCapacidadEstandar();
            int numVagonesPasajeros = tren.getNumVagonesPasajeros();
            if (numVagonesPasajeros == 0) {
                mostrarError("Error: El tren " + tren.getIdTren() + " no tiene vagones de pasajeros.");
                return;
            }
            int totalPremium = numVagonesPasajeros * capPremium;
            int totalEjecutiva = numVagonesPasajeros * capEjecutiva;
            int totalEstandar = numVagonesPasajeros * capEstandar;
            // --- Fin lógica de asientos ---

            String nuevoIdViaje = sistema.getGestorIds().generarIdViaje();
            Viaje nuevoViaje = new Viaje(nuevoIdViaje, fechaSalida, fechaLlegada, idRuta,
                    totalPremium, totalEjecutiva, totalEstandar);

            nuevoViaje.setVisible(false);

            sistema.getGestorViajes().agregarViaje(nuevoViaje, admin);
            mostrarExito("Viaje creado con éxito. ID: " + nuevoIdViaje + ". (Está Oculto por defecto)");

            // Limpiar
            comboBoxIdRuta.setValue(null);
            datePickerSalida.setValue(LocalDate.now());
            datePickerLlegada.setValue(LocalDate.now());
            fieldSalidaHora.setText("12:00");
            fieldLlegadaHora.setText("18:00");

        } catch (DateTimeParseException e) {
            mostrarError("Formato de hora incorrecto. Use HH:mm (ej: 08:30 o 14:00).");
        } catch (Exception e) {
            mostrarError("Error al guardar el viaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) { labelMensaje.setText("✅ " + mensaje); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String mensaje) { labelMensaje.setText("❌ " + mensaje); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}