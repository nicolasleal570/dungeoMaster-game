package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import inventario.Objeto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import principal.Constantes;

public class MenuInventario extends SeccionMenu {

    private final EstructuraMenu em;

    public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);

        this.em = em;

    }

    @Override
    public void actualizar() {

        this.actualizarPosicionesMenu();

    }

    private void actualizarPosicionesMenu() {

        if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
            return;
        }

        for (int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {

            final Point puntoInicial = new Point(em.fondo.x + margenGeneral,
                    em.fondo.y + margenGeneral);

            final int lado = Constantes.LADO_SPRITE;

            int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();

            ElementosPrincipales.inventario.getObjeto(idActual)
                    .setPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y, lado, lado));

        }

    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {

        //this.dibujarSpritesInventario(g, this.em);
        dibujarObjetosConsumibles(g, em);

    }

    private void dibujarElementosInventario(Graphics g, EstructuraMenu em) {

        final Point puntoInicial = new Point(em.fondo.x + 16, em.fondo.y + 16);

        for (int y = 0; y < 8; y++) {

            for (int x = 0; x < 12; x++) { // 12 es el Numero de objetos

                DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + x * (Constantes.LADO_SPRITE + margenGeneral),
                        puntoInicial.y + y * (Constantes.LADO_SPRITE + margenGeneral),
                        Constantes.LADO_SPRITE, Constantes.LADO_SPRITE, Color.red
                );

            }

        }

    }

    private void dibujarObjetosConsumibles(final Graphics g, EstructuraMenu em) {

        if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
            return;
        }

        //final Point puntoInicial = new Point(tituloPanel.x + margenGeneral, tituloPanel.y + tituloPanel.height + margenGeneral);
        final Point puntoInicial = new Point(em.fondo.x + 16, em.fondo.y + 16);
        final int lado = Constantes.LADO_SPRITE;

        for (int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {

            int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();
            Objeto objActual = ElementosPrincipales.inventario.getObjeto(idActual);

            DibujoDebug.dibujarImagen(g, objActual.getSprite().getImagen(),
                    objActual.getPosicionMenu().x, objActual.getPosicionMenu().y);

            g.setColor(Color.white);
            Rectangle leyendaObj = new Rectangle(puntoInicial.x + i * (lado + margenGeneral),
                    puntoInicial.y + lado - 8, 12, 8);
            DibujoDebug.dibujarRectanguloRelleno(g, leyendaObj);

            g.setColor(Color.black);

            String texto = "";
            if (objActual.getCantidad() < 10) {

                texto = "0" + objActual.getCantidad();

            } else {
                texto = "" + objActual.getCantidad();
            }

            g.setColor(new Color(0x1d1d1d));

            g.setFont(g.getFont().deriveFont(9f));

            DibujoDebug.dibujarString(g, texto,
                    puntoInicial.x + i * (lado + margenGeneral),
                    puntoInicial.y + lado - 1);

        }

        g.setFont(g.getFont().deriveFont(12f));

    }

    private void dibujarSpritesInventario(Graphics g, EstructuraMenu em) {

        final Point puntoInicial = new Point(em.fondo.x + 16, em.fondo.y + 16);

        final int lado = 32;

        for (int i = 0; i < ElementosPrincipales.inventario.objetos.size(); i++) {

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.inventario.objetos.get(i).getSprite().getImagen(),
                    puntoInicial.x + i * (Constantes.LADO_SPRITE + margenGeneral),
                    puntoInicial.y);

            g.setColor(Color.white);
            DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + i * (lado + margenGeneral),
                    puntoInicial.y + lado - 8, 12, 8);

            String texto = "" + ElementosPrincipales.inventario.objetos.get(i).getCantidad();

            g.setColor(new Color(0x1d1d1d));

            g.setFont(g.getFont().deriveFont(9f));

            DibujoDebug.dibujarString(g, texto,
                    puntoInicial.x + i * (lado + margenGeneral),
                    puntoInicial.y + lado - 1);

        }
        g.setFont(g.getFont().deriveFont(12f));

    }

}
