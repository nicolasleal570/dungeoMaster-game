package gestorMapas;

public class CapaSprites extends CapaTiled {

    private int[] sprites;

    // CAPA DE LOS SPRITES DEL MAPA
    public CapaSprites(int ancho, int alto, int x, int y, int[] sprites) {
        super(ancho, alto, x, y);

        this.sprites = sprites;

    }

    public int[] getSprites() {
        return sprites;
    }

}
