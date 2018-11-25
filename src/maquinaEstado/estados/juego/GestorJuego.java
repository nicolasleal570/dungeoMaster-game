package maquinaEstado.estados.juego;

import herramientas.ElementosPrincipales;
import interfaz_usuario.MenuInferior;
import java.awt.Graphics;
import maquinaEstado.EstadoJuego;

public class GestorJuego implements EstadoJuego {

    MenuInferior menuInferior;

    // GESTOR DEL JUEGO 
    public GestorJuego() {
        this.menuInferior = new MenuInferior();
    }

    // ACTUALIZA AL GESTOR DEL JUEGO PRINCIPA;
    @Override
    public void actualizar() {
        ElementosPrincipales.jugador.actualizar();
        ElementosPrincipales.mapa.actualizar();
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
