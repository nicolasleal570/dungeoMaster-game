package gestorMapas;

import java.awt.Point;

public class SalidasMapa {

    protected String mapaActual, siguienteMapa;
    protected Point posicionSalida;

    public SalidasMapa(String mapaActual, String siguienteMapa, Point posicionSalida) {
        this.mapaActual = mapaActual;
        this.siguienteMapa = siguienteMapa;
        this.posicionSalida = posicionSalida;
    }

    public String getMapaActual() {
        return mapaActual;
    }

    public String getSiguienteMapa() {
        return siguienteMapa;
    }

    public Point getPosicionSalida() {
        return posicionSalida;
    }

}
