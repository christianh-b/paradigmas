package org.tp.modelo;

import javafx.scene.image.ImageView;
import org.tp.vista.EntidadView;

public class Jugador implements Entidad, Animable {
    private Celda celdaActual;
    private int recolectablesObtenidos;
    private int teletransportacionSegura;
    private final int CELDAS_POR_TURNO = 1;

    public Jugador() {
        this.recolectablesObtenidos = 0;
        this.teletransportacionSegura = 0;
    }

    public void setCelda(Celda celdaJugador) {
        this.celdaActual = celdaJugador;
    }

    public Celda getCelda() {
        return this.celdaActual;
    }

    public void agregarTeletransportacionSegura() {
        this.teletransportacionSegura++;
    }

    public int getCeldasPorTurno() {
        return CELDAS_POR_TURNO;
    }

    public boolean puedeExplotar() {
        return false;
    }

    public int getRecolectablesObtenidos() {
        return this.recolectablesObtenidos;
    }

    public void agarrarRecolectable(Recolectable recolectable) {
        this.recolectablesObtenidos++;
    }

    public void reiniciarRecolectables() {
        this.recolectablesObtenidos = 0;
    }

    public Celda mover(Celda celda, Tablero tablero) { //para teletransportacion

        if (celda.ocupar(this)) {
            celdaActual.desocuparCelda();
            celdaActual = celda;
            return celdaActual;
        } else {
            return null;
        }
    }


    public Celda mover(int horizontal, int vertical, Tablero tablero) {
        if (horizontal == 0 && vertical == 0) {
            return celdaActual;
        }

        Celda celdaNueva = tablero.getCelda(celdaActual.getFila() + horizontal, celdaActual.getColumna() + vertical);
        if (celdaNueva == null) {
            return null;
        }
        if (celdaNueva.ocupar(this)) {
            celdaActual.desocuparCelda();
             tablero.eliminarCeldaOcupada(celdaActual);
            celdaActual = celdaNueva;
            return celdaActual;
        } else {
            celdaActual.desocuparCelda();
             tablero.eliminarCeldaOcupada(celdaActual);
            return null;
        }
    }

    @Override
    public void interactuarCeldaConRecolectable(CeldaRecolectable celda) {
        if (celda.tieneRecolectable()) {
            agarrarRecolectable(celda.getRecolectable());
            celda.actualizarCelda();
        }
    }

    @Override
    public ImageView[] getImagenesParaAnimacion() {
        return EntidadView.JUGADOR_IMAGENES;
    }
}
