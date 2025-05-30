package org.tp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tp.modelo.Juego;
import org.tp.utilities.Path;
import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {
    private static Stage escenario;
    public static Juego juego;

    public static Juego getJuego() {
        return juego;
    }

    public static Stage getEscenario() {
        return escenario;
    }

    @Override
    public void start(Stage stage) throws IOException {
        escenario = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(Path.INICIO));
        Scene escenaInicio = new Scene(fxmlLoader.load());
        stage.setTitle("Robots");
        stage.setScene(escenaInicio);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}