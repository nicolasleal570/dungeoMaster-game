package mapas;

import java.awt.Rectangle;

public class CapaColisiones extends CapaTiled {

    private Rectangle[] colisionables;

    // CAPA DE LAS COLISIONES DEL MAPA
    public CapaColisiones(int ancho, int alto, int x, int y, Rectangle[] colisionables) {
        super(ancho, alto, x, y);

        this.colisionables = colisionables;
    }

    public Rectangle[] getColisionables() {
        return colisionables;
    }

}
