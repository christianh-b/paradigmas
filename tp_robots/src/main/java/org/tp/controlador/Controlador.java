package org.tp.controlador;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.tp.App;
import org.tp.modelo.*;
import org.tp.utilities.Path;
import org.tp.vista.EtiquetasViews;
import org.tp.vista.CeldaView;
import org.tp.vista.CursorView;
import org.tp.vista.TableroView;

import java.io.IOException;

public class Controlador {
    private Juego juego;
    private TableroView tableroView;
    private EtiquetasViews etiquetasViews;
    int teletransportacionesRealizadas;
    EstadoJuegoController estadoJuegoController;
    NivelController nivelController;

    @FXML
    public GridPane tableroContainer;
    @FXML
    private Label lblNivel;
    @FXML
    private Label lblDiamantes;
    @FXML
    private Text txtTeletransportacionSegura;
    @FXML
    private HBox menuBar;
    @FXML
    private Label lblDiamanteRecolectado;

    public Controlador() {
        this.juego = App.getJuego();
        tableroView = new TableroView(this.juego.getTablero());
        tableroView.setControlador(this);

        teletransportacionesRealizadas = 0;
    }

    public void initialize() {
        tableroContainer.getChildren().add(tableroView);
        etiquetasViews = new EtiquetasViews(tableroView, lblNivel, lblDiamantes, txtTeletransportacionSegura, lblDiamanteRecolectado, menuBar);
        nivelController = new NivelController(juego,this, etiquetasViews);
        estadoJuegoController = new EstadoJuegoController(juego, this, etiquetasViews);
        actualizarTextos();
        GridPane.setHgrow(tableroView, Priority.ALWAYS);
        GridPane.setVgrow(tableroView, Priority.ALWAYS);
    }

    protected void actualizarTextos() {
        int teletransportacionesDisponibles = juego.getNivelActual() - teletransportacionesRealizadas;
        etiquetasViews.actualizarTextos(juego.getNivelActual(), juego.getJugador().getRecolectablesObtenidos(), juego.getRecursosPorNivel(), teletransportacionesDisponibles);
    }

    @FXML
    public void teletransportarAlAzar(ActionEvent actionEvent) {
        new Thread(() -> {
            juego.teletransportarAzar();
            Platform.runLater(() -> {
                tableroView.actualizar();
            });
        }).start();
        actualizarTextos();
    }

    @FXML
    public void esperarRobots(ActionEvent actionEvent) {
        new Thread(() -> {
            for (int i = 0; i < Math.min(tableroView.getColumnCount(), tableroView.getRowCount()); i++) {
                juego.realizarMovimiento(0, 0);

                if (!juego.juegoSigue() || juego.nivelFinalizado()) {
                    Platform.runLater(() -> {
                        tableroView.actualizar();
                        actualizarTextos();
                        juego.reiniciarEstadoNivel();
                    });
                    break;
                }

                Platform.runLater(() -> {
                    tableroView.actualizar();
                    actualizarTextos();
                });

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }

    @FXML
    public void volverInicio(ActionEvent actionEvent) {
        juego.reiniciar();
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(Path.INICIO));
            Scene escenaInicio = new Scene(loader.load());
            App.getEscenario().setScene(escenaInicio);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void teletransportarSeguro(ActionEvent actionEvent) {
        if (teletransportacionesRealizadas >= juego.getNivelActual()) {
            etiquetasViews.agotarTeletransportacionesSeguras();
            return;
        }
        new Thread(() -> {
            juego.teletransportarSeguro();
            Platform.runLater(() -> {
                tableroView.actualizar();
                teletransportacionesRealizadas++;
                actualizarTextos();
            });
        }).start();

    }

    public void setCursor(MouseEvent mouseEntered) {
        CeldaView celdaEvento = (CeldaView) mouseEntered.getSource();
        CeldaView celdaJugador = tableroView.getCeldaViewJugador();
        if (celdaJugador == null) {
            celdaEvento.setCursor(CursorView.DEFAULT);
        }

        int filaJugador = GridPane.getRowIndex(celdaJugador) != null ? GridPane.getRowIndex(celdaJugador) : 0;
        int colJugador = GridPane.getColumnIndex(celdaJugador) != null ? GridPane.getColumnIndex(celdaJugador) : 0;

        int filaMouse = GridPane.getRowIndex(celdaEvento) != null ? GridPane.getRowIndex(celdaEvento) : 0;
        int colMouse = GridPane.getColumnIndex(celdaEvento) != null ? GridPane.getColumnIndex(celdaEvento) : 0;


        int dy = filaMouse - filaJugador;
        if (dy != 0) {
            dy = dy / Math.abs(dy);
        }

        int dx = colMouse - colJugador;
        if (dx != 0) {
            dx = dx / Math.abs(dx);
        }

        celdaEvento.setCursor(CursorView.crearFlecha(dy, dx));
    }

    public void moverJugador(MouseEvent mouseEvent) {
        CeldaView celdaEvento = (CeldaView) mouseEvent.getSource();
        int filaMouse = GridPane.getRowIndex(celdaEvento) != null ? GridPane.getRowIndex(celdaEvento) : 0;
        int colMouse = GridPane.getColumnIndex(celdaEvento) != null ? GridPane.getColumnIndex(celdaEvento) : 0;

        CeldaView celdaJugador = tableroView.getCeldaViewJugador();
        int filaJugador = GridPane.getRowIndex(celdaJugador) != null ? GridPane.getRowIndex(celdaJugador) : 0;
        int colJugador = GridPane.getColumnIndex(celdaJugador) != null ? GridPane.getColumnIndex(celdaJugador) : 0;

        int dx = filaJugador - filaMouse;
        if (dx != 0) {
            dx = dx / -Math.abs(dx);
        }
        final int DX = dx;
        int dy = colJugador - colMouse;
        if (dy != 0) {
            dy = dy / -Math.abs(dy);
        }
        final int DY = dy;
        new Thread(() -> {
            juego.realizarMovimiento(DX, DY);
            Platform.runLater(() -> {
                tableroView.actualizar();
                actualizarTextos();
            });
        }).start();
    }

    public void resetearTeletransportacionesSeguras() {
        teletransportacionesRealizadas = 0;
    }
}
