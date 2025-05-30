package org.tp.modelo;

public interface Entidad {
    Celda mover(Celda celda, Tablero tablero);
    Celda getCelda();
    void setCelda(Celda celda);
    int getCeldasPorTurno(); //devuelve cuantas celdas se mueve por turno
    boolean puedeExplotar();
    default void interactuarCeldaConRecolectable(CeldaRecolectable celda) {}
}
