package org.tp.vista;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tp.controlador.Controlador;


public class EtiquetasViews {

    TableroView tableroView;
    Label lblNivel;
    Label lblDiamantes;
    Text txtTeletransportacionSegura;
    Label lblDiamanteRecolectado;
    HBox menuBar;

    public EtiquetasViews(TableroView tableroView, Label lblNivel, Label lblDiamantes, Text txtTeletransportacionSegura, Label lblDiamanteRecolectado, HBox menuBar){
        this.tableroView = tableroView;
        this.lblNivel = lblNivel;
        this.lblDiamantes = lblDiamantes;
        this.txtTeletransportacionSegura = txtTeletransportacionSegura;
        this.lblDiamanteRecolectado = lblDiamanteRecolectado;
        this.menuBar = menuBar;
    }

    public void agotarTeletransportacionesSeguras(){
        Platform.runLater(()->{txtTeletransportacionSegura.setText("Agotado!");});

    }

    public void actualizarTextos(int nivelActual, int recolectablesObtenidos, int recursosNivel , int teletransportacionesDisponibles){
        Platform.runLater(()->{
            String nivelAntes = lblNivel.getText();
            String diamantesAntes = lblDiamantes.getText();
            lblNivel.setText("Nivel: " + nivelActual);
            lblDiamantes.setText("Diamantes: " + recolectablesObtenidos + "/" + recursosNivel);
            if (nivelAntes.equals(lblNivel.getText()) && !diamantesAntes.equals(lblDiamantes.getText())) {
                lblDiamanteRecolectado.setText("¡+1 recolectable!");
                lblDiamanteRecolectado.setStyle("-fx-background-color: #333; -fx-text-fill: white; -fx-background-radius: 5; -fx-font-size: 15px;");
                lblDiamanteRecolectado.setOpacity(0);

                FadeTransition fade = new FadeTransition(Duration.seconds(2), lblDiamanteRecolectado);
                fade.setFromValue(1);
                fade.setToValue(0);
                fade.setOnFinished(e -> menuBar.getChildren().remove(lblDiamanteRecolectado));
                fade.play();
            }
            txtTeletransportacionSegura.setText("(Quedan: " + teletransportacionesDisponibles + ")");
        });

    }

    public void mostrarAlertaNivelPasado(int nivelNuevo){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Felicitaciones, has pasado el nivel: " + (nivelNuevo - 1) + " !");
            alert.setHeaderText(null);
            alert.setContentText("Continuar al siguiente nivel: " + nivelNuevo);
            alert.setGraphic(null);
            alert.getDialogPane().setStyle("-fx-background-color: #F0FFF0; -fx-text-fill: white;");  // Cambiar el color de fondo
            alert.showAndWait();
            tableroView.actualizar();
        });
    }

    public void mostrarAlertaJuegoPerdido(Controlador controlador){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Has sido eliminado :(");
            alert.setContentText("Desea iniciar otra partida?");
            alert.getDialogPane().setStyle("-fx-background-color: #FFA07A; -fx-text-fill: black;");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    controlador.volverInicio(new ActionEvent());
                } else if (response == ButtonType.CLOSE) {
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            });
        });
    }

}
