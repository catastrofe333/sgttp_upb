package controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.entidades.*;
import modelo.enums.EstadoTrayecto;
import modelo.logica.Sistema;
import vista.Aplicacion;

public class EmpleadoViajesEstadoController {

    @FXML private ComboBox<String> comboBoxIdViaje;
    @FXML private ComboBox<EstadoTrayecto> comboBoxEstado;
    @FXML private Label labelMensaje;

    private final Sistema sistema = Aplicacion.getSistema();

    @FXML
    public void initialize() {
        ocultarMensaje();
        comboBoxEstado.getItems().setAll(EstadoTrayecto.values()); // Carga los estados del enum
        comboBoxEstado.setDisable(true);
        cargarViajes();
    }

    private void cargarViajes() {
        if(comboBoxIdViaje == null) return;
        comboBoxIdViaje.getItems().clear();
        Viaje[] viajes = sistema.getGestorViajes().getViajes(); //
        if (viajes != null) {
            for (Viaje v : viajes) {
                // Sigue mostrando solo viajes PENDIENTES o VIAJANDO
                if (v != null && v.getEstado() != EstadoTrayecto.FINALIZADO) { //
                    comboBoxIdViaje.getItems().add(v.getIdViaje()); //
                }
            }
        }
    }

    @FXML
    void onViajeSeleccionado(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        comboBoxEstado.setDisable(true);
        ocultarMensaje();
        if(idViaje == null) return;

        Viaje viaje = sistema.getGestorViajes().buscarViaje(idViaje); //
        if(viaje != null){
            comboBoxEstado.setValue(viaje.getEstado()); // Mostrar estado actual
            comboBoxEstado.setDisable(false);
        }
    }

    @FXML
    void onActualizarClick(ActionEvent event) {
        String idViaje = comboBoxIdViaje.getValue();
        EstadoTrayecto nuevoEstado = comboBoxEstado.getValue();
        Empleado empleado = (Empleado) sistema.getSesionActual();

        if (idViaje == null || nuevoEstado == null) {
            mostrarError("Seleccione un viaje y un nuevo estado.");
            return;
        }

        // Guardar estado actual ANTES de modificar, para la lógica de kilometraje
        Viaje viaje = sistema.getGestorViajes().buscarViaje(idViaje); //
        EstadoTrayecto estadoAnterior = (viaje != null) ? viaje.getEstado() : null; //

        // Intentar modificar el estado del viaje
        boolean exitoModificarEstado = sistema.getGestorViajes().modificarEstadoTrayecto(idViaje, nuevoEstado, empleado); //

        if (exitoModificarEstado) {
            mostrarExito("Estado del viaje " + idViaje + " actualizado a " + nuevoEstado + ".");

            // ----- LÓGICA PARA ACTUALIZAR KILOMETRAJE Y VACIAR PASAJEROS -----
            if (estadoAnterior != EstadoTrayecto.FINALIZADO && nuevoEstado == EstadoTrayecto.FINALIZADO) { // Si se acaba de finalizar

                // 1. Obtener Ruta y Tren
                Ruta ruta = (viaje != null) ? sistema.getGestorRuta().buscarRutaPorId(viaje.getIdRuta()) : null; //
                if (ruta != null) {
                    Tren tren = sistema.getGestorTrenes().buscarTren(ruta.getIdTren()); //
                    if (tren != null) {
                        // 2. Actualizar Kilometraje del Tren
                        double kilometrosRuta = ruta.getKilometros(); //
                        boolean exitoKm = sistema.getGestorTrenes().actualizarKilometrajeTren(tren.getIdTren(), kilometrosRuta, empleado); //
                        if (!exitoKm) {
                            // Mostrar un error adicional si no se pudo actualizar el KM, pero continuar
                            mostrarError("Advertencia: No se pudo actualizar el kilometraje del tren " + tren.getIdTren());
                        }

                        // 3. Vaciar Pasajeros del Tren
                        boolean exitoVaciar = sistema.getGestorTrenes().vaciarPasajerosTren(tren.getIdTren(), empleado); //
                        if (exitoVaciar) {
                            System.out.println("Pasajeros vaciados del tren " + tren.getIdTren() + " para viaje finalizado " + idViaje);
                        } else {
                            System.err.println("Error al intentar vaciar pasajeros del tren " + tren.getIdTren());
                        }

                    } else {
                        mostrarError("Advertencia: No se encontró el tren " + ruta.getIdTren() + " para actualizar KM y vaciar.");
                    }
                } else {
                    String idRuta = (viaje != null) ? viaje.getIdRuta() : "[ID RUTA DESCONOCIDO]";
                    mostrarError("Advertencia: No se encontró la ruta " + idRuta + " para actualizar KM y vaciar.");
                }

                // Quitar el viaje finalizado de la lista
                comboBoxIdViaje.getItems().remove(idViaje);
                comboBoxIdViaje.setValue(null);
                comboBoxEstado.setValue(null);
                comboBoxEstado.setDisable(true);
            }
            // ----- FIN LÓGICA KILOMETRAJE Y VACIAR -----

        } else {
            mostrarError("Error: No se pudo actualizar el estado del viaje (¿ya estaba finalizado?).");
            // Recargar por si el estado actual cambió externamente
            cargarViajes();
            onViajeSeleccionado(null); // Resetea el combo de estado
        }
    }

    // Métodos auxiliares
    private void mostrarExito(String msg) { labelMensaje.setText("✅ "+msg); labelMensaje.getStyleClass().setAll("successLabel"); }
    private void mostrarError(String msg) { labelMensaje.setText("❌ "+msg); labelMensaje.getStyleClass().setAll("errorLabel"); }
    private void ocultarMensaje() { labelMensaje.setText(""); labelMensaje.getStyleClass().clear(); }
}