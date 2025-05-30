package org.tp.modelo;

import org.tp.controlador.Subscriber;

import java.util.*;

public class Tablero implements Observable {
    private final Random random;
    private final int filas;
    private final int columnas;
    private final Celda[][] celdas;
    private Jugador jugador;
    private final List<Entidad> enemigos;
    private List<Celda> recolectables;
    private List<Celda> celdasOcupadas;
    private List<Celda> celdasIncendiadas;
    private List<Subscriber> suscriptores;
    private boolean juegoFinalizado;
    private boolean nivelFinalizado;

    public Tablero(int filas, int columnas, Jugador jugador) {
        this.random = new Random();
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Celda[filas][columnas];
        this.jugador = jugador;
        this.celdasOcupadas = new ArrayList<>();
        this.enemigos = new ArrayList<>();
        this.recolectables = new ArrayList<>();
        this.celdasIncendiadas = new ArrayList<>();
        this.juegoFinalizado = false;
        this.nivelFinalizado = false;
    }

    public int getFilas() {
        return this.filas;
    }

    public int getColumnas() {
        return this.columnas;
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public List<Entidad> getEnemigos() {
        return this.enemigos;
    }

    public List<Celda> getRecolectables() {
        return this.recolectables;
    }

    public Celda getCelda(int fil, int col) {
        if (celdaDentroDeRango(fil, col)) {
            return this.celdas[fil][col];
        }
        return null;
    }

    public List<Celda> getCeldasOcupadas() {
        return this.celdasOcupadas;
    }

    public List<Celda> getCeldasIncendiadas() {
        return this.celdasIncendiadas;
    }

    public Celda getCeldaSegura() {

        Celda celda;
        int intentos = 0;
        int maximoIntentos = filas * columnas - celdasOcupadas.size();
        while (intentos <= maximoIntentos) {
            intentos++;
            celda = getCeldaAleatoriaLibre();
            if (celdasIncendiadas.contains(celda)) {
                continue;
            }
            boolean esSegura = true;
            Celda celdaEnemigo;
            for (Entidad enemigo : enemigos) {
                int distanciaMinima = enemigo.getCeldasPorTurno();
                celdaEnemigo = enemigo.getCelda();
                if (!cumpleDistanciaMinima(distanciaMinima + 1, celdaEnemigo, celda)) {
                    esSegura = false;
                    break;
                }
            }
            if (esSegura) {
                return celda;
            }
        }
        return null;
    }

    public Celda getCeldaAleatoriaLibre() {
        Celda celda;
        do {
            int fil = random.nextInt(this.filas);
            int col = random.nextInt(this.columnas);
            celda = this.getCelda(fil, col);
        } while (celda.estaOcupada());
        return celda;
    }

    public Celda getCeldaAleatoria() {
        int fil = random.nextInt(this.filas);
        int col = random.nextInt(this.columnas);
        return this.getCelda(fil, col);
    }

    public void reemplazarCelda(Celda celda1, Celda celda2) {
        celdas[celda1.getFila()][celda1.getColumna()] = celda2;
    }

    public void agregarCeldaOcupada(Celda celda) {
        this.celdasOcupadas.add(celda);
    }

    public void agregarCeldaIncendiada(Celda celda) {
        this.celdasIncendiadas.add(celda);
    }

    public void agregarCeldaRecolectable(Celda celda) {
        this.recolectables.add(celda);
    }

    public void eliminarRecolectable(Celda celda) {
        recolectables.remove(celda); //no elimino la celda, solo la saco de celdas recolectables
    }

    public void eliminarCeldaOcupada(Celda celda) {
        this.celdasOcupadas.remove(celda);
    }

    public void inicializarCeldas(GestorDeColisiones gestorColisiones) {
        for (int fil = 0; fil < this.filas; fil++) {
            for (int col = 0; col < this.columnas; col++) {
                this.celdas[fil][col] = new Celda(fil, col, gestorColisiones);
            }
        }
    }

    private boolean celdaDentroDeRango(int fil, int col) {
        return ((fil >= 0 && fil < this.filas) && (col >= 0 && col < this.columnas));
    }

    public boolean cumpleDistanciaMinima(int distanciaMinima, Celda celda1, Celda celda2) {
        return Math.max(Math.abs(celda2.getFila() - celda1.getFila()), Math.abs(celda2.getColumna() - celda1.getColumna())) > distanciaMinima;
    }

    public void agregarRobot(Robot robot) {
        this.enemigos.add(robot);
    }

    public void eliminarEntidad(Entidad entidad) {
        if(enemigos.contains(entidad)){
            enemigos.remove(entidad);
        }
    }

    public boolean juegoEstaFinalizado() {
        return this.juegoFinalizado;
    }

    public boolean nivelFinalizado() {
        return this.nivelFinalizado;
    }

    public void setNivelFinalizado() {
        this.nivelFinalizado = true;
    }

    public void finalizarJuego() {
        enemigos.clear();
        update();
        juegoFinalizado = true;
    }

    public void reiniciarEstadoNivel() {
        this.nivelFinalizado = false;
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
}

