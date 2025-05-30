package org.tp.vista;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.tp.modelo.Animable;

public class CeldaView extends StackPane {
    private int fila;
    private int columna;
    private ImageView entidadImageView;
    private Timeline animacion;

    public CeldaView(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        setPrefSize(TableroView.TAMANIO_CELDA, TableroView.TAMANIO_CELDA);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public void agregarEntidadAnimada(Animable entidadAnimable) {
        if (animacion != null) {
            animacion.stop();
        }

        ImageView[] imagenes = entidadAnimable.getImagenesParaAnimacion();
        ImageView imagen = EntidadView.copiarImageView(imagenes[0]);
        final int[] indiceImagen = {0};
        animacion = new Timeline(
                new KeyFrame(Duration.millis(150), e -> {
                    indiceImagen[0] = (indiceImagen[0] + 1) % imagenes.length;
                    imagen.setImage(imagenes[indiceImagen[0]].getImage());
                    imagen.setViewport(imagenes[indiceImagen[0]].getViewport());
                })
        );
        animacion.setCycleCount(Animation.INDEFINITE);
        imagen.setImage(imagenes[0].getImage());
        imagen.setViewport(imagenes[0].getViewport());
        getChildren().add(imagen);
        animacion.play();
    }

    public void agregarEntidad(ImageView entidad) {
        if (entidadImageView != null) {
            this.getChildren().remove(entidadImageView);
        }
        this.getChildren().add(entidad);
        this.entidadImageView = entidad;
    }

    public void eliminarEntidad() {
        if (animacion != null) {
            animacion.stop();
            animacion = null;
        }
        this.entidadImageView = null;
        getChildren().clear();
    }
}
