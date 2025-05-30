package org.tp.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tp.App;
import org.tp.modelo.Juego;
import org.tp.utilities.Path;

import java.io.IOException;
import java.util.Optional;

public class InicioControlador {
    @FXML
    private TextField txtFilas;
    @FXML
    private TextField txtColumnas;

    @FXML
    public void setDimensiones() {
        try {
            int fil = Integer.parseInt(txtFilas.getText());
            int col = Integer.parseInt(txtColumnas.getText());

            if ((fil < 8 || fil > 24) || (col < 8 || col > 40)) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Advertencia");
                alerta.setHeaderText("Tamaño de tablero no recomendado");
                alerta.setContentText("Tableros menores a 8x8 o mayores a 24x40 pueden causar errores o comportamientos inesperados.");
                ButtonType botonAceptar = new ButtonType("Aceptar");
                ButtonType botonCancelar = new ButtonType("Cancelar");

                alerta.getButtonTypes().setAll(botonAceptar, botonCancelar);

                Optional<ButtonType> resultado = alerta.showAndWait();

                if (resultado.isEmpty() || resultado.get() == botonCancelar) {
                    return; // El usuario canceló
                }
            }

            App.juego = new Juego(fil, col);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.VISTA));
            Scene scene = new Scene(loader.load());
            Stage escenario = App.getEscenario();
            escenario.setScene(scene);
        } catch (NumberFormatException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Entrada no válida");
            error.setContentText("Debés ingresar números enteros válidos.");
            error.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
