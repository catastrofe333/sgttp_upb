package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.entidades.*;
import modelo.enums.EstadoEquipaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.text.SimpleDateFormat;

public class EmpleadoBoletosConsultarController {

    // --- Campos Comunes ---
    @FXML private Label labelMensaje;
    @FXML private ScrollPane scrollPaneResultados;
    @FXML private VBox vboxResultados;

    // --- Campos Específicos (Pueden ser null dependiendo del FXML cargado) ---
    @FXML private TextField fieldInputConsulta; // Para buscar por Pasajero
    @FXML private ComboBox<String> comboBoxInputConsulta; // Para buscar por ID Boleto

    private final Sistema sistema = Aplicacion.getSistema();
    private SimpleDateFormat sdfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private boolean buscarPorPasajero; // Flag para saber qué campo usar

    @FXML
    public void initialize() {
        ocultarMensaje();
        scrollPaneResultados.setVisible(false);
        scrollPaneResultados.setManaged(false);

        // Determinar modo de búsqueda y cargar ComboBox si es necesario
        if (comboBoxInputConsulta != null) {
            buscarPorPasajero = false;
            cargarBoletos();
        } else if (fieldInputConsulta != null) {
            buscarPorPasajero = true;
        } else {
            System.err.println("Error: No se encontró campo de entrada en EmpleadoBoletosConsultarController");
        }
    }

    private void cargarBoletos() {
        if(comboBoxInputConsulta == null) return;
        comboBoxInputConsulta.getItems().clear();
        Boleto[] boletos = sistema.getGestorBoletos().getBoletos(); //
        if (boletos != null) {
            for (Boleto b : boletos) {
                if (b != null) comboBoxInputConsulta.getItems().add(b.getIdBoleto()); //
            }
        }
    }

    @FXML
    void onBuscarClick(ActionEvent event) {
        ocultarMensaje();
        vboxResultados.getChildren().clear();
        scrollPaneResultados.setVisible(false);
        scrollPaneResultados.setManaged(false);

        String valorBusqueda;
        if (buscarPorPasajero) {
            valorBusqueda = fieldInputConsulta.getText().trim();
        } else {
            valorBusqueda = comboBoxInputConsulta.getValue();
        }

        if (valorBusqueda == null || valorBusqueda.isEmpty()) {
            mostrarError("Ingrese el ID a buscar.");
            return;
        }

        Boleto[] boletosEncontrados;
        if (buscarPorPasajero) {
            boletosEncontrados = sistema.getGestorBoletos().buscarBoletoPorIdPasajero(valorBusqueda); //
        } else {
            Boleto b = sistema.getGestorBoletos().buscarBoletoPorIdBoleto(valorBusqueda); //
            boletosEncontrados = (b != null) ? new Boleto[]{b} : new Boleto[0];
        }

        if (boletosEncontrados != null && boletosEncontrados.length > 0) {
            for (Boleto boleto : boletosEncontrados) {
                if (boleto != null) {
                    vboxResultados.getChildren().add(crearTarjetaBoleto(boleto));
                }
            }
            scrollPaneResultados.setVisible(true);
            scrollPaneResultados.setManaged(true);
        } else {
            mostrarError("No se encontraron boletos para el ID: " + valorBusqueda);
        }
    }

    private VBox crearTarjetaBoleto(Boleto boleto) {
        VBox card = new VBox(10);
        card.getStyleClass().add("loginCard");
        card.setPadding(new Insets(15));
        card.setPrefWidth(550); // Ancho mayor para más info

        Label titulo = new Label("BOLETO " + boleto.getIdBoleto()); //
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // --- Info Principal ---
        VBox infoBox = new VBox(5);
        Viaje viaje = sistema.getGestorViajes().buscarViaje(boleto.getIdViaje()); //
        Ruta ruta = (viaje != null) ? sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta()) : null; //
        String horario = (viaje != null) ? sdfCompleto.format(viaje.getFechaSalida()) + " - " + sdfCompleto.format(viaje.getFechaLlegada()) : "N/A";
        String idTren = (ruta != null) ? ruta.getIdTren() : "N/A"; //

