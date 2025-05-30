package org.tp.controlador;

import org.tp.modelo.Juego;
import org.tp.vista.EtiquetasViews;

public class EstadoJuegoController implements Subscriber {
    EtiquetasViews etiquetasViews;
    Juego juego;
    Controlador controlador;

    public EstadoJuegoController(Juego juego, Controlador controlador, EtiquetasViews etiquetasViews) {
        this.juego = juego;
        this.etiquetasViews = etiquetasViews;
        this.controlador = controlador;
        juego.suscribirMuerteJugador(this);
    }


    @Override
    public void update() {
        etiquetasViews.mostrarAlertaJuegoPerdido(controlador);
    }
}
