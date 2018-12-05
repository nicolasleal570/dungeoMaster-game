package inventario;

import herramientas.CargadorRecursos;
import herramientas.DibujoDebug;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ContenedorObjetos {

    private static final BufferedImage sprite = CargadorRecursos.cargarImagenCompatibleTranslucida("/recursos/hojaObjetos/saco.png");

    private final Point posicion;
    private final Objeto[] objetosContenedor;

    public ContenedorObjetos(final Point posicion, final int[] idObjetos, final int[] cantidades) {
        this.posicion = posicion;
        this.objetosContenedor = new Objeto[idObjetos.length];

        for (int i = 0; i < this.objetosContenedor.length; i++) {

            this.objetosContenedor[i] = RegistroObjetos.getObjeto(idObjetos[i]);
            this.objetosContenedor[i].incrementarCantidad(cantidades[i]);

        }

    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {

        DibujoDebug.dibujarImagen(g, this.sprite, puntoX, puntoY);

    }

    public Point getPosicion() {
        return posicion;
    }

    public Objeto[] getObjetosContenedor() {
        return objetosContenedor;
    }

}
