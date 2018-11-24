package sprites;

import herramientas.CargadorRecursos;
import java.awt.image.BufferedImage;

public class HojaSprites {

    final private int anchoHojaEnPixeles, altoHojaEnPixeles, anchoHojaEnSprites, altoHojaEnSprites;
    final private int anchoSprites, altoSprites;

    final private Sprite[] sprite;

    public HojaSprites(String ruta, final int sizeSprite, final boolean hojaOpaca) {

        final BufferedImage imagen;

        if (hojaOpaca) {
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }

        this.anchoHojaEnPixeles = imagen.getWidth();
        this.altoHojaEnPixeles = imagen.getHeight();

        this.anchoHojaEnSprites = this.anchoHojaEnPixeles / sizeSprite;
        this.altoHojaEnSprites = this.altoHojaEnPixeles / sizeSprite;

        this.anchoSprites = sizeSprite;
        this.altoSprites = sizeSprite;

        this.sprite = new Sprite[this.anchoHojaEnSprites * this.altoHojaEnSprites];

        this.rellenarSpritesDesdeImagen(imagen);

    }

    public HojaSprites(String ruta, final int anchoSprites, final int altoSprites, final boolean hojaOpaca) {

        final BufferedImage imagen;

        if (hojaOpaca) {
            imagen = CargadorRecursos.cargarImagenCompatibleOpaca(ruta);
        } else {
            imagen = CargadorRecursos.cargarImagenCompatibleTranslucida(ruta);
        }

        this.anchoHojaEnPixeles = imagen.getWidth();
        this.altoHojaEnPixeles = imagen.getHeight();

        this.anchoHojaEnSprites = this.anchoHojaEnPixeles / anchoSprites;
        this.altoHojaEnSprites = this.altoHojaEnPixeles / altoSprites;

        this.anchoSprites = anchoSprites;
        this.altoSprites = altoSprites;

        this.sprite = new Sprite[this.anchoHojaEnSprites * this.altoHojaEnSprites];

        this.rellenarSpritesDesdeImagen(imagen);

    }

    private void rellenarSpritesDesdeImagen(BufferedImage imagen) {
        for (int y = 0; y < this.altoHojaEnSprites; y++) {
            for (int x = 0; x < this.anchoHojaEnSprites; x++) {

                final int posX = x * this.anchoSprites;
                final int posY = y * this.altoSprites;

                this.sprite[x + y * this.anchoHojaEnSprites] = new Sprite(imagen.getSubimage(posX, posY, this.anchoSprites, this.altoSprites));

            }
        }
    }

    public Sprite getSprite(final int indice) {
        return this.sprite[indice];
    }

    public Sprite getSprite(final int x, final int y) {
        return this.sprite[x + y * this.anchoHojaEnSprites];
    }

}
