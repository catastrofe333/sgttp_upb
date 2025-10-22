package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.entidades.*;
import modelo.enums.EstadoEquipaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class EmpleadoEquipajesEntregarController {

    @FXML private ComboBox<String> comboBoxIdBoleto;
    @FXML private ComboBox<String> comboBoxIdEquipaje;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarBoletosConEquipajeRegistrado();
        comboBoxIdEquipaje.setDisable(true);
    }

    private void cargarBoletosConEquipajeRegistrado() {
        comboBoxIdBoleto.getItems().clear();
        Boleto[] boletos = sistema.getGestorBoletos().getBoletos(); //
        if (boletos != null) {
            for (Boleto b : boletos) {
                if (b != null && b.getNumEquipajes() > 0) { // Si tiene equipaje
                    boolean tieneRegistrado = false;
                    for(Equipaje eq : b.getEquipajes()){ //
                        if(eq != null && eq.getEstado() == EstadoEquipaje.REGISTRADO){ //
                            tieneRegistrado = true;
                            break;
                        }
                    }
                    if(tieneRegistrado) comboBoxIdBoleto.getItems().add(b.getIdBoleto()); //
                }
            }
        }
    }

    @FXML
    void onBoletoSeleccionado(ActionEvent event) {
        String idBoleto = comboBoxIdBoleto.getValue();
        comboBoxIdEquipaje.getItems().clear();
        comboBoxIdEquipaje.setDisable(true);
        ocultarMensaje();

        if (idBoleto == null) return;

        Boleto boleto = sistema.getGestorBoletos().buscarBoletoPorIdBoleto(idBoleto); //
        if (boleto != null) {
            for (Equipaje eq : boleto.getEquipajes()) { //
                // Solo mostrar equipajes REGISTRADOS para este boleto
                if (eq != null && eq.getEstado() == EstadoEquipaje.REGISTRADO) { //
                    comboBoxIdEquipaje.getItems().add(eq.getIdEquipaje()); //
                }
            }
            if (!comboBoxIdEquipaje.getItems().isEmpty()) {
                comboBoxIdEquipaje.setDisable(false);
            } else {
                mostrarError("Este boleto no tiene equipajes pendientes de entrega.");
            }
        }
    }


    @FXML
    void onEntregarClick(ActionEvent event) {
        String idBoleto = comboBoxIdBoleto.getValue();
        String idEquipaje = comboBoxIdEquipaje.getValue();
        Empleado empleado = (Empleado) sistema.getSesionActual();

        if (idBoleto == null || idEquipaje == null) {
            mostrarError("Seleccione boleto y equipaje.");
            return;
        }

        boolean exito = sistema.getGestorBoletos().marcarEntregadoEquipaje(idBoleto, idEquipaje, empleado); //

        if (exito) {
            mostrarExito("Equipaje " + idEquipaje + " marcado como ENTREGADO.");
            // Actualizar combos
            comboBoxIdEquipaje.getItems().remove(idEquipaje);
            comboBoxIdEquipaje.setValue(null);
            if (comboBoxIdEquipaje.getItems().isEmpty()) {
                comboBoxIdEquipaje.setDisable(true);
                // Si ya no quedan equipajes registrados en este boleto, quitarlo del combo principal
                Boleto boleto = sistema.getGestorBoletos().buscarBoletoPorIdBoleto(idBoleto); //
                boolean quedanRegistrados = false;
                if(boleto != null){
                    for(Equipaje eq : boleto.getEquipajes()){ //
                        if(eq != null && eq.getEstado() == EstadoEquipaje.REGISTRADO){ //
                            quedanRegistrados = true; break;
                        }
                    }
                }
                if(!quedanRegistrados) {
                    comboBoxIdBoleto.getItems().remove(idBoleto);
                    comboBoxIdBoleto.setValue(null);
                }
            }
        } else {
            mostrarError("Error: No se encontró el boleto o el equipaje.");
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}