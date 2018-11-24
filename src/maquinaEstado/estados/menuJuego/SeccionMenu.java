package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import principal.Constantes;

public abstract class SeccionMenu {

    protected final String nombreSeccion;
    protected final Rectangle etiquetaMenu;
    protected final EstructuraMenu em;

    protected final int margenGeneral = 8;

    public SeccionMenu(final String nombreSeccion, final Rectangle etiquetaMenu, final EstructuraMenu em) {
        this.nombreSeccion = nombreSeccion;
        this.etiquetaMenu = etiquetaMenu;

        this.em = em;
    }

    public abstract void actualizar();

    public abstract void dibujar(final Graphics g, final SuperficieDibujo sd, EstructuraMenu em);

    public void dibujarEtiquetaInactiva(final Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, this.etiquetaMenu, Color.white);
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

    }

    public void dibujarEtiquetaActiva(final Graphics g) {

        Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);

        DibujoDebug.dibujarRectanguloRelleno(g, this.etiquetaMenu, Color.white);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(0x1BA160));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

    }

    public void dibujarEtiquetaInactivaResaltada(final Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        Rectangle r = new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10);
        DibujoDebug.dibujarRectanguloRelleno(g, r, new Color(23, 23, 23));

        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

    }

    public void dibujarEtiquetaActivaResaltada(Graphics g) {

        Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);

        DibujoDebug.dibujarRectanguloRelleno(g, this.etiquetaMenu, Color.white);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(0x1BA160));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

        Rectangle r = new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5, 5, etiquetaMenu.height - 10);
        DibujoDebug.dibujarRectanguloRelleno(g, r, new Color(23, 23, 23));

        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public Rectangle getEtiquetaMenu() {
        return etiquetaMenu;
    }

    public Rectangle getEtiquetaMenuEscalada() {

        final Rectangle etiquetaEscalada = new Rectangle((int) (etiquetaMenu.x * Constantes.FACTOR_ESCALADO_X),
                (int) (etiquetaMenu.y * Constantes.FACTOR_ESCALADO_Y),
                (int) (etiquetaMenu.width * Constantes.FACTOR_ESCALADO_X),
                (int) (etiquetaMenu.height * Constantes.FACTOR_ESCALADO_Y));

        return etiquetaEscalada;
    }

}
