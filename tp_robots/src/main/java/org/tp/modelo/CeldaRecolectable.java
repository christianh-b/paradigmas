package org.tp.modelo;

public class CeldaRecolectable extends Celda {
    private Recolectable recolectable;

    public CeldaRecolectable(int fila, int columna, GestorDeColisiones gestorColisiones) {
        super(fila, columna, gestorColisiones);
        super.esTransitableRobot = false;
        super.estaOcupada = false;
    }

    public void colocarRecolectable(Recolectable recolectable) {
        this.recolectable = recolectable;
    }

    public boolean tieneRecolectable() {
        return this.recolectable != null;
    }

    public void actualizarCelda() {
        gestorColisiones.eliminarRecolectable(this);
        this.recolectable = null;
        super.esTransitableRobot = true;
    }

    public Recolectable getRecolectable() {
        return recolectable;
    }

    @Override
    public boolean ocupar(Entidad entidad) {
        entidad.interactuarCeldaConRecolectable(this);
        if (super.estaOcupada) {
            super.gestorColisiones.resolverColision(super.entidad, entidad, this);
            return false;
        }
        super.estaOcupada = true;
        super.entidad = entidad;
        return true;
    }

    @Override
    public Celda reiniciarCelda() {
        return new Celda(super.fila, super.columna, super.gestorColisiones);
    }
}
