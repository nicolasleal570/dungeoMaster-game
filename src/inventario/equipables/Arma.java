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

    /**
     *
     * @param id del objeto
     * @param nombre del objeto
     * @param ataqueMinimo minimo rango de ataque
     * @param ataqueMaximo maximo rango de ataque
     * @param automatica si el arma es automatica o no
     * @param penetrante si es penetrante o no
     * @param ataquePorSegundo ataques por segundo
     */
    public Arma(int id, String nombre, final int ataqueMinimo, final int ataqueMaximo, final boolean automatica,
            final boolean penetrante, final double ataquePorSegundo) {
        super(id, nombre);

        this.ataqueMinimo = ataqueMinimo;
        this.ataqueMaximo = ataqueMaximo;

        this.automatica = automatica;
        this.penetrante = penetrante;
        this.ataquePorSegundo = ataquePorSegundo;
        this.actualizacionParaSiguienteAtaque = 0;

    }

    // ACTUALIZA EL ARMA DURANTE EL JUEGO
    public void actualizar() {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            this.actualizacionParaSiguienteAtaque--;
        }

    }

    // EVALUA LOS ATAQUES DEL ARMA
    public void atacar(final ArrayList<Enemigo> enemigos) {

        if (this.actualizacionParaSiguienteAtaque > 0) {
            return;
        }

        this.actualizacionParaSiguienteAtaque = (int) (this.ataquePorSegundo * 60); // Fijando las actualizacion cada 1 segundo

        if (enemigos.isEmpty()) {
            return;
        }

        double ataqueActual = 0;

        for (Enemigo enemigo : enemigos) {

            if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma() instanceof Desarmado) {

                ataqueActual = this.getAtaqueMedio() + ElementosPrincipales.jugador.getFuerza() / 50;

            } else {

                ataqueActual = this.getAtaqueMedio();

            }

            enemigo.perderVidaEnemigo(ataqueActual);

        }

    }

    // ALCANCE DEL ARMA
    public abstract ArrayList<Rectangle> getAlcance(final Jugador jugador);

    @Override
    public Sprite getSprite() {
        return this.hojaArmas.getSprite(this.id - 20);
    }

    // GETTER DEL ATAQUE MEDIO ENTRE LOS RANGOS DEL ARMA
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
