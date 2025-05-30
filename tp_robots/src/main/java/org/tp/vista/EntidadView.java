package org.tp.vista;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.tp.utilities.Path;

public class EntidadView extends ImageView {
    private static final Image IMAGENES = new Image(Path.IMAGENES);
    private static final int IMAGEN_WIDTH = 32;
    private static final int IMAGEN_HEIGHT = 32;

    public static ImageView[] JUGADOR_IMAGENES = new ImageView[]{
            cortarImagen(0),
            cortarImagen(1),
            cortarImagen(2),
            cortarImagen(3),
            cortarImagen(4)
    };

    public static ImageView[] ROBOT1X_IMAGENES = new ImageView[]{
            cortarImagen(5),
            cortarImagen(6),
            cortarImagen(7),
            cortarImagen(8)
    };

    public static ImageView[] ROBOT2X_IMAGENES = new ImageView[]{
            cortarImagen(9),
            cortarImagen(10),
            cortarImagen(11),
            cortarImagen(12)
    };

    private static ImageView cortarImagen(int indice) {
        ImageView view = new ImageView(IMAGENES);
        view.setViewport(new Rectangle2D(indice * IMAGEN_WIDTH, 0, IMAGEN_WIDTH, IMAGEN_HEIGHT));
        view.setFitWidth(20);
        view.setFitHeight(20);
        view.setPreserveRatio(true);
        return view;
    }

    protected static ImageView copiarImageView(ImageView imagen) {
        ImageView copia = new ImageView(imagen.getImage());
        copia.setViewport(imagen.getViewport());
        copia.setFitWidth(imagen.getFitWidth());
        copia.setFitHeight(imagen.getFitHeight());
        copia.setPreserveRatio(true);
        return copia;
    }

    public static ImageView getExplosion() {
        return cortarImagen(13);
    }

    public static ImageView getDiamante() {
        return cortarImagen(14);
    }
}
