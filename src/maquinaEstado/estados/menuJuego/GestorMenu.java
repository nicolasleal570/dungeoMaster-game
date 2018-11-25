package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import java.awt.Graphics;
import java.awt.Rectangle;
import maquinaEstado.EstadoJuego;

public class GestorMenu implements EstadoJuego {

    private final SuperficieDibujo sd;

    private final EstructuraMenu estructuraMenu;

    private final SeccionMenu[] secciones;
    private SeccionMenu seccionActual;

    // GESTIONA AL INVENTARIO PARA QUE SE DIBUJE EN EL CANVAS
    public GestorMenu(final SuperficieDibujo sd) {

        this.sd = sd;

        this.estructuraMenu = new EstructuraMenu();

        this.secciones = new SeccionMenu[2]; // Numero de secciones en el inventario

        final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.bannerLateral.x + estructuraMenu.margenHorizontalEtiquetas,
                estructuraMenu.bannerLateral.y + estructuraMenu.margenVerticalEtiquetas,
                estructuraMenu.anchoEtiquetas,
                estructuraMenu.altoEtiquetas);

        secciones[0] = new MenuInventario("Inventario", etiquetaInventario, this.estructuraMenu);

        final Rectangle etiquetaEquipo = new Rectangle(estructuraMenu.bannerLateral.x + estructuraMenu.margenHorizontalEtiquetas,
                etiquetaInventario.y + etiquetaInventario.height + estructuraMenu.margenVerticalEtiquetas,
                estructuraMenu.anchoEtiquetas,
                estructuraMenu.altoEtiquetas);

        secciones[1] = new MenuEquipo("Equipo", etiquetaEquipo, this.estructuraMenu);

        // Seccion activa por defecto
        seccionActual = secciones[0];

    }

    // ACTUALIZA LAS SECCIONES EN EL INVENTARIO
    @Override
    public void actualizar() {

        /* HACIENDO EL CLICK DEL RATON EN LAS OPCIONES DEL MENU */
        for (int i = 0; i < secciones.length; i++) {

            if (this.sd.getRaton().isClickIzquierdo() && this.sd.getRaton().getRectanguloPosicion().intersects(this.secciones[i].getEtiquetaMenuEscalada())) {

                if (secciones[i] instanceof MenuEquipo) {

                    MenuEquipo seccion = (MenuEquipo) secciones[i];
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }

                }

                this.seccionActual = secciones[i];

            }

        }

        // Seccion Actual activa
        this.seccionActual.actualizar();

    }

    // DIBUJA LAS SECCIONES DEL INVENTARIO
    @Override
    public void dibujar(final Graphics g) {

        this.estructuraMenu.dibujar(g);

        for (int i = 0; i < this.secciones.length; i++) {

            if (seccionActual == secciones[i]) { // COmprobamos la seccion activa del menu

                /* COMPRUEBA SI EL RATON CHOCA CON LA ETIQUETA DEL INVENTARIO */
                if (this.sd.getRaton().getRectanguloPosicion().intersects(secciones[i].getEtiquetaMenuEscalada())) {

                    this.secciones[i].dibujarEtiquetaActivaResaltada(g);

                } else {

                    this.secciones[i].dibujarEtiquetaActiva(g);

                }

            } else {

                if (this.sd.getRaton().getRectanguloPosicion().intersects(secciones[i].getEtiquetaMenuEscalada())) {

                    this.secciones[i].dibujarEtiquetaInactivaResaltada(g);

                } else {

                    this.secciones[i].dibujarEtiquetaInactiva(g);

                }

            }

        }

        this.seccionActual.dibujar(g, sd, this.estructuraMenu);

    }

}
