package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import modelo.entidades.Boleto;
import modelo.entidades.Equipaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class EmpleadoEquipajesConsultarController {

    @FXML private ComboBox<String> comboBoxIdBoleto;
    @FXML private Label labelMensaje;
    @FXML private ScrollPane scrollPaneResultados;
    @FXML private FlowPane flowPaneEquipajes;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        scrollPaneResultados.setVisible(false);
        scrollPaneResultados.setManaged(false);
        cargarBoletosConEquipaje();
    }

    private void cargarBoletosConEquipaje() {
        comboBoxIdBoleto.getItems().clear();
        Boleto[] boletos = sistema.getGestorBoletos().getBoletos(); //
        if (boletos != null) {
            for (Boleto b : boletos) {
                if (b != null && b.getNumEquipajes() > 0) { // Solo mostrar si tiene equipaje
                    comboBoxIdBoleto.getItems().add(b.getIdBoleto()); //
                }
            }
        }
    }

    @FXML
    void onBoletoSeleccionado(ActionEvent event) {
        String idBoleto = comboBoxIdBoleto.getValue();
        ocultarMensaje();
        flowPaneEquipajes.getChildren().clear(); // Limpiar resultados anteriores
        scrollPaneResultados.setVisible(false);
        scrollPaneResultados.setManaged(false);

        if (idBoleto == null) return;

        Equipaje[] equipajes = sistema.getGestorBoletos().buscarEquipajeBoleto(idBoleto); //

        if (equipajes != null) {
            boolean encontrado = false;
            for (Equipaje eq : equipajes) {
                if (eq != null) {
                    encontrado = true;
                    flowPaneEquipajes.getChildren().add(crearTarjetaEquipaje(eq));
                }
            }

            if (encontrado) {
                scrollPaneResultados.setVisible(true);
                scrollPaneResultados.setManaged(true);
            } else {
                mostrarError("El boleto " + idBoleto + " no tiene equipajes registrados.");
                // Asegurarse de que el combo no esté vacío si el boleto existe pero sin equipaje
                if(sistema.getGestorBoletos().buscarBoletoPorIdBoleto(idBoleto) != null && !comboBoxIdBoleto.getItems().contains(idBoleto)){
                    comboBoxIdBoleto.getItems().add(idBoleto); // Re-añadir si existe pero no tenía equipaje antes
                }
            }
        } else {
            mostrarError("Boleto " + idBoleto + " no encontrado.");
            // Quitarlo del combo si ya no existe
            comboBoxIdBoleto.getItems().remove(idBoleto);
            comboBoxIdBoleto.setValue(null);
        }
    }

    // Método para crear dinámicamente la tarjeta de un equipaje
    private VBox crearTarjetaEquipaje(Equipaje equipaje) {
        VBox card = new VBox(5);
        card.getStyleClass().add("loginCard"); // Reutilizar estilo
        card.setPadding(new Insets(15));
        card.setPrefWidth(250); // Ancho fijo para cada tarjeta

        Label idLabel = new Label("ID Equipaje: " + equipaje.getIdEquipaje()); //
        idLabel.setStyle("-fx-font-weight: bold;");
        Label pesoLabel = new Label("Peso: " + String.format("%.2f Kg", equipaje.getPeso())); //
        Label estadoLabel = new Label("Estado: " + equipaje.getEstado()); //
        Label vagonLabel = new Label("Vagón Carga: " + equipaje.getIdVagonCarga()); //

        card.getChildren().addAll(idLabel, pesoLabel, estadoLabel, vagonLabel);
        return card;
    }

    // Métodos auxiliares
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); labelMensaje.setVisible(true); labelMensaje.setManaged(true);}
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.setVisible(false); labelMensaje.setManaged(false); }
}