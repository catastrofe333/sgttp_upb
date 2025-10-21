package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;

import java.text.SimpleDateFormat;

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

    private SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    private Viaje viajeActual;

    /**
     * Rellena los labels de la tarjeta con la info del viaje y la ruta.
     * Es llamado por el controlador 'Resultados'.
     */
    public void setDatos(Viaje viaje, Ruta ruta) {
        this.viajeActual = viaje;

        labelHoraSalida.setText(sdfHora.format(viaje.getFechaSalida()));
        labelHoraLlegada.setText(sdfHora.format(viaje.getFechaLlegada()));

        // Usamos la Ruta para obtener los nombres de las estaciones
        labelOrigen.setText(ruta.getOrigen().toString());
        labelDestino.setText(ruta.getDestino().toString());
    }

    @FXML
    void onVerClick(ActionEvent event) {
        System.out.println("Botón VER presionado para el viaje: " + viajeActual.getIdViaje());
        // Aquí cargarías la siguiente pantalla (selección de asiento)
    }
}