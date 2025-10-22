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
import modelo.entidades.Ruta;
import modelo.entidades.Viaje;
import modelo.enums.Estacion;
import vista.Aplicacion; // Usamos tu clase Aplicacion para obtener el Sistema

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InicioController {
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
        System.out.println("Botón 'Viajes' presionado. Mostrando todos los viajes visibles...");
        try {
            // 1. Obtener TODOS los viajes visibles
            Viaje[] todosLosViajesVisibles = Aplicacion.getSistema().getGestorViajes().buscarViajesVisibles(); //

            // 2. Cargar la vista de resultados
            FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/resultados_busqueda_rutas.fxml")); //
            Parent root = fxmlLoader.load();
            ResultadosController controllerResultadosController = fxmlLoader.getController();

            // 3. Llamar a inicializarDatos con los viajes y parámetros genéricos
            //    Como no hay origen/destino específico, podemos poner null o un texto
            //    La fecha podría ser la actual, pero no es tan relevante aquí.
            //    esBusquedaDirecta es false porque no es una búsqueda Origen-Destino.
            controllerResultadosController.inicializarDatos(
                    todosLosViajesVisibles,
                    null, // Sin origen específico
                    null, // Sin destino específico
                    new Date(), // Fecha actual como referencia
                    false // No es una búsqueda directa
            );
            // Sobrescribir el título para que tenga sentido
            controllerResultadosController.setTituloBusqueda("Todos los Viajes Disponibles");


            // 4. Cambiar la escena
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root); //

        } catch (IOException e) {
            e.printStackTrace();
            // Podrías mostrar un error al usuario si falla la carga
        } catch (Exception e) {
            // Captura general por si buscarViajesVisibles falla
            e.printStackTrace();
        }
    }

    @FXML
    public void onBoletosClick(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/mis_boletos_opciones.fxml")); // Carga las opciones
            Parent root = loader.load();
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root); //
        } catch (IOException e) {
            System.err.println("Error al cargar mis_boletos_opciones.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onIniciarClick(ActionEvent actionEvent) {
        // --- ⬇️ REEMPLAZA ESTE MÉTODO CON ESTO ⬇️ ---
        try {
            FXMLLoader loader = new FXMLLoader(Aplicacion.class.getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = ((Node) actionEvent.getSource()).getScene();
            scene.setRoot(root); // Cambia el contenido de la escena actual
        } catch (IOException e) {
            System.err.println("Error al cargar login.fxml: " + e.getMessage());
            e.printStackTrace();
        }
        // --- ⬆️ FIN DEL REEMPLAZO ⬆️ ---
    }

    @FXML
    public void onBuscarClick(ActionEvent actionEvent) throws IOException {

        Estacion estacionOrigen = origen.getValue();
        Estacion estacionDestino = destino.getValue();
        Date fecha = Date.from(fecha_salida.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        // ----- LÓGICA DE BÚSQUEDA MODIFICADA (Punto 4) -----
        Viaje[] viajesAMostrar;
        boolean busquedaDirecta = true;

        // 1. Intentar búsqueda directa
        //
        Ruta[] rutasDirectas = Aplicacion.getSistema().getGestorRuta().buscarRutasPorOrigenDestino(estacionOrigen, estacionDestino);
        //
        Viaje[] viajesDirectos = Aplicacion.getSistema().getGestorViajes().buscarViajesPorRuta(rutasDirectas, fecha);

        if (viajesDirectos != null && viajesDirectos.length > 0) {
            viajesAMostrar = viajesDirectos;
        } else {
            // 2. Si no hay directos, buscar indirectos
            busquedaDirecta = false;

            // 2a. Viajes DESDE el origen
            //
            Ruta[] rutasOrigen = Aplicacion.getSistema().getGestorRuta().buscarRutasPorOrigen(estacionOrigen);
            //
            Viaje[] viajesOrigen = Aplicacion.getSistema().getGestorViajes().buscarViajesPorRuta(rutasOrigen, fecha);

            // 2b. Viajes HACIA el destino
            //
            Ruta[] rutasDestino = Aplicacion.getSistema().getGestorRuta().buscarRutasPorDestino(estacionDestino);
            //
            Viaje[] viajesDestino = Aplicacion.getSistema().getGestorViajes().buscarViajesPorRuta(rutasDestino, fecha);

            // 2c. Combinar resultados (Usando el GestorViajes modificado)
            //
            viajesAMostrar = Aplicacion.getSistema().getGestorViajes().concatenarViajes(viajesOrigen, viajesDestino);
        }
        // ----- FIN LÓGICA DE BÚSQUEDA -----

        // Cargar FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/resultados_busqueda_rutas.fxml"));
        Parent root = fxmlLoader.load();
        ResultadosController controllerResultadosController = fxmlLoader.getController();

        // Pasar el flag 'busquedaDirecta' (Punto 3)
        controllerResultadosController.inicializarDatos(viajesAMostrar, estacionOrigen, estacionDestino, fecha, busquedaDirecta);

        // Transición de escena
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(root);
    }
}