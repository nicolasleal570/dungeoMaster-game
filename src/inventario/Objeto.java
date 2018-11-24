package inventario;

import java.awt.Rectangle;
import sprites.Sprite;

public abstract class Objeto {

    protected final int id;
    protected final String nombre;
    protected final String descripcion;

    protected int cantidad;
    protected int cantidadMaxima;

    protected Rectangle posicionMenu;
    protected Rectangle posicionFlotante;

    public Objeto(final int id, final String nombre, final String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;

        this.cantidad = 0;
        this.cantidadMaxima = 10;

        this.posicionMenu = new Rectangle(0, 0, 0, 0);
        this.posicionFlotante = new Rectangle(0, 0, 0, 0);

    }

    public Objeto(final int id, final String nombre, final String descripcion, final int cantidad) {
        this(id, nombre, descripcion);

        if (cantidad <= this.cantidadMaxima) {

            this.cantidad = cantidad;
        }

    }

    public boolean incrementarCantidad(final int incremento) {

        boolean incrementado = false;

        if (this.cantidad + incremento <= this.cantidadMaxima) {
            this.cantidad += incremento;
            incrementado = true;
        }

        return incrementado;

    }

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

    public String getDescripcion() {
        return descripcion;
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

    public Rectangle getPosicionMenu() {
        return posicionMenu;
    }

    public Rectangle getPosicionFlotante() {
        return posicionFlotante;
    }

    public void setPosicionMenu(Rectangle posicionMenu) {
        this.posicionMenu = posicionMenu;
    }

    public void setPosicionFlotante(Rectangle posicionFlotante) {
        this.posicionFlotante = posicionFlotante;
    }

}
