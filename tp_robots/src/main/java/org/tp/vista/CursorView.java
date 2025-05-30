package org.tp.vista;

import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.tp.utilities.Path;

public class CursorView {
    private static final Image CURSOR_IMAGEN = new Image(Path.FLECHA);
    private static final Image CURSOR_CIRCULO = crearImagenCirculo();
    public static final Cursor DEFAULT = Cursor.DEFAULT;
    public static final Cursor FLECHA_DERECHA = crearFlecha(0, 1);
    public static final Cursor FLECHA_IZQUIERDA = crearFlecha(0, -1);
    public static final Cursor FLECHA_ARRIBA = crearFlecha(-1, 0);
    public static final Cursor FLECHA_ABAJO = crearFlecha(1, 0);
    public static final Cursor FLECHA_ARRIBA_DERECHA = crearFlecha(-1, 1);
    public static final Cursor FLECHA_ARRIBA_IZQUIERDA = crearFlecha(-1, -1);
    public static final Cursor FLECHA_ABAJO_DERECHA = crearFlecha(1, 1);
    public static final Cursor FLECHA_ABAJO_IZQUIERDA = crearFlecha(1, -1);
    public static final Cursor CIRCULO = new ImageCursor(CURSOR_CIRCULO, 16, 16);

    public static Cursor crearFlecha(int direccionVertical, int direccionHorizontal) {
        if (direccionVertical == 0 && direccionHorizontal == 0) return CIRCULO;
        ImageView cursorView = new ImageView(CURSOR_IMAGEN);
        cursorView.setViewport(new Rectangle2D(210, 250, 80, 80)); // Recortar la flecha
        cursorView.setFitWidth(40);
        cursorView.setFitHeight(40);

        if (direccionVertical == 0 && direccionHorizontal == -1) {
            cursorView.setRotate(-180);
        }
        if (direccionVertical == 1 && direccionHorizontal == 0) {
            cursorView.setRotate(90);
        }
        if (direccionVertical == -1 && direccionHorizontal == 0) {
            cursorView.setRotate(-90);
        }
        if (direccionVertical == 1 && direccionHorizontal == 1) {
            cursorView.setRotate(45);
        }
        if (direccionVertical == 1 && direccionHorizontal == -1) {
            cursorView.setRotate(135);
        }
        if (direccionVertical == -1 && direccionHorizontal == 1) {
            cursorView.setRotate(-45);
        }
        if (direccionVertical == -1 && direccionHorizontal == -1) {
            cursorView.setRotate(-135);
        }

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image cursorImage = cursorView.snapshot(params, null);
        ImageCursor cursorPersonalizado = new ImageCursor(cursorImage, 16, 16);
        return cursorPersonalizado;
    }

    private static Image crearImagenCirculo() {
        int width = 40;
        int height = 40;

        WritableImage image = new WritableImage(width, height);
        PixelWriter pixelWriter = image.getPixelWriter();

        double centerX = width / 2.0;
        double centerY = height / 2.0;
        double radius = 10;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                if (distance <= radius) {
                    // con v3 controlo el nivel de transparencia
                    pixelWriter.setColor(x, y, new Color(0, 0, 0, 0.3));
                }
            }
        }
        return image;
    }
}
