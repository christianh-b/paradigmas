package org.tp.modelo;

import org.tp.controlador.Subscriber;

import java.util.*;

public class GestorDeNivel implements Observable {
    public static final int RECURSOS_POR_NIVEL = 4;
    private static final int DISTANCIA_MINIMA_ENTRE_RECOLECTABLES = 3;
    private int nivelActual;
    private Tablero tablero;
    private GestorDeColisiones gestorColisiones;
    private List<Subscriber> suscriptores;


    public GestorDeNivel(Tablero tablero, GestorDeColisiones gestorColisiones) {
        this.tablero = tablero;
        tablero.inicializarCeldas(gestorColisiones);
        this.gestorColisiones = gestorColisiones;
    }

    public void configurarNivel(int nivel) {
        nivelActual = nivel;

        tablero.getJugador().reiniciarRecolectables();
        limpiarTablero();
        Celda celdaJugador = posicionarJugador(tablero.getFilas(), tablero.getColumnas());
        posicionarRecolectables();
        posicionarRobots(
                calcularCantidadDeRobotsPorNivel(nivel, tablero.getFilas(), tablero.getColumnas()),
                celdaJugador
        );
    }

    private Celda posicionarJugador(int filas, int columnas) {
        Jugador jugador = tablero.getJugador();
        Celda celdaMedio = tablero.getCelda(filas / 2, columnas / 2);
        jugador.setCelda(celdaMedio);
        celdaMedio.ocupar(jugador);
        jugador.agregarTeletransportacionSegura();
        return celdaMedio;
    }

    private void posicionarRecolectables() {
        List<Celda> celdas = celdasParaRecolectables(this.tablero);
        for (Celda celda : celdas) {
            Recolectable diamante = new Diamante();
            CeldaRecolectable celdaRecolectable = new CeldaRecolectable(celda.getFila(), celda.getColumna(), gestorColisiones);
            celdaRecolectable.colocarRecolectable(diamante);
            tablero.reemplazarCelda(celda, celdaRecolectable);
            tablero.agregarCeldaRecolectable(celdaRecolectable);
        }
    }

    public void pasarNivel() {
        this.nivelActual++;
        configurarNivel(nivelActual);
        update();
    }

    private void posicionarRobots(int cantidad, Celda celdaJugador) {
        Queue<Celda> candidatas = celdasParaRobots(celdaJugador);
        int contador = 1;
        Robot robot;
        while (!candidatas.isEmpty() && contador < cantidad) {
            Celda celda = candidatas.poll();
            if (contador % 4 == 0) {
                robot = new Robot2x();
            } else {
                robot = new Robot1x();
            }
            robot.setCelda(celda);
            celda.ocupar(robot);
            tablero.agregarRobot(robot);
            tablero.agregarCeldaOcupada(celda);
            contador++;
        }
    }

    private Queue<Celda> celdasParaRobots(Celda celdaJugador) {
        List<Celda> celdasCandidatas = new ArrayList<>();
        for (int fil = 0; fil < tablero.getFilas(); fil++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                Celda celda = tablero.getCelda(fil, col);
                if (celda.esTransitablePorRobot() &&
                        tablero.cumpleDistanciaMinima(3, celdaJugador, celda)) {
                    celdasCandidatas.add(celda);
                }
            }
        }
        Collections.shuffle(celdasCandidatas);

        return new LinkedList<>(celdasCandidatas);
    }

    private int calcularCantidadDeRobotsPorNivel(int nivel, int filas, int colum) {
        return (int) (filas * colum * (nivel + 0.5)/ 20);
    }

    private List<Celda> celdasParaRecolectables(Tablero tablero) {
        int filas;
        int columnas;
        filas = tablero.getFilas();
        columnas = tablero.getColumnas();
        List<Celda> celdas = new ArrayList<>();

        for (int fil = 0; fil < filas; fil++) {
            for (int col = 0; col < columnas; col++) {
                if (fil == filas / 2 && col == columnas / 2) {
                    continue;
                }
                Celda celda = tablero.getCelda(fil, col);
                celdas.add(celda);
            }
        }

        Collections.shuffle(celdas);
        Queue<Celda> posiblesCeldas = new LinkedList<>(celdas);
        List<Celda> celdasParaRecolectables = new ArrayList<>();

        for (int i = 0; i < RECURSOS_POR_NIVEL; i++) {
            Celda celda;
            do {
                celda = posiblesCeldas.poll();
            } while (!esCeldaValidaParaRecolectable(celdasParaRecolectables, celda));
            celdasParaRecolectables.add(celda);
        }

        return celdasParaRecolectables;

    }

    private boolean esCeldaValidaParaRecolectable(List<Celda> celdas, Celda posibleCelda) {

        for (Celda celda : celdas) {
            if (!tablero.cumpleDistanciaMinima(DISTANCIA_MINIMA_ENTRE_RECOLECTABLES, celda, posibleCelda)) {
                return false;
            }
        }
        return true;
    }


    private void limpiarTablero() {
        // Limpio las entidades del tablero
        tablero.getCeldasOcupadas().clear();
        tablero.getEnemigos().clear();
        tablero.getRecolectables().clear();
        tablero.getCeldasIncendiadas().clear();

        // Limpio las celdas
        for (int fil = 0; fil < tablero.getFilas(); fil++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                Celda celda = tablero.getCelda(fil, col);
                Celda celdaLimpia = celda.reiniciarCelda();
                if (celda != celdaLimpia) {
                    tablero.reemplazarCelda(celda, celdaLimpia);
                }
            }
        }
    }

    @Override
    public void update() {
        if (suscriptores == null) {
            return;
        }
        for (Subscriber suscriptor : suscriptores) {
            suscriptor.update();
        }
    }

    @Override
    public void addObserver(Subscriber suscriptor) {
        if (suscriptores == null) {
            suscriptores = new ArrayList<>();
        }
        suscriptores.add(suscriptor);
    }

    @Override
    public void removeObserver(Subscriber suscriptor) {
        if (suscriptores.contains(suscriptor)) {
            suscriptores.remove(suscriptor);
        }
    }

    public int getNivelActual() {
        return this.nivelActual;
    }
}
