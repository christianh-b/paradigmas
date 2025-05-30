package org.tp.modelo;

public class Celda {
    protected int fila;
    protected int columna;
    protected Entidad entidad;
    protected boolean estaOcupada;
    protected boolean esTransitableRobot;
    protected GestorDeColisiones gestorColisiones;

    public Celda(int fila, int columna, GestorDeColisiones gestorColisiones) {
        this.fila = fila;
        this.columna = columna;
        this.entidad = null;
        this.esTransitableRobot = true;
        this.gestorColisiones = gestorColisiones;
        this.estaOcupada = false;
    }

    public Celda reiniciarCelda() {
        entidad = null;
        estaOcupada = false;
        return this;
    }

    public boolean estaOcupada() {
        return estaOcupada;
    }

    public Entidad getEntidad() {
        return this.entidad;
    }

    public boolean ocupar(Entidad entidad) {
        if (estaOcupada) {
            this.gestorColisiones.resolverColision(this.entidad, entidad, this);
            return false;
        }
        estaOcupada = true;
        this.entidad = entidad;
        return true;
    }

    public void desocuparCelda() {
        estaOcupada = false;
        entidad = null;
    }

    public boolean esTransitablePorRobot() {
        return this.esTransitableRobot;
    }

    public int getFila() {
        return this.fila;
    }

    public int getColumna() {
        return this.columna;
    }
}


