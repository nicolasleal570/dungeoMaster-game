package inventario.equipables;

import entes.Jugador;
import entes.enemigos.Enemigo;
import herramientas.ElementosPrincipales;
import inventario.Objeto;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import sprites.HojaSprites;
import sprites.Sprite;

public abstract class Arma extends Objeto {

    public static HojaSprites hojaArmas = new HojaSprites("/recursos/hojaObjetos/armas.png", 32, false);

    protected int ataqueMinimo;
    protected int ataqueMaximo;

    protected boolean automatica;
    protected boolean penetrante;
    protected double ataquePorSegundo; // Cadencia
    protected int actualizacionParaSiguienteAtaque; // Cuantas actualizaciones deben pasar entre un disparo y otro

    public Arma(int id, String nombre, String descripcion, final int ataqueMinimo, final int ataqueMaximo, final boolean automatica,
            final boolean penetrante, final double ataquePorSegundo) {
        super(id, nombre, descripcion);

        this.ataqueMinimo = ataqueMinimo;
        this.ataqueMaximo = ataqueMaximo;

        this.automatica = automatica;
        this.penetrante = penetrante;
        this.ataquePorSegundo = ataquePorSegundo;
        this.actualizacionParaSiguienteAtaque = 0;

    }

    public void actualizar() {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            this.actualizacionParaSiguienteAtaque--;
        }

    }

    public void atacar(final ArrayList<Enemigo> enemigos) {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            return;
        }
        this.actualizacionParaSiguienteAtaque = (int) (this.ataquePorSegundo * 60); // Fijando las actualizacion cada 1 segundo

        if (enemigos.isEmpty()) {
            return;
        }

        double ataqueActual = this.getAtaqueMedio();

        for (Enemigo enemigo : enemigos) {

            if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma1() instanceof Desarmado) {
                ataqueActual += (ElementosPrincipales.jugador.getFuerza() / 100);
            }

            enemigo.perderVidaEnemigo(ataqueActual);

        }

    }

    public abstract ArrayList<Rectangle> getAlcance(final Jugador jugador);

    @Override
    public Sprite getSprite() {
        return this.hojaArmas.getSprite(this.id - 20);
    }

    public int getAtaqueMedio() {

        Random r = new Random();

        return r.nextInt(this.ataqueMaximo - this.ataqueMinimo) + this.ataqueMaximo;
    }

    public boolean isAutomatica() {
        return automatica;
    }

    public boolean isPenetrante() {
        return penetrante;
    }

}
