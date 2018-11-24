package mapas;

import java.awt.Rectangle;
import sprites.Sprite;

public class Tile {

    private final Sprite sprite;
    private final int id;
    private boolean solido;

    public Tile(Sprite sprite, int id) {
        this.sprite = sprite;
        this.id = id;
        this.solido = false;
    }

    public Tile(Sprite sprite, int id, boolean solido) {
        this.sprite = sprite;
        this.id = id;
        this.solido = solido;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getId() {
        return id;
    }

    public void setSolido(boolean solido) {
        this.solido = solido;
    }

    public boolean isSolido() {
        return solido;
    }

    public Rectangle getLimites(final int x, final int y) {
        return new Rectangle(x, y, this.sprite.getWidth(), this.sprite.getHeight());
    }

}
