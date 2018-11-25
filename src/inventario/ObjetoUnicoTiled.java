package inventario;

import java.awt.Point;

public class ObjetoUnicoTiled {

    private Point posicion;
    private Objeto objeto;
    private int cantidad;

    // CREA UN OBJETO UNICO A PARTIR DE LA CLASE OBJETO
    public ObjetoUnicoTiled(Point posicion, Objeto objeto, int cantidad) {
        this.posicion = posicion;
        this.objeto = objeto;
        this.cantidad = cantidad;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

}
