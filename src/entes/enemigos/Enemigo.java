package entes.enemigos;

import dijkstra.Nodo;
import entes.Jugador;
import herramientas.CalculadoraDistancia;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import principal.Constantes;

public abstract class Enemigo {

    private int idEnemigo;

    private double posicionX;
    private double posicionY;

    private double velocidad;

    private String nombre;

    private int vidaMaxima;
    private float vidaActual;

    private int fuerza;

    private int defensaMaxima;
    private float defensaActual;

    private int nivel;

    private Nodo siguienteNodo;

    public Enemigo(int idEnemigo, String nombre, int vidaMaxima, int fuerza, int defensaMaxima, int nivel) {

        this.idEnemigo = idEnemigo;
        this.nombre = nombre;

        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;

        this.defensaMaxima = defensaMaxima;
        this.defensaActual = defensaMaxima;

        this.fuerza = fuerza;

        this.nivel = nivel;

        this.posicionX = 0;
        this.posicionY = 0;

        this.velocidad = 0.5;
    }

    public void actualizar(ArrayList<Enemigo> enemigos) {

        this.moverHaciaSiguienteNodo(enemigos);

        if (this.jugadorColisionandoContraEnemigo()) {

            ElementosPrincipales.jugador.getVidaActual();
        }
    }

    protected void moverHaciaSiguienteNodo(ArrayList<Enemigo> enemigos) {
        if (siguienteNodo == null) {
            return;
        }
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getAreaPosicional().equals(this.getAreaPosicional())) {
                continue;
            }

            if (enemigo.getAreaPosicional().intersects(siguienteNodo.getAreaPixeles())) {
                return;
            }
        }

        //this.velocidad = 0.5;
        int xSiguienteNodo = siguienteNodo.getPosicion().x * Constantes.LADO_SPRITE;
        int ySiguienteNodo = siguienteNodo.getPosicion().y * Constantes.LADO_SPRITE;

        if (posicionX < xSiguienteNodo) {
            posicionX += velocidad;
        }

        if (posicionX > xSiguienteNodo) {
            posicionX -= velocidad;
        }

        if (posicionY < ySiguienteNodo) {
            posicionY += velocidad;
        }

        if (posicionY > ySiguienteNodo) {
            posicionY -= velocidad;
        }
    }

    public void dibujar(Graphics g, final int puntoX, final int puntoY) {

        if (this.vidaActual <= 0) {
            return;
        }

        this.dibujarBarraVida(g, puntoX, puntoY);
        this.dibujarBarraDefensa(g, puntoX, puntoY);

        g.setColor(Color.red);

        DibujoDebug.dibujarRectanguloContorno(g, this.getArea());

        this.dibujarNivel(g, puntoX, puntoY);

        if (this.armaColisionandoContraEnemigo(ElementosPrincipales.jugador)) {
            DibujoDebug.dibujarRectanguloContorno(g, this.getArea(), Color.GREEN);

        }

    }

    private void dibujarDistancia(Graphics g, final int puntoX, final int puntoY) {

        Point puntoJugador = new Point(ElementosPrincipales.jugador.getPosXInt(),
                ElementosPrincipales.jugador.getPosYInt());

        Point puntoEnemigo = new Point(this.getPosicionXInt(), this.getPosicionYInt());

        Double distancia = CalculadoraDistancia.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);

        DibujoDebug.dibujarString(g, String.format("%.2f", distancia), puntoX, puntoY - 8);

    }

    private void dibujarNivel(Graphics g, final int puntoX, final int puntoY) {

        DibujoDebug.dibujarString(g, "Z-Nivel: " + this.nivel, puntoX, puntoY - 12);

    }

    private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {

        g.setColor(Color.red);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 5, Constantes.LADO_SPRITE * (int) this.vidaActual / this.vidaMaxima, 2);

    }

    private void dibujarBarraDefensa(final Graphics g, final int puntoX, final int puntoY) {

        g.setColor(Color.blue);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 10, Constantes.LADO_SPRITE * (int) this.defensaActual / this.defensaMaxima, 2);

    }

    public void perderVidaEnemigo(double ataqueRecibido) {

        if (this.vidaActual - ataqueRecibido < 0) {
            vidaActual = 0;

        } else {

            if (this.defensaActual > 0) {

                this.defensaActual -= ataqueRecibido;
            }

            if (this.defensaActual <= 0) {

                this.vidaActual -= ataqueRecibido;

            }
        }

    }

    public void setPosicion(double posicionX, double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public int getIdEnemigo() {
        return idEnemigo;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public int getPosicionXInt() {
        return (int) posicionX;
    }

    public int getPosicionYInt() {
        return (int) posicionY;
    }

    public String getNombre() {
        return nombre;
    }

    public float getVidaActual() {
        return vidaActual;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public float getDefensaActual() {
        return defensaActual;
    }

    public void setDefensaActual(float defensaActual) {
        this.defensaActual = defensaActual;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public Rectangle getArea() {

        final int puntoX = (int) this.posicionX - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
        final int puntoY = (int) this.posicionY - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

        return new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

    }

    public Rectangle getAreaPosicional() {
        return new Rectangle((int) this.posicionX, (int) this.posicionY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }

    public void cambiarSiguienteNodo(Nodo nodo) {
        this.siguienteNodo = nodo;
    }

    public Nodo getSiguienteNodo() {
        return this.siguienteNodo;
    }

    public boolean armaColisionandoContraEnemigo(Jugador jugador) {

        boolean chocando = false;

        if (this.getArea().intersects(ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getAlcance(jugador).get(0))) {
            chocando = true;
        }

        return chocando;

    }

    public boolean jugadorColisionandoContraEnemigo() {

        boolean chocando = false;

        if (this.getArea().intersects(ElementosPrincipales.jugador.getArea())) {
            chocando = true;
        }

        return chocando;

    }

}