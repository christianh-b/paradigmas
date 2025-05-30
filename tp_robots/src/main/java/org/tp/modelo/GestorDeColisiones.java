package org.tp.modelo;

public class GestorDeColisiones {
    private Tablero tablero;

    public GestorDeColisiones(Tablero tablero) {
        this.tablero = tablero;
    }

    public void resolverColision(Entidad entidad1, Entidad entidad2, Celda celda) {
        tablero.eliminarEntidad(entidad1);
        tablero.eliminarEntidad(entidad2);

        if ((entidad1.puedeExplotar()) && (entidad2.puedeExplotar())) {
            CeldaIncendiada celdaIncendiada = new CeldaIncendiada(celda.getFila(), celda.getColumna(), this);
            tablero.reemplazarCelda(celda, celdaIncendiada);
            tablero.agregarCeldaIncendiada(celdaIncendiada);
            tablero.eliminarCeldaOcupada(celda); //eliminar por si estaba en la lista de celdas ocupadas, si no estaba queda igual la lista
        }else{
            tablero.finalizarJuego();
        }
    }

    public void eliminarEntidad(Entidad entidad) {
        tablero.eliminarEntidad(entidad);
    }

    public void eliminarRecolectable(Celda celda) {
        tablero.eliminarRecolectable(celda);
    }
}
