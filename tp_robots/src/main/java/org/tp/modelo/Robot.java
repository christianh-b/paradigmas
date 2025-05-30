package org.tp.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Robot implements Entidad, Animable {
    protected Celda celdaActual;

    public Celda getCelda() {
        return this.celdaActual;
    }

    public void setCelda(Celda celda) {
        this.celdaActual = celda;
    }

    public boolean puedeExplotar() {
        return true;
    }

    public abstract Celda mover(Celda celdaJugador, Tablero tablero);

    protected int calcularMovimientoFila(Celda celdaJugador) {
        int deltaFil = celdaActual.getFila() - celdaJugador.getFila();
        int movimientoFil = 0;
        if (deltaFil != 0) {
            movimientoFil = deltaFil / -Math.abs(deltaFil);
        }
        return movimientoFil;
    }

    protected int calcularMovimientoColumna(Celda celdaJugador) {
        int deltaCol = celdaActual.getColumna() - celdaJugador.getColumna();
        int movimientoCol = 0;
        if (deltaCol != 0) {
            movimientoCol = deltaCol / -Math.abs(deltaCol);
        }
        return movimientoCol;
    }

    protected Celda buscarOtroCamino(int movimientoFil, int movimientoCol, Tablero tablero, Celda celdaRecolectable) {
        List<Celda> celdasAlternativas = new ArrayList<>();
        int filaAlternativa1;
        int filaAlternativa2;
        int columnaAlternativa1;
        int columnaAlternativa2;
        //no haria falta chequear que no haya recolectable porque sabemos que van a estar a distancia 3
        if (movimientoFil != 0 && movimientoCol != 0) {
            filaAlternativa1 = celdaRecolectable.getFila();
            columnaAlternativa1 = celdaActual.getColumna();
            filaAlternativa2 = celdaActual.getFila();
            columnaAlternativa2 = celdaRecolectable.getColumna();
        } else if (movimientoFil != 0) {
            filaAlternativa1 = celdaRecolectable.getFila();
            columnaAlternativa1 = celdaRecolectable.getColumna() - 1;
            filaAlternativa2 = celdaRecolectable.getFila();
            columnaAlternativa2 = celdaRecolectable.getColumna() + 1;
        } else {
            filaAlternativa1 = celdaRecolectable.getFila() - 1;
            columnaAlternativa1 = celdaRecolectable.getColumna();
            filaAlternativa2 = celdaRecolectable.getFila() + 1;
            columnaAlternativa2 = celdaRecolectable.getColumna();
        }


        Celda celdaAlternativa1 = tablero.getCelda(filaAlternativa1, columnaAlternativa1);
        Celda celdaAlternativa2 = tablero.getCelda(filaAlternativa2, columnaAlternativa2);

        if (celdaAlternativa1 != null) {
            celdasAlternativas.add(celdaAlternativa1);
        }
        if (celdaAlternativa2 != null) {
            celdasAlternativas.add(celdaAlternativa2);
        }

        Collections.shuffle(celdasAlternativas);
        if (celdasAlternativas.isEmpty()) {
            return null;
        }
        return celdasAlternativas.get(0);
    }
}
