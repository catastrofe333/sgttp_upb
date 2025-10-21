package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.logica.Sistema;

import java.io.IOException;

public class Aplicacion extends Application {
    private static final Sistema sistema = new Sistema();

    public static Sistema getSistema() {
        return sistema;
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tu FXML se carga (cambia esto de nuevo a inicio.fxml cuando termines las pruebas)
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("ESC"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        // --- ⬇️ AÑADE ESTA LÍNEA ⬇️ ---
        stage.setFullScreenExitHint(""); // Esto oculta el mensaje "Press ESC..."
        // --- ⬆️ FIN DE LA SOLUCIÓN ⬆️ ---

        stage.show();
    }
}
