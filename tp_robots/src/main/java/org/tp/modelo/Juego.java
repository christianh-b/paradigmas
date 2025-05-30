package org.tp.modelo;

import org.tp.controlador.Subscriber;

public class Juego {
    private Jugador jugador;
    private Tablero tablero;
    private int nivelActual = 1;
    private GestorDeNivel gestorDeNivel;
    private GestorDeMovimiento gestorDeMovimiento;
    private GestorDeColisiones gestorColisiones;

    public Juego(int fil, int col) {
        this.jugador = new Jugador();
        this.tablero = new Tablero(fil, col, jugador);
        this.gestorColisiones = new GestorDeColisiones(this.tablero);
        this.gestorDeNivel = new GestorDeNivel(this.tablero, this.gestorColisiones);
        this.gestorDeNivel.configurarNivel(this.nivelActual);
        this.gestorDeMovimiento = new GestorDeMovimiento(tablero);
    }

    public void reiniciar() {
        jugador.reiniciarRecolectables();
        this.nivelActual = 1;
        this.gestorDeNivel.configurarNivel(this.nivelActual);
    }

    public boolean nivelFinalizado() {
        return this.tablero.nivelFinalizado();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public int getNivelActual() {
        return gestorDeNivel.getNivelActual();
    }

    public int getRecursosPorNivel() {
        return GestorDeNivel.RECURSOS_POR_NIVEL;
    }

    public void teletransportarAzar() {
        gestorDeMovimiento.teletransportarJugador(gestorDeNivel);
    }

    public void teletransportarSeguro() {
        gestorDeMovimiento.teletransportarSeguroJugador(gestorDeNivel);
    }

    public boolean juegoSigue() {
        return !tablero.juegoEstaFinalizado();
    }

    public void realizarMovimiento(int horizontal, int vertical) {
        gestorDeMovimiento.realizarMovimiento(horizontal, vertical, gestorDeNivel);
    }

    public void reiniciarEstadoNivel(){
        tablero.reiniciarEstadoNivel();
    }

    public void suscribirObservadorNivel(Subscriber subscriberNivel) {
        gestorDeNivel.addObserver(subscriberNivel);
    }

    public void suscribirMuerteJugador(Subscriber suscriptor) {
        tablero.addObserver(suscriptor);
    }
}
