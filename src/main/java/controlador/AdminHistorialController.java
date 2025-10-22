package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import modelo.persistencia.GestorArchivos; // Importar GestorArchivos

public class AdminHistorialController {

    @FXML private TextArea textAreaLog;

    @FXML
    public void initialize() {
        System.out.println("AdminHistorialController inicializado.");
        cargarHistorial();
    }

    private void cargarHistorial() {
        // Llama al método estático para leer el archivo log.txt
        String contenidoLog = GestorArchivos.leerTxt("log"); //

        if (contenidoLog != null) {
            textAreaLog.setText(contenidoLog);
            // Mover el scroll al final (opcional, para ver lo más reciente primero)
            textAreaLog.setScrollTop(Double.MAX_VALUE);
        } else {
            textAreaLog.setText("No se pudo cargar el archivo log.txt o está vacío.");
        }
    }
}