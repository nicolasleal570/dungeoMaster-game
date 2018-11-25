package inventario;

import java.awt.Rectangle;
import sprites.Sprite;

public abstract class Objeto {

    protected final int id;
    protected final String nombre;

    protected int cantidad;
    protected int cantidadMaxima;

    protected Rectangle posicionMenu;
    protected Rectangle posicionFlotante;

    /**
     * @param id del objeto
     * @param nombre del objeto
     */
    public Objeto(final int id, final String nombre) {
        this.id = id;
        this.nombre = nombre;

        this.cantidad = 0;
        this.cantidadMaxima = 10;

        this.posicionMenu = new Rectangle(0, 0, 0, 0);
        this.posicionFlotante = new Rectangle(0, 0, 0, 0);

    }

    /**
     * @param id del objeto
     * @param nombre del objeto
     * @param cantidad del objeto
     */
    public Objeto(final int id, final String nombre, final int cantidad) {
        this(id, nombre);

        if (cantidad <= this.cantidadMaxima) {

            this.cantidad = cantidad;
        }

    }

    // INCRMEENTA AL OBJETO CUANDO SE VA A RECOGER
    public boolean incrementarCantidad(final int incremento) {

        boolean incrementado = false;

        if (this.cantidad + incremento <= this.cantidadMaxima) {
            this.cantidad += incremento;
            incrementado = true;
        }

        return incrementado;

    }

    // RECUDE AL OBJETO
    public boolean reducirCantidad(final int reduccion) {

        boolean reducido = false;

        if (this.cantidad - reduccion >= 0) {
            this.cantidad -= reduccion;
            reducido = true;
        }

        return reducido;

    }

    public String getNombre() {
        return nombre;
    }

    public abstract Sprite getSprite();

    public int getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            this.cantidad = 0;
        } else {
            this.cantidad = cantidad;
        }
    }

    // GETTER DE LA POSICION EN EL INVENTARIO
    public Rectangle getPosicionMenu() {
        return posicionMenu;
    }

    // GETTER DE LA POSICION CUANDO EL RATON SELECCIONA AL OBJETO
    public Rectangle getPosicionFlotante() {
        return posicionFlotante;
    }

    // SETTER DE LA POSICION EN EL INVENTARIO
    public void setPosicionMenu(Rectangle posicionMenu) {
        this.posicionMenu = posicionMenu;
    }

    // SETTER DE LA POSICION CON EL MOUSE
    public void setPosicionFlotante(Rectangle posicionFlotante) {
        this.posicionFlotante = posicionFlotante;
    }

}
