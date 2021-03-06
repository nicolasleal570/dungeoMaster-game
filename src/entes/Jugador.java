package entes;

import control.GestorControles;
import herramientas.Constantes;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import inventario.RegistroObjetos;
import inventario.equipables.Arma;
import inventario.equipables.Desarmado;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import sprites.HojaSprites;

public class Jugador {

    private String nombre = null;

    private double posX, posY;

    private int direccion;

    private double velocidad = 1.5;

    private HojaSprites hs;

    private BufferedImage imagenActual;

    private final int anchoJugador = 16;
    private final int altoJugador = 16;

    private int enemigosMuertos = 0;
    private int nivel = 1;
    private float experiencia = 0;
    private int vida = 100;
    private float vidaActual;
    private int fuerza = 25;
    private int defensa = 25;
    private float defensaActual;

    private final Rectangle limiteArriba = new Rectangle(Constantes.CENTRO_VENTANA_X - 8, Constantes.CENTRO_VENTANA_Y, 16, 1);
    private final Rectangle limiteAbajo = new Rectangle(Constantes.CENTRO_VENTANA_X - 8, Constantes.CENTRO_VENTANA_Y + 16, 16, 1);
    private final Rectangle limiteIzquierda = new Rectangle(Constantes.CENTRO_VENTANA_X - 8, Constantes.CENTRO_VENTANA_Y, 1, 16);
    private final Rectangle limiteDerecha = new Rectangle(Constantes.CENTRO_VENTANA_X + 8, Constantes.CENTRO_VENTANA_Y, 1, 16);

    private AlmacenEquipo ae;

    private ArrayList<Rectangle> alcanceActual;

    private int animacion, estado;

    private boolean enMovimiento;

    public Jugador() {

        // Poniendo el nombre del jugador
        while (this.nombre == null) {

            this.nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del jugador");

            if (this.nombre != null) {
                break;
            }

        }

        this.posX = ElementosPrincipales.mapa.getPosicionInicial().x;
        this.posY = ElementosPrincipales.mapa.getPosicionInicial().y;

        this.vidaActual = this.vida;
        this.defensaActual = this.defensa;

        this.animacion = 0;
        this.estado = 0;

        this.direccion = 0;

        this.hs = new HojaSprites("/recursos/hojaPersonaje/2.png", Constantes.LADO_SPRITE, false);

        this.imagenActual = hs.getSprite(0).getImagen();

        this.animacion = 0;
        this.estado = 0;

        this.enMovimiento = false;

        this.ae = new AlmacenEquipo((Arma) RegistroObjetos.getObjeto(39));

        this.alcanceActual = new ArrayList<>();

    }

    // ACTUALIZA AL JUGADOR
    public void actualizar() {
        this.cambiarHojaSprite();
        cambiarAnimacionEstado();
        enMovimiento = false;
        determinarDireccion();
        animar();

        this.actualizarArmas();

    }

    // ACTUALIZA LA UTILIZACION DE LAS ARMAS
    private void actualizarArmas() {

        /*if (this.ae.getArma1() instanceof Desarmado) { // No ataca sin arma
            return;
        }*/
        if (this.ae.getArma() != null) {
            this.calcularAlcanceAtaque();
            ae.getArma().actualizar();
        }

    }

    // CALCULO DEL ALCANCE DEL ARMA
    private void calcularAlcanceAtaque() {

        // Si no tiene pistola
        /*if (this.ae.getArma1() instanceof Desarmado) {
            return;
        }*/
        if (this.ae.getArma() != null) {
            this.alcanceActual = this.ae.getArma().getAlcance(this);
        }

    }

    // CAMBIA LA SKIN SI TIENE UN ARMA EQUIPADA
    private void cambiarHojaSprite() {

        if (this.ae.getArma() != null && !(this.ae.getArma() instanceof Desarmado)) {

            this.hs = new HojaSprites("/recursos/hojaPersonaje/2_1.png", Constantes.LADO_SPRITE, false);

        } else if (this.ae.getArma() == null || this.ae.getArma() instanceof Desarmado) {

            this.hs = new HojaSprites("/recursos/hojaPersonaje/2.png", Constantes.LADO_SPRITE, false);

        }

    }

    // DIBUJA AL JUGADOR 
    public void dibujar(Graphics g) {

        final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

        g.setColor(Color.green);
        g.drawImage(this.imagenActual, centroX, centroY, null);
        this.dibujarNombre(g, centroX, centroY);
        this.dibujarAlcance(g);

        DibujoDebug.dibujarRectanguloContorno(g, this.limiteArriba, Color.green);

    }

    // DIBUJA EL ALCANCE DEL ARMA
    private void dibujarAlcance(Graphics g) {

        g.setColor(Color.red);
        DibujoDebug.dibujarRectanguloRelleno(g, this.alcanceActual.get(0));

    }

    // DIBUJA EL NOMBRE DEL JUGADOR
    private void dibujarNombre(final Graphics g, final int puntoX, final int puntoY) {

        g.setColor(Color.red);
        DibujoDebug.dibujarString(g, this.nombre, puntoX, puntoY - 5);

    }

