package maquinaEstado.estados.juego;

import herramientas.ElementosPrincipales;
import interfaz_usuario.MenuInferior;
import java.awt.Graphics;
import maquinaEstado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

    MenuInferior menuInferior;

    public GestorJuego() {
        this.menuInferior = new MenuInferior();
    }

    @Override
    public void actualizar() {
        ElementosPrincipales.jugador.actualizar();
        //ElementosPrincipales.mapa.actualizar();
        ElementosPrincipales.mapa.actualizar();
    }

    @Override
    public void dibujar(Graphics g) {
        /* DIBUJANDO EL MAPA EN EL CANVAS */

        //ElementosPrincipales.mapa.dibujar(g);
        ElementosPrincipales.mapa.dibujar(g);

        ElementosPrincipales.jugador.dibujar(g);
        this.menuInferior.dibujar(g);

    }

}
