package maquinaEstado;

import graficos.SuperficieDibujo;
import java.awt.Graphics;
import maquinaEstado.estados.juego.GestorJuego;
import maquinaEstado.estados.menuJuego.GestorMenu;
import maquinaEstado.estados.menuJuegoPrincipal.GestorMenuPrincipal;
import maquinaEstado.estados.menuMuerteJugador.GestorMuerteJugador;

public class GestorEstados {

    private EstadoJuego[] estados;
    private EstadoJuego estadoActual;

    public GestorEstados(final SuperficieDibujo sd) {

        this.iniciarEstados(sd);
        this.iniciarEstadoActual();

    }

    private void iniciarEstados(final SuperficieDibujo sd) {
        // Añadir otros estados a medida que los creemos
        this.estados = new EstadoJuego[4];

        this.estados[0] = new GestorMenuPrincipal(sd); // Menu Principal de Inicio
        this.estados[1] = new GestorJuego(); // Juego 
        this.estados[2] = new GestorMenu(sd); // Inventario
        this.estados[3] = new GestorMuerteJugador(sd); // Cuando el jugador muere y acaba el juego

    }

    private void iniciarEstadoActual() {
        this.estadoActual = this.estados[0];
    }

    public void actualizar() {
        this.estadoActual.actualizar();
    }

    public void dibujar(final Graphics g) {
        this.estadoActual.dibujar(g);
    }

    public void cambiarEstadoActual(final int nuevoEstado) {
        this.estadoActual = estados[nuevoEstado];
    }

    public EstadoJuego getEstadoActual() {
        return estadoActual;
    }

}