    // ANIMA AL JUGADOR
    private void cambiarAnimacionEstado() {

        if (this.animacion < 30) {
            this.animacion++;
        } else {
            this.animacion = 0;
        }

        if (this.animacion < 15) {
            this.estado = 1;
        } else {
            this.estado = 2;
        }

    }

    // DETERMINA SI ESTA VIENDO AL NORTE, SUR, ESTE U OESTE
    private void determinarDireccion() {

        final int velX = this.evaluarVelocidadX();
        final int velY = this.evaluarVelocidadY();

        if (velX == 0 && velY == 0) {

            return;

        }

        if ((velX != 0 && velY == 0) || (velX == 0 && velY != 0)) {
            mover(velX, velY);
        } else {

            //izquierda y arriba a la vez
            if (velX == -1 && velY == -1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velX, 0);
                } else {
                    mover(0, velY);
                }
            }

            //izquierda y abajo a la vez 
            if (velX == -1 && velY == 1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velX, 0);
                } else {
                    mover(0, velY);
                }
            }

            // derecha y arriba a la vez 
            if (velX == 1 && velY == -1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velX, 0);
                } else {
                    mover(0, velY);
                }
            }

            //derecha y abajo a la vez
            if (velX == 1 && velY == 1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velX, 0);
                } else {
                    mover(0, velY);
                }
            }

        }

    }

    // MUEVE AL JUGADOR
    private void mover(int velocidadX, int velocidadY) {

        this.enMovimiento = true;

        this.cambiarDireccion(velocidadX, velocidadY);

        if (!this.fueraMapa(velocidadX, velocidadY)) {

            if (velocidadX == -1 && !this.enColisionIzquierda(velocidadX)) {

                this.posX += velocidadX * this.velocidad;
            }

            if (velocidadX == 1 && !this.enColisionDerecha(velocidadX)) {

                this.posX += velocidadX * this.velocidad;
            }

            if (velocidadY == -1 && !this.enColisionArriba(velocidadY)) {
                this.posY += velocidadY * this.velocidad;

            }

            if (velocidadY == 1 && !this.enColisionAbajo(velocidadY)) {

                this.posY += velocidadY * this.velocidad;
            }

        }

    }

    // CALCULA SI EL JUGADOR VA A SALIR DEL MAPA
    private boolean fueraMapa(final int velocidadX, final int velocidadY) {

        int posicionFuturaX = (int) this.posX + velocidadX * (int) this.velocidad;
        int posicionFuturaY = (int) this.posY + velocidadY * (int) this.velocidad;

        final Rectangle bordesMapa = ElementosPrincipales.mapa.getBordes(posicionFuturaX, posicionFuturaY);

        final boolean fuera;

        if (this.limiteArriba.intersects(bordesMapa)
                || this.limiteAbajo.intersects(bordesMapa)
                || this.limiteIzquierda.intersects(bordesMapa)
                || this.limiteDerecha.intersects(bordesMapa)) {

            fuera = false; // estamos dentro del mapa

        } else {
            fuera = true;
        }

        return fuera;
    }

    // COLISIONES SUPERIORES
    private boolean enColisionArriba(final int velocidadY) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.velocidad + 3 * (int) this.velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            if (this.limiteArriba.intersects(areaFutura)) {
                return true;
            }

        }

        return false;

        /*for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.velocidad + 3 * (int) this.velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (this.limiteArriba.intersects(areaFutura)) {
                return true;
            }

        }

        return false; */
    }

    // COLISIONES INFERIORES
    private boolean enColisionAbajo(final int velocidadY) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.velocidad - 3 * (int) this.velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            if (this.limiteAbajo.intersects(areaFutura)) {
                return true;
            }

        }

        return false;

        /*for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.velocidad - 3 * (int) this.velocidad;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (this.limiteAbajo.intersects(areaFutura)) {
                return true;
            }

        }

        return false;*/
    }

    // COLISIONES LATERAL IZQUIERDO
    private boolean enColisionIzquierda(final int velocidadX) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

            int origenX = area.x + velocidadX * (int) this.velocidad + 3 * (int) this.velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            if (this.limiteIzquierda.intersects(areaFutura)) {
                return true;
            }

        }

        return false;

        /*for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) this.velocidad + 3 * (int) this.velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (this.limiteIzquierda.intersects(areaFutura)) {
                return true;
            }

        }

        return false;*/
    }

    // COLISIONES LATERAL DERECHO
    private boolean enColisionDerecha(final int velocidadX) {

        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionPorActualizacion.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColisionPorActualizacion.get(r);

            int origenX = area.x + velocidadX * (int) this.velocidad - 3 * (int) this.velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            if (this.limiteDerecha.intersects(areaFutura)) {
                return true;
            }

        }

        return false;

        /*for (int r = 0; r < ElementosPrincipales.mapa.areasColision.size(); r++) {

            final Rectangle area = ElementosPrincipales.mapa.areasColision.get(r);

            int origenX = area.x + velocidadX * (int) this.velocidad - 3 * (int) this.velocidad;
            int origenY = area.y;

            final Rectangle areaFutura = new Rectangle(origenX, origenY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            if (this.limiteDerecha.intersects(areaFutura)) {
                return true;
            }

        }

        return false;*/
    }

    // CAMBIA LA DIRECCION DEL JUGADOR
    private void cambiarDireccion(int velocidadX, int velocidadY) {

        // Nos movemos a la izquierda
        if (velocidadX == -1) {

            this.direccion = 3;

        } else if (velocidadX == 1) {
            //Nos movemos a la derecha
            this.direccion = 2;

        }

        // Nos movemos arriba
        if (velocidadY == -1) {
            this.direccion = 1;
        } else if (velocidadY == 1) {

            //Nos movemos hacia abajo
            this.direccion = 0;
        }

    }

    // EVALUA LA VELOCIDAD EN X
    private int evaluarVelocidadX() {

        int velocidadX = 0;

        if (GestorControles.teclado.izquierda.isPulsada() && !GestorControles.teclado.derecha.isPulsada()) {

            velocidadX = -1; // nos movemos a la izquierda

        } else if (GestorControles.teclado.derecha.isPulsada() && !GestorControles.teclado.izquierda.isPulsada()) {

            velocidadX = 1; // nos movemos a la derecha 

        }

        return velocidadX;
    }

    // EVALUA LAA VELOCIDAD EN Y 
    private int evaluarVelocidadY() {

        int velocidadY = 0;

        if (GestorControles.teclado.arriba.isPulsada() && !GestorControles.teclado.abajo.isPulsada()) {

            velocidadY = -1;

        } else if (GestorControles.teclado.abajo.isPulsada() && !GestorControles.teclado.arriba.isPulsada()) {

            velocidadY = 1;

        }

        return velocidadY;

    }

    // ANIMA AL PERSONAJE
    private void animar() {

        if (!enMovimiento) {
            this.estado = 0;
            this.animacion = 0;
        }

        this.imagenActual = hs.getSprite(this.direccion, this.estado).getImagen();

    }

    // SETTER
    public void setPosX(double posX) {
        this.posX = posX;
    }

    // SETTER
    public void setPosY(double posY) {
        this.posY = posY;
    }

    // GETTER
    public double getPosXDouble() {
        return posX;
    }

    // GETTER
    public double getPosYDouble() {
        return posY;
    }

    // GETTER
    public int getPosXInt() {
        return (int) posX;
    }

    // GETTER
    public int getPosYInt() {
        return (int) posY;
    }

    // GETTER
    public int getAnchoJugador() {
        return anchoJugador;
    }

    // GETTER
    public int getAltoJugador() {
        return altoJugador;
    }

    // GETTER
    public float getExperiencia() {
        return experiencia;
    }

    // SETTER
    public void setExperiencia(float experiencia) {
        this.experiencia = experiencia;
    }

    // GETTER
    public int getFuerza() {
        return fuerza;
    }

    // SETTER
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    // GETTER
    public AlmacenEquipo getAlmacenEquipo() {
        return ae;
    }

    // GETTER
    public int getDireccion() {
        return this.direccion;
    }

    // GETTER
    public Point getPosicion() {

        return new Point(this.getPosXInt(), this.getPosYInt());

    }

    // GETTER
    public ArrayList<Rectangle> getAlcanceActual() {
        return this.alcanceActual;
    }

    // GETTER
    public int getVida() {
        return vida;
    }

    // GETTER
    public float getVidaActual() {
        return vidaActual;
    }

    // SETTER
    public void setVidaActual(float vidaActual) {
        this.vidaActual = vidaActual;
    }

    // GETTER
    public float getDefensaActual() {
        return defensaActual;
    }

    // SETTER
    public void setDefensaActual(float defensaActual) {
        this.defensaActual = defensaActual;
    }

    // GETTER
    public String getNombre() {
        return nombre;
    }

    // GETTER
    public Rectangle getArea() {

        final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

        return new Rectangle(centroX, centroY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

    }

    // GETTER
    public int getNivel() {
        return nivel;
    }

    // SETTER
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    // GETTER
    public int getEnemigosMuertos() {
        return enemigosMuertos;
    }

    // SETTER
    public void setEnemigosMuertos(int enemigosMuertos) {
        this.enemigosMuertos = enemigosMuertos;
    }

    // CALCULA LA PERDIDA DE VIDA DEL JUGADOR
    public void perderVidaJugador(double ataqueRecibido) {

        if (this.defensaActual > 0) {

            this.defensaActual -= ataqueRecibido;

        } else if (this.defensaActual - ataqueRecibido <= 0) {

            this.defensaActual = 0;
            this.vidaActual -= ataqueRecibido;

        } else if (this.vidaActual - ataqueRecibido <= 0) {

            vidaActual = 0;

        }

    }

    public Rectangle getLimiteArriba() {
        return limiteArriba;
    }

}
