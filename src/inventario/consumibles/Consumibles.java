package inventario.consumibles;

import inventario.Objeto;
import principal.Constantes;
import sprites.HojaSprites;
import sprites.Sprite;

public abstract class Consumibles extends Objeto {

    private int fuerzaExtra = 0;
    private int defensaExtra = 0;
    private int vidaExtra = 0;

    public HojaSprites consumibles = new HojaSprites("/recursos/hojaObjetos/consumibles.png", Constantes.LADO_SPRITE, false);

    public Consumibles(int id, String nombre, String descripcion, int cantidad, int fuerzaExtra, int defensaExtra, int vidaExtra) {
        super(id, nombre, descripcion, cantidad);

        this.fuerzaExtra = fuerzaExtra;
        this.defensaExtra = defensaExtra;
        this.vidaExtra = vidaExtra;

    }

    public Consumibles(int id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
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