        infoBox.getChildren().addAll(
                crearInfoLabel("Fecha Compra:", sdfCompleto.format(boleto.getFechaHoraCompra())), //
                crearInfoLabel("Horario Viaje:", horario),
                crearInfoLabel("Tipo ID Pasajero:", boleto.getTipoIdPasajero().toString()), //
                crearInfoLabel("ID Pasajero:", boleto.getIdPasajero()), //
                crearInfoLabel("Nombres:", boleto.getNombrePasajero()), //
                crearInfoLabel("Apellidos:", boleto.getApellidoPasajero()), //
                crearInfoLabel("Dirección:", boleto.getDireccionPasajero()), //
                crearInfoLabel("Teléfono:", boleto.getTelefonoPasajero()), //
                crearInfoLabel("ID Tren:", idTren),
                crearInfoLabel("Asiento:", String.valueOf(boleto.getAsiento())), //
                crearInfoLabel("Categoría:", boleto.getCategoria().toString()), //
                crearInfoLabel("Valor:", String.format("$%,.0f", boleto.getValorBoleto())) //
        );

        // --- Botones ---
        HBox botonesBox = new HBox(10);
        Button btnContacto = new Button("PERSONA DE CONTACTO");
        btnContacto.getStyleClass().add("adminActionButton");
        btnContacto.setOnAction(e -> mostrarPopupContacto(boleto, btnContacto)); // Pasar el botón para anclar popup

        Button btnEquipajes = new Button("EQUIPAJES (" + boleto.getNumEquipajes() + ")"); //
        btnEquipajes.getStyleClass().add("adminActionButton");
        btnEquipajes.setOnAction(e -> mostrarPopupEquipajes(boleto, btnEquipajes));

        botonesBox.getChildren().addAll(btnContacto, btnEquipajes);

        card.getChildren().addAll(titulo, new Separator(), infoBox, botonesBox);
        return card;
    }

    private Label crearInfoLabel(String titulo, String valor){
        Label label = new Label(titulo + " " + (valor != null ? valor : "N/A"));
        label.setWrapText(true);
        return label;
    }

    private void mostrarPopupContacto(Boleto boleto, Node ownerNode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Persona de Contacto");
        alert.setHeaderText("Contacto de emergencia para Boleto " + boleto.getIdBoleto()); //

        VBox content = new VBox(5);
        content.getChildren().addAll(
                new Label("Nombres: " + boleto.getNombreContactoPasajero()), //
                new Label("Apellidos: " + boleto.getApellidoContactoPasajero()), //
                new Label("Teléfono: " + boleto.getTelefonoContactoPasajero()) //
        );
        alert.getDialogPane().setContent(content);

        // Anclar a la ventana principal
        Stage stage = (Stage) ownerNode.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }

    private void mostrarPopupEquipajes(Boleto boleto, Node ownerNode) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Equipajes");
        alert.setHeaderText("Equipajes registrados para Boleto " + boleto.getIdBoleto()); //

        FlowPane flowPane = new FlowPane(15, 15);
        flowPane.setPadding(new Insets(10));
        flowPane.setPrefWrapLength(500); // Ajustar ancho

        Equipaje[] equipajes = boleto.getEquipajes(); //
        boolean hayEquipaje = false;
        if(equipajes != null) {
            for(Equipaje eq : equipajes){
                if(eq != null){
                    hayEquipaje = true;
                    VBox cardEq = new VBox(5);
                    cardEq.setPadding(new Insets(10));
                    cardEq.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
                    cardEq.setPrefWidth(220);
                    cardEq.getChildren().addAll(
                            new Label("ID: " + eq.getIdEquipaje()){{ setStyle("-fx-font-weight: bold;"); }}, //
                            new Label("Peso: " + String.format("%.2f Kg", eq.getPeso())), //
                            new Label("Estado: " + eq.getEstado()), //
                            new Label("Vagón: " + eq.getIdVagonCarga()) //
                    );
                    flowPane.getChildren().add(cardEq);
                }
            }
        }

        if(!hayEquipaje){
            alert.getDialogPane().setContent(new Label("No hay equipajes registrados para este boleto."));
        } else {
            ScrollPane scroll = new ScrollPane(flowPane);
            scroll.setFitToWidth(true);
            alert.getDialogPane().setContent(scroll);
            alert.setResizable(true);
        }

        Stage stage = (Stage) ownerNode.getScene().getWindow();
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }


    // Métodos auxiliares
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); labelMensaje.setVisible(true); labelMensaje.setManaged(true);}
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.setVisible(false); labelMensaje.setManaged(false); }
}