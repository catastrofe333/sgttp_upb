package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.entidades.*;
import modelo.enums.EstadoEquipaje;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class EmpleadoEquipajesAgregarController {

    @FXML private ComboBox<String> comboBoxIdBoleto;
    @FXML private TextField fieldPeso;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        cargarBoletos();
    }

    private void cargarBoletos() {
        comboBoxIdBoleto.getItems().clear();
        Boleto[] boletos = sistema.getGestorBoletos().getBoletos(); //
        if (boletos != null) {
            for (Boleto b : boletos) {
                // Solo mostrar boletos que aún no tienen el máximo de equipajes
                if (b != null && b.getNumEquipajes() < b.getMaxEquipaje()) { //
                    comboBoxIdBoleto.getItems().add(b.getIdBoleto()); //
                }
            }
        }
    }

    @FXML
    void onAgregarClick(ActionEvent event) {
        String idBoleto = comboBoxIdBoleto.getValue();
        String pesoStr = fieldPeso.getText().trim();
        Empleado empleado = (Empleado) sistema.getSesionActual();

        if (idBoleto == null || pesoStr.isEmpty()) {
            mostrarError("Seleccione un boleto e ingrese el peso.");
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
            if (peso <= 0 || peso > Equipaje.getMaxPeso()) { // Usando constante estática
                mostrarError("El peso debe ser mayor a 0 y menor o igual a " + Equipaje.getMaxPeso() + " Kg."); //
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("El peso debe ser un número válido.");
            return;
        }

        Boleto boleto = sistema.getGestorBoletos().buscarBoletoPorIdBoleto(idBoleto); //
        if (boleto == null) {
            mostrarError("Error: Boleto no encontrado.");
            return;
        }
        if (boleto.getNumEquipajes() >= boleto.getMaxEquipaje()){ // Doble check
            mostrarError("Este boleto ya alcanzó el máximo de equipajes (" + boleto.getMaxEquipaje() + ")."); //
            cargarBoletos(); // Recargar por si acaso
            return;
        }

        // --- Lógica para asignar vagón de carga (Simple: el primero disponible) ---
        String idVagonCargaAsignado = null;
        Viaje viaje = sistema.getGestorViajes().buscarViaje(boleto.getIdViaje()); //
        if(viaje == null){ mostrarError("Error: Viaje asociado al boleto no encontrado."); return; }
        Ruta ruta = sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta()); //
        if(ruta == null){ mostrarError("Error: Ruta asociada al viaje no encontrada."); return; }
        Tren tren = sistema.getGestorTrenes().buscarTren(ruta.getIdTren()); //
        if(tren == null){ mostrarError("Error: Tren asociado a la ruta no encontrado."); return; }

        for(int i = 0; i < tren.getNumVagonesCarga(); i++){ //
            VagonCarga vc = tren.getVagonesCarga()[i]; //
            if(vc != null) {
                idVagonCargaAsignado = vc.getIdVagon(); // Asigna el primero que encuentre
                break;
            }
        }
        if(idVagonCargaAsignado == null){ mostrarError("Error: El tren de este viaje no tiene vagones de carga asignados."); return; }
        // --- Fin lógica vagón ---


        try {
            String nuevoIdEquipaje = sistema.getGestorIds().generarIdEquipaje(); //
            Equipaje nuevoEquipaje = new Equipaje(nuevoIdEquipaje, peso, idVagonCargaAsignado); //
            // Estado por defecto es REGISTRADO
            boolean agregado = sistema.getGestorBoletos().agregarEquipajeBoleto(idBoleto, nuevoEquipaje, empleado); //

            if (agregado) {
                mostrarExito("Equipaje " + nuevoIdEquipaje + " agregado al boleto " + idBoleto + ".");
                fieldPeso.clear();
                // Si el boleto ya se llenó, quitarlo del combo
                if (boleto.getNumEquipajes() >= boleto.getMaxEquipaje()){ // Ahora sí verificar después de agregar
                    comboBoxIdBoleto.getItems().remove(idBoleto);
                    comboBoxIdBoleto.setValue(null);
                }
            } else {
                mostrarError("No se pudo agregar el equipaje (posiblemente por límite excedido).");
            }

        } catch (Exception e) {
            mostrarError("Error al guardar equipaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}