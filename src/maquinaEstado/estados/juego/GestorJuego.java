package maquinaEstado.estados.juego;

import control.GestorControles;
import gestorMapas.mapas.RegistroMapas;
import herramientas.ElementosPrincipales;
import interfaz_usuario.MenuInferior;
import java.awt.Graphics;
import java.awt.Point;
import maquinaEstado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

    private MenuInferior menuInferior;

    // GESTOR DEL JUEGO 
    public GestorJuego() {
        this.menuInferior = new MenuInferior();
    }

    public void reiniciarJuego() {

        final String nombreMapa = ElementosPrincipales.mapa.getSiguienteMapa(); // Nombre del siguiente Mapa

        this.iniciarMapa(nombreMapa); // Reiniciar el mapa

        this.iniciarJugador(ElementosPrincipales.mapa.getPuntoSalida()); // Reinicia la posicion del jugador

    }

    private void iniciarMapa(final String nombreMapa) {
        ElementosPrincipales.mapa = RegistroMapas.getMapa(nombreMapa);
    }

    private void iniciarJugador(Point posicionInicial) {

        ElementosPrincipales.jugador.setPosX(posicionInicial.x);
        ElementosPrincipales.jugador.setPosY(posicionInicial.y);
    }

    // ACTUALIZA AL GESTOR DEL JUEGO PRINCIPAL
    @Override
    public void actualizar() {

        ElementosPrincipales.jugador.actualizar();
        ElementosPrincipales.mapa.actualizar();

        if (ElementosPrincipales.jugador.getLimiteArriba().intersects(ElementosPrincipales.mapa.getZonaSalida())
                && GestorControles.teclado.entrarPuerta) {
            this.reiniciarJuego();
        }
    }

    // DIBUJA LOS ELEMENTOS EN EL JUEGO
    @Override
    public void dibujar(Graphics g) {

        /* DIBUJANDO EL MAPA EN EL CANVAS */
        ElementosPrincipales.mapa.dibujar(g);

        ElementosPrincipales.jugador.dibujar(g);
        this.menuInferior.dibujar(g);

    }

}
