package interfaz_usuario;

import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import herramientas.MedidorStrings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import principal.Constantes;
import principal.GestorPrincipal;

public class MenuInferior {

    private Rectangle areaInventario;
    private Rectangle bordeAreaInventario;
    private Rectangle areaTiempo;

    private Color negroDesaturado;
    private Color RojoClaro;
    private Color RojoOscuro;

    public MenuInferior() {

        int altoMenu = 64; //64pixeles

        this.areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);
        this.bordeAreaInventario = new Rectangle(areaInventario.x, areaInventario.y - 1, areaInventario.width, 1);

        this.negroDesaturado = new Color(23, 23, 23); //codigo del color
        this.RojoClaro = new Color(255, 0, 0);
        this.RojoOscuro = new Color(150, 0, 0);

    }

    public void dibujar(final Graphics g) {
        this.dibujarAreaInventario(g);
        this.dibujarBarraVida(g, (int) ElementosPrincipales.jugador.getVidaActual());
        this.dibujarBarraFuerza(g, ElementosPrincipales.jugador.getFuerza());
        this.dibujarBarraDefensa(g, (int) ElementosPrincipales.jugador.getDefensaActual());
        this.dibujarBarraExp(g, ElementosPrincipales.jugador.getExperiencia());
        this.dibujarNivelJugador(g, ElementosPrincipales.jugador.getNivel());
        this.dibujarOrdaActualZombies(g, ElementosPrincipales.mapa.getNumeroRonda());
        this.dibujarTiempoJuego(g, "Tiempo de Juego: " + this.calcularTiempo());
        //this.dibujarRanurasObjetos(g);
    }

    private void dibujarAreaInventario(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario, negroDesaturado);
        DibujoDebug.dibujarRectanguloRelleno(g, bordeAreaInventario, Color.white);

    }

    private void dibujarBarraVida(final Graphics g, final int vida) {
        final int medidaVertical = 4;
        final int anchoTotal = 100; // Vida del jugador
        final int ancho = anchoTotal * vida / anchoTotal;

        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35,
                areaInventario.y + medidaVertical,
                ancho, medidaVertical, RojoClaro);

        DibujoDebug.dibujarRectanguloRelleno(g, areaInventario.x + 35,
                areaInventario.y + medidaVertical * 2,
                ancho, medidaVertical, RojoOscuro);

        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "vida", areaInventario.x + 10, areaInventario.y + medidaVertical * 3);
        DibujoDebug.dibujarString(g, ElementosPrincipales.jugador.getVidaActual() + "%", 145, areaInventario.y + medidaVertical * 3);

    }

    private void dibujarBarraFuerza(final Graphics g, final int fuerza) {
        final int medidaVertical = 4;
        final int anchoTotal = 100;
        final int ancho = anchoTotal * fuerza / anchoTotal;

        final int puntoX = areaInventario.x + 35;

        if (ancho >= 100) {

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX, areaInventario.y + medidaVertical * 4, 100, medidaVertical, new Color(0x007ACC));

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX, areaInventario.y + medidaVertical * 5, 100, medidaVertical, new Color(0x005188));

            g.setColor(Color.WHITE);
            DibujoDebug.dibujarString(g, "fuer", areaInventario.x + 10, areaInventario.y + medidaVertical * 6);
            DibujoDebug.dibujarString(g, fuerza + " pts", anchoTotal + 45, areaInventario.y + medidaVertical * 6);

        } else if (ancho < 100) {

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX, areaInventario.y + medidaVertical * 4, ancho, medidaVertical, new Color(0x007ACC));

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX, areaInventario.y + medidaVertical * 5, ancho, medidaVertical, new Color(0x005188));

            g.setColor(Color.WHITE);
            DibujoDebug.dibujarString(g, "fuer", areaInventario.x + 10, areaInventario.y + medidaVertical * 6);
            DibujoDebug.dibujarString(g, fuerza + " pts", anchoTotal + 45, areaInventario.y + medidaVertical * 6);

        }

    }

    private void dibujarBarraDefensa(final Graphics g, final int defensa) {
        final int medidaVertical = 4;
        final int anchoTotal = 100;
        final int ancho = anchoTotal * defensa / anchoTotal;

        final int puntoX = areaInventario.x + 35;

        DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                areaInventario.y + medidaVertical * 7, ancho, medidaVertical, new Color(0xBD7ACC));
        DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                areaInventario.y + medidaVertical * 8, ancho, medidaVertical, new Color(0xBD2CCC));

        g.setColor(Color.WHITE);
        DibujoDebug.dibujarString(g, "def", areaInventario.x + 10, areaInventario.y + medidaVertical * 9);
        DibujoDebug.dibujarString(g, defensa + "%", anchoTotal + 45, areaInventario.y + medidaVertical * 9);
    }

    private void dibujarBarraExp(final Graphics g, final float exp) {
        final int medidaVertical = 4;
        final int anchoTotal = 100;
        final float ancho = anchoTotal * exp / anchoTotal;

        final int puntoX = areaInventario.x + 35;

        if (ancho >= 100) {

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                    areaInventario.y + medidaVertical * 10, 100, medidaVertical, new Color(0x39BA2C));
            DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                    areaInventario.y + medidaVertical * 11, 100, medidaVertical, new Color(0x23921D));

            g.setColor(Color.WHITE);

            DibujoDebug.dibujarString(g, exp + " pts", anchoTotal + 45, areaInventario.y + medidaVertical * 12);

            DibujoDebug.dibujarString(g, "XP", areaInventario.x + 10, areaInventario.y + medidaVertical * 12);

        } else if (ancho < 100) {

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                    areaInventario.y + medidaVertical * 10, (int) ancho, medidaVertical, new Color(0x39BA2C));

            DibujoDebug.dibujarRectanguloRelleno(g, puntoX,
                    areaInventario.y + medidaVertical * 11, (int) ancho, medidaVertical, new Color(0x23921D));

            g.setColor(Color.WHITE);

            DibujoDebug.dibujarString(g, exp + " pts", anchoTotal + 45, areaInventario.y + medidaVertical * 12);

            DibujoDebug.dibujarString(g, "XP", areaInventario.x + 10, areaInventario.y + medidaVertical * 12);
        }
    }

    private void dibujarNivelJugador(final Graphics g, final int nivel) {

        final int medidaVertical = 4;
        final String txt = "Nivel del Jugador: " + nivel;
        final int xInicial = Constantes.ANCHO_JUEGO - MedidorStrings.medirAnchoPixeles(g, txt) - 10;

        g.setColor(Color.white);
        DibujoDebug.dibujarString(g, txt, xInicial, areaInventario.y + medidaVertical * 3);

    }

    private void dibujarOrdaActualZombies(final Graphics g, final int ronda) {

        final int medidaVertical = 4;
        final String txt = "Ronda de Zombies: " + ronda;
        final int xInicial = Constantes.ANCHO_JUEGO - MedidorStrings.medirAnchoPixeles(g, txt) - 10;
        final int yInicial = areaInventario.y + medidaVertical * 6;

        g.setColor(Color.white);
        DibujoDebug.dibujarString(g, txt, xInicial, yInicial);

    }

    private void dibujarTiempoJuego(Graphics g, final String txt) {

        this.areaTiempo = new Rectangle(0, 0, 140 + MedidorStrings.medirAnchoPixeles(g, txt), 25);

        final int xInicial = this.areaTiempo.x + MedidorStrings.medirAnchoPixeles(g, txt) / 2;
        final int yInicial = this.areaTiempo.y + this.areaTiempo.height - MedidorStrings.medirAltoPixeles(g, txt) / 2 - 2;

        g.setColor(new Color(23, 23, 23));

        DibujoDebug.dibujarRectanguloRelleno(g, this.areaTiempo);
        DibujoDebug.dibujarRectanguloContorno(g, this.areaTiempo.x, this.areaTiempo.y, this.areaTiempo.width, this.areaTiempo.height, Color.white);

        g.setColor(Color.white);
        DibujoDebug.dibujarString(g, txt, xInicial, yInicial);

    }

    private void dibujarRanurasObjetos(final Graphics g) {
        final int anchoRanura = 32;
        final int numerosRanuras = 5;
        final int espaciadoRanura = 5;
        final int anchoTotal = anchoRanura * numerosRanuras + espaciadoRanura * numerosRanuras;
        final int xInicial = Constantes.ANCHO_JUEGO - anchoTotal;
        final int anchoRanuraYEspacio = anchoRanura + espaciadoRanura;

        g.setColor(Color.white);

        for (int i = 0; i < numerosRanuras; i++) { //no entendi min 10 mas o menos 

            int xActual = xInicial + anchoRanuraYEspacio * i;

            Rectangle ranura = new Rectangle(xActual, areaInventario.y + 4, anchoRanura, anchoRanura);

            DibujoDebug.dibujarRectanguloRelleno(g, ranura);
            DibujoDebug.dibujarString(g, "" + (i + 1), xActual + 13, areaInventario.y + 54); //casillas

        }
    }

    private String calcularTiempo() {

        int segundos = GestorPrincipal.getSegundosJuego();
        int minuto = segundos / 60;
        int hora = minuto / 60;

        String txt = hora + ":" + minuto + ":" + segundos;

        return txt;
    }

}
