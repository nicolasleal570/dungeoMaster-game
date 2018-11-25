package mapas;

public abstract class CapaTiled {

    protected int ancho, alto, x, y;

    // CAPA DE LOS TILES DEL MAPA
    public CapaTiled(int ancho, int alto, int x, int y) {
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
    }

}
