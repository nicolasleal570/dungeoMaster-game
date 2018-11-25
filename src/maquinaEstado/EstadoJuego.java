package maquinaEstado;

import java.awt.Graphics;

public interface EstadoJuego {

    // ACTUALIZA LOS ESTADOS DEL JUEGO
    public void actualizar();

    public void dibujar(final Graphics g);

}
