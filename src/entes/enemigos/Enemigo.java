package entes.enemigos;

import dijkstra.Nodo;
import entes.Jugador;
import herramientas.Constantes;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/* SUPER CLASE ENEMIGO */
public abstract class Enemigo {

    protected int idEnemigo;

    protected double posicionX;
    protected double posicionY;

    protected double velocidad;

    protected String nombre;

    protected int vidaMaxima;
    protected float vidaActual;

    protected int fuerza;

    protected int defensaMaxima;
    protected float defensaActual;

    protected int ataqueMinimo;
    protected int ataqueMaximo;

    protected double ataquePorSegundo; // Cadencia de ataque
    protected int actualizacionParaSiguienteAtaque; // Cuantas actualizaciones deben pasar entre un disparo y otro

    protected int nivel;

    protected Nodo siguienteNodo;

    /**
     * @param idEnemigo tipo del enemigo
     * @param nombre del enemigo
     * @param vidaMaxima
     * @param ataqueMinimo
     * @param ataqueMaximo
     * @param defensaMaxima
     * @param nivel en el que se encuentra
     * @param ataquePorSegundo velocidad con la que ataca
     */
    public Enemigo(int idEnemigo, String nombre, int vidaMaxima, int ataqueMinimo, int ataqueMaximo, int defensaMaxima, int nivel,
            final double ataquePorSegundo) {

        this.idEnemigo = idEnemigo;
        this.nombre = nombre;

        this.vidaMaxima = vidaMaxima;
        this.vidaActual = vidaMaxima;

        this.defensaMaxima = defensaMaxima;
        this.defensaActual = defensaMaxima;

        this.ataqueMinimo = ataqueMinimo;
        this.ataqueMaximo = ataqueMaximo;
        this.ataquePorSegundo = ataquePorSegundo;
        this.actualizacionParaSiguienteAtaque = 0;

        this.nivel = nivel;

        this.posicionX = 0;
        this.posicionY = 0;

        this.velocidad = 0.6;
    }

    // ACTUALIZA AL ENEMIGO
    public void actualizar(ArrayList<Enemigo> enemigos) {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            this.actualizacionParaSiguienteAtaque--;
        }

        this.moverHaciaSiguienteNodo(enemigos);

        if (this.jugadorColisionandoContraEnemigo()) {

            ElementosPrincipales.jugador.getVidaActual();
        }
    }

    // LO MOVILIZA SEGUN DONDE ESTE
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

    // DIBUJA AL ENEMIGO
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

    // DIBUJA EL NIVEL DEL ENEMIGO
    private void dibujarNivel(Graphics g, final int puntoX, final int puntoY) {

        DibujoDebug.dibujarString(g, "Z-Nivel: " + this.nivel, puntoX, puntoY - 12);

    }

    // DIBUJA LA BARRA DE VIDA
    private void dibujarBarraVida(final Graphics g, final int puntoX, final int puntoY) {

        g.setColor(Color.red);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 5, Constantes.LADO_SPRITE * (int) this.vidaActual / this.vidaMaxima, 2);

    }

    // DIBUJA LA BARRA DE DEFENSA
    private void dibujarBarraDefensa(final Graphics g, final int puntoX, final int puntoY) {

        g.setColor(Color.blue);
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX, puntoY - 10, Constantes.LADO_SPRITE * (int) this.defensaActual / this.defensaMaxima, 2);

    }

    // CALCULA LA PERDIDA DE VIDA DEL ENEMIGO
    public void perderVidaEnemigo(double ataqueRecibido) {

        if (this.defensaActual > 0) {

            this.defensaActual -= ataqueRecibido;

        } else if (this.defensaActual <= 0) {

            this.defensaActual = 0;
            this.vidaActual -= ataqueRecibido;

        } else if (this.vidaActual - ataqueRecibido <= 0) {

            vidaActual = 0;

        }

    }

    // GETTER
    public int getAtaqueMedio() {

        Random r = new Random();

        return r.nextInt(this.ataqueMaximo - this.ataqueMinimo) + this.ataqueMaximo;

    }

    // SETTER
    public void setPosicion(double posicionX, double posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    // GETTER
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

    // SETTER
    public void cambiarSiguienteNodo(Nodo nodo) {
        this.siguienteNodo = nodo;
    }

    public Nodo getSiguienteNodo() {
        return this.siguienteNodo;
    }

    // EVALUA CUANDO UN ARMA TIENE CAPACIDAD DE DARLE AL ENEMIGO
    public boolean armaColisionandoContraEnemigo(Jugador jugador) {

        boolean chocando = false;

        if (this.getArea().intersects(ElementosPrincipales.jugador.getAlmacenEquipo().getArma().getAlcance(jugador).get(0))) {
            chocando = true;
        }

        return chocando;

    }

    // CUANDO EL ENEMIGO CHOCA CONTRA EL ENEMIGO
    public boolean jugadorColisionandoContraEnemigo() {

        boolean chocando = false;

        if (this.getArea().intersects(ElementosPrincipales.jugador.getArea())) {
            chocando = true;
        }

        return chocando;

    }

    // CALCULO DEL ATAQUE DEL ENEMIGO
    public void atacar(Jugador jugador) {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            return;
        }

        this.actualizacionParaSiguienteAtaque = (int) (this.ataquePorSegundo * 30); // Fijando las actualizacion cada 1 segundo

        double ataqueActual = 0;

        ataqueActual = this.getAtaqueMedio();

        jugador.perderVidaJugador(ataqueActual);

    }

}
