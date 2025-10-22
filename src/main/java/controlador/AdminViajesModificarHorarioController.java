package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField; // Importar
import modelo.entidades.Administrador;
import modelo.entidades.Viaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.time.*; // Importar
import java.time.format.DateTimeParseException; // Importar
import java.util.Calendar; // Importar
import java.util.Date;

public class AdminViajesModificarHorarioController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private DatePicker datePickerSalida;
    @FXML private DatePicker datePickerLlegada;
    @FXML private Label labelMensaje;

    // --- NUEVOS FXML ---
    @FXML private TextField fieldSalidaHora;
    @FXML private TextField fieldLlegadaHora;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        // Deshabilitar campos hasta que se seleccione un viaje
        datePickerSalida.setDisable(true);
        datePickerLlegada.setDisable(true);
        fieldSalidaHora.setDisable(true);
        fieldLlegadaHora.setDisable(true);

        // Cargar Viajes PENDIENTES
        comboBoxIdViaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes();
        if (viajes != null) {
            for (Viaje viaje : viajes) {
                if (viaje != null && viaje.getEstado() == modelo.enums.EstadoTrayecto.PENDIENTE) {
                    comboBoxIdViaje.getItems().add(viaje.getIdViaje());
                }
            }
        }
    }

    @FXML
    void onViajeSeleccionado(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        if (idViaje == null) return;

        Viaje viaje = sistema.getGestorViajes().buscarViaje(idViaje);
        if (viaje != null) {
            // ----- LÓGICA DE HORA MODIFICADA -----
            // Convertir java.util.Date a LocalDate y LocalTime

            // Salida
            Instant instantSalida = Instant.ofEpochMilli(viaje.getFechaSalida().getTime());
            LocalDateTime ldtSalida = LocalDateTime.ofInstant(instantSalida, ZoneId.systemDefault());
            datePickerSalida.setValue(ldtSalida.toLocalDate());
            fieldSalidaHora.setText(String.format("%02d:%02d", ldtSalida.getHour(), ldtSalida.getMinute()));

            // Llegada
            Instant instantLlegada = Instant.ofEpochMilli(viaje.getFechaLlegada().getTime());
            LocalDateTime ldtLlegada = LocalDateTime.ofInstant(instantLlegada, ZoneId.systemDefault());
            datePickerLlegada.setValue(ldtLlegada.toLocalDate());
            fieldLlegadaHora.setText(String.format("%02d:%02d", ldtLlegada.getHour(), ldtLlegada.getMinute()));

            // Habilitar campos
            datePickerSalida.setDisable(false);
            datePickerLlegada.setDisable(false);
            fieldSalidaHora.setDisable(false);
            fieldLlegadaHora.setDisable(false);
            // ----- FIN LÓGICA DE HORA -----
        }
    }

    @FXML
    void onModificarClick(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        LocalDate localDateSalida = datePickerSalida.getValue();
        LocalDate localDateLlegada = datePickerLlegada.getValue();
        String strHoraSalida = fieldSalidaHora.getText();
        String strHoraLlegada = fieldLlegadaHora.getText();

        Administrador admin = (Administrador) sistema.getSesionActual();

        if (idViaje == null || localDateSalida == null || localDateLlegada == null ||
                strHoraSalida.isEmpty() || strHoraLlegada.isEmpty()) {
            mostrarError("Por favor, complete todos los campos (Fecha y Hora).");
            return;
        }

        try {
            // ----- LÓGICA DE HORA MODIFICADA -----
            LocalTime localTimeSalida = LocalTime.parse(strHoraSalida);
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

            boolean exito = sistema.getGestorViajes().modificarHorarios(idViaje, fechaSalida, fechaLlegada, admin);

            if (exito) {
                mostrarExito("Horario del viaje " + idViaje + " actualizado.");
            } else {
                mostrarError("Error: El viaje no se encontró o no está en estado 'PENDIENTE'.");
            }

        } catch (DateTimeParseException e) {
            mostrarError("Formato de hora incorrecto. Use HH:mm (ej: 08:30 o 14:00).");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String mensaje) { labelMensaje.setText("✅ " + mensaje); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String mensaje) { labelMensaje.setText("❌ " + mensaje); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}