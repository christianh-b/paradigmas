package org.tp.vista;

import javafx.scene.layout.*;
import org.tp.controlador.Controlador;
import org.tp.modelo.*;

import java.util.ArrayList;
import java.util.List;


public class TableroView extends GridPane {
    private final Tablero tablero;
    private CeldaView[][] celdas;
    protected static final int TAMANIO_CELDA = 20;
    public static final String COLOR_CLARO = "#e0e8f0";
    public static final String COLOR_OSCURO = "#c0d0e0";
    private CeldaView celdaViewJugador;
    private Controlador controlador;
    private CeldaView jugador;
    private List<CeldaView> celdaViewRecolectables;
    private List<CeldaView> incendiadas;
    private List<CeldaView> ocupadas;

    public TableroView(Tablero tablero) {
        super();
        this.tablero = tablero;
        this.celdas = new CeldaView[tablero.getFilas()][tablero.getColumnas()];
        iniciarTablero();
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public CeldaView getCeldaViewJugador() {
        return celdaViewJugador;
    }

    private void iniciarTablero() {
        jugador = null;
        celdaViewRecolectables = new ArrayList<>();
        incendiadas = new ArrayList<>();
        ocupadas = new ArrayList<>();
        crearCeldas();
        registrarEventoMouseCeldas();
        posicionarEntidades();
        registrarEventoMouseCeldas();
    }

    public void registrarEventoMouseCeldas() {
        for (int fila = 0; fila < celdas.length; fila++) {
            for (int col = 0; col < celdas[fila].length; col++) {
                CeldaView celda = celdas[fila][col];
                CeldaView celdaJugador = getCeldaViewJugador();
                // Acá hacés lo que necesites con cada celda
                celda.setOnMouseEntered(mouseEvent -> controlador.setCursor(mouseEvent));
                celda.setOnMouseClicked(mouseEvent -> controlador.moverJugador(mouseEvent));
            }
        }

    }

    public void actualizar() {
        vaciarCeldas();
        posicionarEntidades();
    }

    public void posicionarEntidades() {
        Jugador jugador = tablero.getJugador();
        Celda celdaJugador = jugador.getCelda();
        celdaViewJugador = this.celdas[celdaJugador.getFila()][celdaJugador.getColumna()];
        this.jugador = celdaViewJugador;
        celdaViewJugador.agregarEntidadAnimada(jugador);

        for (Entidad robot : tablero.getEnemigos()) {

            Celda celdaRobot = (robot).getCelda();
            CeldaView celdaViewRobot = this.celdas[celdaRobot.getFila()][celdaRobot.getColumna()];
            ocupadas.add(celdaViewRobot);
            celdaViewRobot.agregarEntidadAnimada((Animable) robot);
        }

        for (Celda celdaRecolectable : tablero.getRecolectables()) {
            CeldaView celdaViewRecolectable = this.celdas[celdaRecolectable.getFila()][celdaRecolectable.getColumna()];
            celdaViewRecolectable.agregarEntidad(EntidadView.getDiamante());
            celdaViewRecolectables.add(celdaViewRecolectable);
        }

        for (Celda celdaIncendiada : tablero.getCeldasIncendiadas()) {
            CeldaView celdaViewIncendiada = this.celdas[celdaIncendiada.getFila()][celdaIncendiada.getColumna()];
            celdaViewIncendiada.agregarEntidad(EntidadView.getExplosion());
            incendiadas.add(celdaViewIncendiada);

        }
    }

    private void crearCeldas() {
        for (int col = 0; col < tablero.getColumnas(); col++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setMinWidth(TAMANIO_CELDA);
            colConstraints.setMaxWidth(Double.MAX_VALUE);
            colConstraints.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(colConstraints);
        }

        for (int fil = 0; fil < tablero.getFilas(); fil++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(TAMANIO_CELDA);
            rowConstraints.setMaxHeight(Double.MAX_VALUE);
            rowConstraints.setVgrow(Priority.ALWAYS);
            getRowConstraints().add(rowConstraints);
        }

        for (int fil = 0; fil < tablero.getFilas(); fil++) {
            for (int col = 0; col < tablero.getColumnas(); col++) {
                boolean esColorClaro = (fil + col) % 2 == 0;
                String colorCelda = esColorClaro ? COLOR_CLARO : COLOR_OSCURO;
                CeldaView celda = new CeldaView(fil, col);
                celda.setStyle("-fx-background-color: " + colorCelda);
                this.celdas[fil][col] = celda;
                add(celda, col, fil);
            }
        }
    }

    public void vaciarCeldas() {
        jugador.eliminarEntidad();

        for (CeldaView recolectable : celdaViewRecolectables) {
            recolectable.eliminarEntidad();
        }

        celdaViewRecolectables.clear();

        for (CeldaView enemigo : ocupadas) {
            enemigo.eliminarEntidad();
        }

        ocupadas.clear();

        for (CeldaView incendiada : incendiadas) {
            incendiada.eliminarEntidad();
        }

        incendiadas.clear();
    }
}
