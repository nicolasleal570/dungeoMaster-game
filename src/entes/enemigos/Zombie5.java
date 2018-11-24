package entes.enemigos;

import herramientas.DibujoDebug;
import java.awt.Graphics;
import principal.Constantes;
import sprites.HojaSprites;

public class Zombie5 extends Enemigo {

    private static HojaSprites hojaZombie;

    public Zombie5(int idEnemigo, String nombre, int vidaMaxima, int fuerza, int defensa, int nivel) {
        super(idEnemigo, nombre, vidaMaxima, fuerza, defensa, nivel);

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
