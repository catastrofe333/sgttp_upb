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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.entidades.Ruta;
import modelo.enums.Estacion;
import vista.Aplicacion;

import java.io.IOException;
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
        Estacion estacionOrigen = origen.getValue();
        Estacion estacionDestino = destino.getValue();

        Date fecha = Date.from(fecha_salida.getValue());

        if(estacionOrigen == null || estacionDestino == null) {
            return;
        }

        Ruta[] rutasCoincidentes = Aplicacion.getSistema().getGestorRuta().buscarRutasPorOrigenDestino(estacionOrigen, estacionDestino);


        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/resultadosBusquedaRutas.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
