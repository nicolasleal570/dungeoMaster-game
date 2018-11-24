package inventario.consumibles;

import inventario.Objeto;
import principal.Constantes;
import sprites.HojaSprites;
import sprites.Sprite;

public class Consumibles extends Objeto {

    public HojaSprites consumibles = new HojaSprites("/recursos/hojaObjetos/consumibles.png", Constantes.LADO_SPRITE, false);

    public Consumibles(int id, String nombre, String descripcion, int cantidad) {
        super(id, nombre, descripcion, cantidad);
    }

    public Consumibles(int id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }

    @Override
    public Sprite getSprite() {

        return this.consumibles.getSprite(id);
    }

}
