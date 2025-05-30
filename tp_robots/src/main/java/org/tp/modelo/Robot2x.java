package org.tp.modelo;

import javafx.scene.image.ImageView;
import org.tp.vista.EntidadView;

public class Robot2x extends Robot {
    private final int CELDAS_POR_TURNO = 2;

    public int getCeldasPorTurno() {
        return CELDAS_POR_TURNO;
    }

    @Override
    public Celda mover(Celda celdaJugador, Tablero tablero) {
        for (int i = 0; i < CELDAS_POR_TURNO; i++) {
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
            } else {
                celdaActual.desocuparCelda();
                 tablero.eliminarCeldaOcupada(celdaActual);
                return null;
            }

        }
        return celdaActual;
    }

    public ImageView[] getImagenesParaAnimacion() {
        return EntidadView.ROBOT2X_IMAGENES;
    }
}
