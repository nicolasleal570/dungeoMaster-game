package dijkstra;

import herramientas.Constantes;
import java.awt.Point;
import java.awt.Rectangle;

public class Nodo {

    private Point posicion;
    private double distancia;

    /**
     * @param posicion del zombie
     * @param distancia entre el zombie y el jugador
     */
    public Nodo(final Point posicion, final double distancia) {
        this.posicion = posicion;
        this.distancia = distancia;
    }

    // GETTER
    public Rectangle getAreaPixeles() {
        return new Rectangle(posicion.x * Constantes.LADO_SPRITE, posicion.y * Constantes.LADO_SPRITE,
                Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }

    // GETTER
    public Rectangle getArea() {
        return new Rectangle(posicion.x, posicion.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }

    // GETTER
    public Point getPosicion() {
        return posicion;
    }

    // SETTER
    public void cambiarDistancia(double distancia) {
        this.distancia = distancia;
    }

    // GETTER
    public double getDistancia() {
        return distancia;
    }
}
