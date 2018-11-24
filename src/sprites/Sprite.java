package sprites;

import java.awt.image.BufferedImage;

public class Sprite {

    private final BufferedImage imagen;

    private final int width, height;

    public Sprite(BufferedImage imagen) {
        this.imagen = imagen;

        this.width = imagen.getWidth();
        this.height = imagen.getHeight();
    }

    public BufferedImage getImagen() {
        return this.imagen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
