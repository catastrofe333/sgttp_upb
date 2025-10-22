package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox; // Importar
import modelo.entidades.*;
import modelo.estructuras.ColaPrioridad; // Importar
import modelo.logica.GestorAbordaje; // Importar
import modelo.logica.Sistema;
import vista.Aplicacion;

import java.io.IOException;

public class EmpleadoViajesAbordajeController {

    // --- Campos FXML (pueden ser null dependiendo de la vista cargada) ---
    // Vista Inicio:
    @FXML private ComboBox<String> comboBoxIdViajeAbordaje;
    // Vista Mostrar:
    @FXML private Label labelTituloAbordaje;
    @FXML private Label labelAsientoSiguiente;
    @FXML private Label labelInfoPasajero;
    // Común:
    @FXML private Label labelMensajeAbordaje;

    private final Sistema sistema = Aplicacion.getSistema();
    private final GestorAbordaje gestorAbordaje = new GestorAbordaje(); // Instancia del gestor

    // --- Estado del abordaje ---
    private static ColaPrioridad<Boleto> colaAbordajeActual; // Estática para mantenerla entre cargas de FXML
    private static String idViajeAbordajeActual; // Para el título

    @FXML
    public void initialize() {
        ocultarMensaje();
        // Si estamos en la vista de inicio, cargar viajes
        if (comboBoxIdViajeAbordaje != null) {
            cargarViajesPendientesOViajando();
            // Limpiar estado anterior si volvemos a esta pantalla
            colaAbordajeActual = null;
            idViajeAbordajeActual = null;
        }
        // Si estamos en la vista de mostrar, mostrar el siguiente
        else if (labelAsientoSiguiente != null) {
            if (colaAbordajeActual == null || idViajeAbordajeActual == null) {
                // Error: se llegó a esta pantalla sin iniciar abordaje
                mostrarError("Error: No se ha iniciado un proceso de abordaje.");
                // Podríamos navegar hacia atrás aquí
            } else {
                labelTituloAbordaje.setText("ABORDAJE VIAJE " + idViajeAbordajeActual);
                mostrarSiguiente(); // Muestra el primero al cargar
            }
        }
    }

    private void cargarViajesPendientesOViajando() {
        if(comboBoxIdViajeAbordaje == null) return;
        comboBoxIdViajeAbordaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes(); //
        if (viajes != null) {
            for (Viaje v : viajes) {
                // Solo viajes que no han finalizado
                if (v != null && v.getEstado() != modelo.enums.EstadoTrayecto.FINALIZADO) { //
                    comboBoxIdViajeAbordaje.getItems().add(v.getIdViaje()); //
                }
            }
        }
    }

    @FXML
    void onEmpezarAbordajeClick(ActionEvent event) {
        String idViaje = comboBoxIdViajeAbordaje.getValue();
        if (idViaje == null) {
            mostrarError("Seleccione un viaje para iniciar el abordaje.");
            return;
        }

        Boleto[] boletosDelViaje = sistema.getGestorBoletos().buscarBoletosIdViaje(idViaje); //

        if (boletosDelViaje == null || boletosDelViaje.length == 0) {
            mostrarError("Este viaje no tiene boletos registrados. No se puede iniciar el abordaje.");
            return;
        }

        // Generar la cola de prioridad
        colaAbordajeActual = gestorAbordaje.generarAbordaje(boletosDelViaje); //
        idViajeAbordajeActual = idViaje; // Guardar ID para el título

        // Navegar a la pantalla de mostrar siguiente
        loadSubContentAbordaje("/empleado_viajes_abordaje_mostrar.fxml", event);
    }

    @FXML
    void onSiguienteAbordajeClick(ActionEvent event) {
        mostrarSiguiente();
    }

    private void mostrarSiguiente() {
        if (colaAbordajeActual == null) {
            mostrarError("Error: Cola de abordaje no inicializada.");
            return;
        }

        if (colaAbordajeActual.vacio()) { //
            labelAsientoSiguiente.setText("FIN");
            labelInfoPasajero.setText("Todos los pasajeros han sido llamados.");
            mostrarExito("Abordaje completado para el viaje " + idViajeAbordajeActual + ".");
            // Podríamos deshabilitar el botón "Siguiente" aquí
        } else {
            Boleto siguiente = colaAbordajeActual.desencolar(); //
            labelAsientoSiguiente.setText("ASIENTO " + siguiente.getAsiento()); //
            labelInfoPasajero.setText("(Pasajero: " + siguiente.getNombrePasajero() + " " + siguiente.getApellidoPasajero() + //
                    " - Cat: " + siguiente.getCategoria() + ")"); //
            ocultarMensaje();
        }
    }


    // Método específico para cargar sub-vistas DENTRO de la sección de abordaje
    private void loadSubContentAbordaje(String fxmlPath, ActionEvent event) {
        try {
            // Asume que el botón está dentro de un VBox que está en el StackPane
            Node sourceNode = (Node) event.getSource();
            StackPane contentArea = (StackPane) sourceNode.getScene().lookup("#contentAreaEmpleado");

            if (contentArea != null) {
                // Usamos un FXMLLoader para poder acceder al controlador si fuera necesario
                FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource(fxmlPath)); //
                Parent subVista = loader.load();
                contentArea.getChildren().clear();
                contentArea.getChildren().add(subVista);
            } else { System.err.println("Error: No se encontró StackPane 'contentAreaEmpleado'."); }
        } catch (IOException | NullPointerException e) {
            String errorMsg = (e instanceof NullPointerException) ? "No se encontró FXML: " : "Error al cargar: ";
            System.err.println(errorMsg + fxmlPath); e.printStackTrace();
        }
    }

    // Métodos auxiliares de mensajes
    private void mostrarExito(String msg) { if(labelMensajeAbordaje != null) { labelMensajeAbordaje.setText("✅ "+msg); labelMensajeAbordaje.getStyleClass().setAll("successLabel"); labelMensajeAbordaje.setVisible(true); labelMensajeAbordaje.setManaged(true);}}
    private void mostrarError(String msg) { if(labelMensajeAbordaje != null) { labelMensajeAbordaje.setText("❌ "+msg); labelMensajeAbordaje.getStyleClass().setAll("errorLabel"); labelMensajeAbordaje.setVisible(true); labelMensajeAbordaje.setManaged(true);}}
    private void ocultarMensaje() { if(labelMensajeAbordaje != null) { labelMensajeAbordaje.setText(""); labelMensajeAbordaje.setVisible(false); labelMensajeAbordaje.setManaged(false); }}
}