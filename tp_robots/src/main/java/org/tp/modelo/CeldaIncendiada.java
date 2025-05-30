package org.tp.modelo;

public class CeldaIncendiada extends Celda {

    public CeldaIncendiada(int fila, int columna, GestorDeColisiones gestorColisiones) {
        super(fila, columna, gestorColisiones);
    }

    @Override
    public boolean ocupar(Entidad entidad) {
        super.gestorColisiones.eliminarEntidad(entidad);
        return false;
    }

    @Override
    public Celda reiniciarCelda() {
        return new Celda(super.fila, super.columna, super.gestorColisiones);
    }

    @Override
    public boolean esTransitablePorRobot() {
        return true;
    }
}
