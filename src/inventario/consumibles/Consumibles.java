package inventario.consumibles;

import herramientas.Constantes;
import inventario.Objeto;
import sprites.HojaSprites;
import sprites.Sprite;

public abstract class Consumibles extends Objeto {

    private int fuerzaExtra = 0;
    private int defensaExtra = 0;
    private int vidaExtra = 0;

    public HojaSprites consumibles = new HojaSprites("/recursos/hojaObjetos/consumibles.png", Constantes.LADO_SPRITE, false);

    /**
     *
     * @param id del objeto
     * @param nombre del objeto
     * @param cantidad del objeto
     * @param fuerzaExtra fuerza que se va a sumar
     * @param defensaExtra defensa que se va a sumar
     * @param vidaExtra vida que se va a sumar
     */
    public Consumibles(int id, String nombre, int cantidad, int fuerzaExtra, int defensaExtra, int vidaExtra) {
        super(id, nombre, cantidad);

        this.fuerzaExtra = fuerzaExtra;
        this.defensaExtra = defensaExtra;
        this.vidaExtra = vidaExtra;

    }

    @Override
    public Sprite getSprite() {

        return this.consumibles.getSprite(id);
    }

    public int getFuerzaExtra() {
        return fuerzaExtra;
    }

    public void setFuerzaExtra(int fuerzaExtra) {
        this.fuerzaExtra = fuerzaExtra;
    }

    public int getDefensaExtra() {
        return defensaExtra;
    }

    public void setDefensaExtra(int defensaExtra) {
        this.defensaExtra = defensaExtra;
    }

    public int getVidaExtra() {
        return vidaExtra;
    }

    public void setVidaExtra(int vidaExtra) {
        this.vidaExtra = vidaExtra;
    }

}
