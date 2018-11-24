package inventario;

import herramientas.CargadorRecursos;
import herramientas.DibujoDebug;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ContenedorObjetos {

    private static final BufferedImage sprite = CargadorRecursos.cargarImagenCompatibleTranslucida("/recursos/hojaObjetos/saco.png");

    private Point posicion;
    private Objeto[] objetos;

    public ContenedorObjetos(final Point posicion, final int[] objetos, final int[] cantidades) {

        this.posicion = posicion;
        this.objetos = new Objeto[objetos.length];

        for (int i = 0; i < objetos.length; i++) {

            this.objetos[i] = RegistroObjetos.getObjeto(objetos[i]);
            this.objetos[i].incrementarCantidad(cantidades[i]);

        }

    }

    public void dibujar(Graphics g, final int puntoX, final int puntoY) {

        DibujoDebug.dibujarImagen(g, sprite, puntoX, puntoY);

    }

    public Point getPosicion() {
        return posicion;
    }

    public static BufferedImage getSprite() {
        return sprite;
    }

    public Objeto[] getObjetos() {
        return objetos;
    }

}
