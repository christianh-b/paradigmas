package org.tp.modelo;

import org.tp.controlador.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class GestorDeMovimiento {
    private final Tablero tablero;
    private Jugador jugador;
    private List<Subscriber> suscriptores;

    public GestorDeMovimiento(Tablero tablero) {
        this.tablero = tablero;
        this.jugador = tablero.getJugador();
    }

    //cuando el usuario toque una celda se va a llamar a esta funcion con los valores de las coordenadas
    public void realizarMovimiento(int horizontal, int vertical, GestorDeNivel gestorDeNivel) {
        Celda nuevaCelda = jugador.mover(horizontal, vertical, tablero);
        if (nuevaCelda == null) {
            tablero.finalizarJuego();
            return;
        }
        if (jugador.getRecolectablesObtenidos() == 4) {
            tablero.setNivelFinalizado();
            gestorDeNivel.pasarNivel();
            return;
        }
        moverRobots(nuevaCelda);
        if (tablero.getEnemigos().isEmpty() && !tablero.juegoEstaFinalizado()) {
            tablero.setNivelFinalizado();
            gestorDeNivel.pasarNivel();
        }
    }

    private void moverRobots(Celda celdaJugador) {
        List<Entidad> enemigosCopia = new ArrayList<>(tablero.getEnemigos());
        List<Entidad> enemigos = (tablero.getEnemigos()); //original

        for (Entidad enemigo : enemigosCopia) {
            if (!enemigos.contains(enemigo)) {
                continue; // ya fue eliminado, saltearlo
            }
            if (enemigo == null) {
                continue;
            }
            Celda celdaNueva = enemigo.mover(celdaJugador, tablero);
            if (celdaNueva != null) {
                tablero.agregarCeldaOcupada(celdaNueva);
            }
        }

    }


    public void teletransportarJugador(GestorDeNivel gestorDeNivel) {
        Celda celdaAlAzar = tablero.getCeldaAleatoria();
        celdaAlAzar = jugador.mover(celdaAlAzar, tablero);
        if (celdaAlAzar != null) {
            if (jugador.getRecolectablesObtenidos() == 4) {
                gestorDeNivel.pasarNivel();
                return;
            }
            moverRobots(celdaAlAzar);
            if (tablero.getEnemigos().isEmpty() && !tablero.juegoEstaFinalizado()) {
                gestorDeNivel.pasarNivel();
            }
        } else {
            tablero.finalizarJuego();
        }

    }

    public void teletransportarSeguroJugador(GestorDeNivel gestorDeNivel) {
        Celda celdaSegura = tablero.getCeldaSegura();
        if (celdaSegura == null) {
            return;
        }
        celdaSegura = jugador.mover(celdaSegura, tablero);
        if (jugador.getRecolectablesObtenidos() == 4) {
            gestorDeNivel.pasarNivel();
            return;
        }
        moverRobots(celdaSegura);
        if (tablero.getEnemigos().isEmpty() && !tablero.juegoEstaFinalizado()) {
            gestorDeNivel.pasarNivel();
        }
    }
}

