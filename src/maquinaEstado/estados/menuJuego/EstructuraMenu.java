package maquinaEstado.estados.menuJuego;

import herramientas.Constantes;
import herramientas.DibujoDebug;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EstructuraMenu {

    public final Color colorBannerSuperior;
    public final Color colorBannerLateral;
    public final Color colorFondo;

    public final Rectangle bannerSuperior;
    public final Rectangle bannerLateral;
    public final Rectangle fondo;

    public final int margenHorizontalEtiquetas;
    public final int margenVerticalEtiquetas;
    public final int anchoEtiquetas;
    public final int altoEtiquetas;

    // INVENTARIO
    public EstructuraMenu() {

        this.colorBannerSuperior = new Color(0x1BA160);
        this.colorBannerLateral = new Color(23, 23, 23);
        this.colorFondo = new Color(0x1d1d1d);

        this.bannerSuperior = new Rectangle(0, 0, Constantes.ANCHO_JUEGO, 20);
        this.bannerLateral = new Rectangle(0, this.bannerSuperior.height, 140, Constantes.ALTO_JUEGO - bannerSuperior.height);
        this.fondo = new Rectangle(bannerLateral.width, bannerSuperior.height, Constantes.ANCHO_JUEGO - bannerLateral.width, Constantes.ALTO_JUEGO - bannerSuperior.height);

        this.margenHorizontalEtiquetas = 20; // Pixeles
        this.margenVerticalEtiquetas = 20;
        this.anchoEtiquetas = 100;
        this.altoEtiquetas = 20;

    }

    public void actualizar() {

    }

    // DIBUJANDO EL INVENTARIO
    public void dibujar(Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, this.bannerSuperior, this.colorBannerSuperior);
        DibujoDebug.dibujarRectanguloRelleno(g, this.bannerLateral, this.colorBannerLateral);
        DibujoDebug.dibujarRectanguloRelleno(g, this.fondo, this.colorFondo);

    }

}
