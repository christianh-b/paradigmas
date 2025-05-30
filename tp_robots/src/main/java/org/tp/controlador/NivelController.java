package org.tp.controlador;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.tp.modelo.Juego;
import org.tp.vista.EtiquetasViews;


public class NivelController implements Subscriber {
    Juego juego;
    Controlador controlador;
    EtiquetasViews etiquetasViews;

    @FXML
    public GridPane tableroContainer;

    public NivelController(Juego juego, Controlador controlador, EtiquetasViews etiquetasViews) {
        this.juego = juego;
        this.controlador = controlador;
        this.etiquetasViews = etiquetasViews;
        juego.suscribirObservadorNivel(this);
    }

    @Override
    public void update() {
        etiquetasViews.mostrarAlertaNivelPasado(juego.getNivelActual());
        controlador.resetearTeletransportacionesSeguras();
        controlador.actualizarTextos();

    }
}
