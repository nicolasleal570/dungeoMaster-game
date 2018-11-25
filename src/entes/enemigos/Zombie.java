package entes.enemigos;

import herramientas.DibujoDebug;
import java.awt.Graphics;
import herramientas.Constantes;
import sprites.HojaSprites;

public class Zombie extends Enemigo {

    private static HojaSprites hojaZombie;

    public Zombie(int idEnemigo, String nombre, int vidaMaxima, int ataqueMinimo, int ataqueMaximo, int defensaMaxima, int nivel,
            final double ataquePorSegundo) {
        super(idEnemigo, nombre, vidaMaxima, ataqueMinimo, ataqueMaximo, defensaMaxima, nivel, ataquePorSegundo);

        if (this.hojaZombie == null) {
            this.hojaZombie = new HojaSprites("/recursos/hojaEnemigos/" + idEnemigo + ".png", Constantes.LADO_SPRITE, false);
        }

    }

    @Override
    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {

        DibujoDebug.dibujarImagen(g, this.hojaZombie.getSprite(0).getImagen(), puntoX, puntoY);

        super.dibujar(g, puntoX, puntoY);

    }

}
