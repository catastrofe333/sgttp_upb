package controlador;

import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion;
import vista.Aplicacion; // Usamos tu clase Aplicacion para obtener el Sistema

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Inicio {
    @FXML
    public Button viajes;
    @FXML
    public Button boletos;
    @FXML
    public Button iniciar_sesion;
    @FXML
    public ComboBox<Estacion> origen;
    @FXML
    public ComboBox<Estacion> destino;
    @FXML
    public DatePicker fecha_salida;
    @FXML
    public Button buscar;

    @FXML
    public void initialize() {
        origen.getItems().setAll(Estacion.values());
        destino.getItems().setAll(Estacion.values());
        fecha_salida.setValue(LocalDate.now()); // Pone la fecha de hoy por defecto
    }

    @FXML
    public void onViajesClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onBoletosClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onIniciarClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onBuscarClick(ActionEvent actionEvent) throws IOException {

        // ... (TODA TU LÓGICA DE BÚSQUEDA VA AQUÍ) ...
        Estacion estacionOrigen = origen.getValue();
        Estacion estacionDestino = destino.getValue();
        Date fecha = Date.from(fecha_salida.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Ruta[] rutasCoincidentes = Aplicacion.getSistema().getGestorRuta().buscarRutasPorOrigenDestino(estacionOrigen, estacionDestino);
        Viaje[] viajesEncontrados = Aplicacion.getSistema().getGestorViajes().buscarViajesPorRuta(rutasCoincidentes, fecha);

        // ... (TODA TU LÓGICA DE CARGAR EL FXML VA AQUÍ) ...
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/resultadosBusquedaRutas.fxml"));
        Parent root = fxmlLoader.load(); // Carga el nuevo VBox raíz
        Resultados controllerResultados = fxmlLoader.getController();
        controllerResultados.inicializarDatos(viajesEncontrados, estacionOrigen, estacionDestino, fecha);

        // --- ⬇️ AQUÍ ESTÁ LA SOLUCIÓN ⬇️ ---

        // 1. Obtiene la escena ACTUAL
        Scene scene = ((Node) actionEvent.getSource()).getScene();

        // 2. En lugar de cambiar la escena, solo cambia el contenido raíz
        scene.setRoot(root);

        // Ya NO necesitas stage.setScene() ni stage.setFullScreen()
    }
}