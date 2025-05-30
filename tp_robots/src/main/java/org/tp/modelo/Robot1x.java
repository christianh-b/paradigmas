package org.tp.modelo;

import javafx.scene.image.ImageView;
import org.tp.vista.EntidadView;

public class Robot1x extends Robot {
    private final int CELDAS_POR_TURNO = 1;

    public int getCeldasPorTurno() {
        return CELDAS_POR_TURNO;
    }

    @Override
    public Celda mover(Celda celdaJugador, Tablero tablero) {
        int movimientoFil = super.calcularMovimientoFila(celdaJugador);
        int movimientoCol = super.calcularMovimientoColumna(celdaJugador);
        Celda celdaNueva = tablero.getCelda(celdaActual.getFila() + movimientoFil, celdaActual.getColumna() + movimientoCol);

        if (!celdaNueva.esTransitablePorRobot()) {
            celdaNueva = super.buscarOtroCamino(movimientoFil, movimientoCol, tablero, celdaNueva);
            if (celdaNueva == null) {
                return null;
            }
        }

        if (celdaNueva.ocupar(this)) {
            celdaActual.desocuparCelda();
            tablero.eliminarCeldaOcupada(celdaActual);
            celdaActual = celdaNueva;
            return celdaActual;
        }

        celdaActual.desocuparCelda();
        tablero.eliminarCeldaOcupada(celdaActual);
        return null;

    }

    public ImageView[] getImagenesParaAnimacion() {
        return EntidadView.ROBOT1X_IMAGENES;
    }
}

