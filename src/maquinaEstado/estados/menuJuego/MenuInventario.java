package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import herramientas.MedidorStrings;
import inventario.Objeto;
import inventario.RegistroObjetos;
import inventario.consumibles.Consumibles;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import principal.Constantes;
import principal.GestorPrincipal;
import principal.herramientas.EscaladorElementos;

public class MenuInventario extends SeccionMenu {

    private final EstructuraMenu estructuraMenu;

    final Rectangle panelObjetos = new Rectangle(em.fondo.x + margenGeneral,
            em.fondo.y + margenGeneral,
            em.fondo.width - margenGeneral * 2, em.fondo.height - margenGeneral * 2);

    final Rectangle tituloPanelObjetos = new Rectangle(panelObjetos.x, panelObjetos.y, panelObjetos.width, 24);

    // Objeto que se seleciona para equipar
    Objeto objetoSeleccionado = null;

    public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);
        this.estructuraMenu = em;

    }

    @Override
    public void actualizar() {

        this.actualizarPosicionesMenu();
        this.actualizarSeleccionRaton();
        this.actualizarObjetoSeleccionado();
    }

    private void actualizarPosicionesMenu() {

        if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
            return;
        }

        for (int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {

            final Point puntoInicial = new Point(tituloPanelObjetos.x + margenGeneral,
                    tituloPanelObjetos.y + tituloPanelObjetos.height + margenGeneral);

            final int lado = Constantes.LADO_SPRITE;

            int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();
            Objeto objActual = ElementosPrincipales.inventario.getObjeto(idActual);

            objActual.setPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y, lado, lado));

        }

    }

    private void actualizarSeleccionRaton() {

        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelObjetos))) {

            if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
                return;
            }

            for (Objeto obj : ElementosPrincipales.inventario.getConsumibles()) {

                if (this.clickIzquierdoObjeto(obj)) {

                    if (obj.getCantidad() > 0) {

                        this.objetoSeleccionado = obj;

                    } else {

                        this.objetoSeleccionado = null;
                    }

                }

            }

        }

        if (this.objetoSeleccionado != null) {

            System.out.println("Objeto Seleccionado: " + this.objetoSeleccionado.getNombre());
        }

    }

    private void actualizarObjetoSeleccionado() {

        if (this.objetoSeleccionado != null) {

            this.setPocionFuerzaJugador();
            this.setPocionDefensaJugador();
            this.setPocionVidaJugador();
            this.objetoSeleccionado = null;
            return;

        }

    }

    private void setPocionFuerzaJugador() {

        Consumibles consumible = (Consumibles) RegistroObjetos.getObjeto(this.objetoSeleccionado.getId());

        ElementosPrincipales.jugador.setFuerza(ElementosPrincipales.jugador.getFuerza() + consumible.getFuerzaExtra());
        this.objetoSeleccionado.setCantidad(this.objetoSeleccionado.getCantidad() - 1);

    }

    private void setPocionDefensaJugador() {

        Consumibles consumible = (Consumibles) RegistroObjetos.getObjeto(this.objetoSeleccionado.getId());

        ElementosPrincipales.jugador.setDefensaActual(ElementosPrincipales.jugador.getDefensaActual() + consumible.getDefensaExtra());
        this.objetoSeleccionado.setCantidad(this.objetoSeleccionado.getCantidad() - 1);

    }

    private void setPocionVidaJugador() {

        Consumibles consumible = (Consumibles) RegistroObjetos.getObjeto(this.objetoSeleccionado.getId());

        if (ElementosPrincipales.jugador.getVidaActual() >= 100) {

            ElementosPrincipales.jugador.setVidaActual(100);

        } else if (ElementosPrincipales.jugador.getVidaActual() < 100) {

            ElementosPrincipales.jugador.setVidaActual(ElementosPrincipales.jugador.getVidaActual() + consumible.getVidaExtra());
            this.objetoSeleccionado.setCantidad(this.objetoSeleccionado.getCantidad() - 1);

        }

    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {

        this.dibujarPaneles(g);
        //dibujarObjetosConsumibles(g, em);

    }

    private void dibujarPaneles(final Graphics g) {

        //Dibuja los consumibles
        dibujarPanelObjetos(g, this.panelObjetos, this.tituloPanelObjetos, "Consumibles");

    }

    public void dibujarPanel(Graphics g, Rectangle panel, Rectangle tituloPanel, String nombrePanel) {

        g.setColor(new Color(0x1BA160));

        DibujoDebug.dibujarRectanguloContorno(g, panel);

        DibujoDebug.dibujarRectanguloRelleno(g, tituloPanel);

        g.setColor(Color.white);

        DibujoDebug.dibujarString(g, nombrePanel,
                new Point(tituloPanel.x + tituloPanel.width / 2 - MedidorStrings.medirAnchoPixeles(g, nombrePanel) / 2,
                        tituloPanel.y + tituloPanel.height - MedidorStrings.medirAltoPixeles(g, nombrePanel) / 2));

    }

    private void dibujarPanelObjetos(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);

        //dibujar todos los objetos consumibles
        this.dibujarObjetosConsumibles(g, panel, tituloPanel);
    }

    private void dibujarObjetosConsumibles(final Graphics g, final Rectangle panelObjetos, final Rectangle tituloPanel) {

        if (ElementosPrincipales.inventario.getConsumibles().isEmpty()) {
            return;
        }

        final Point puntoInicial = new Point(tituloPanel.x + margenGeneral, tituloPanel.y + tituloPanel.height + margenGeneral);
        final int lado = Constantes.LADO_SPRITE;

        Iterator<Objeto> iterador = ElementosPrincipales.inventario.getConsumibles().iterator();

        while (iterador.hasNext()) {
            Objeto objActual = iterador.next();

            if (objActual.getCantidad() > 0) {

                DibujoDebug.dibujarImagen(g, objActual.getSprite().getImagen(),
                        objActual.getPosicionMenu().x, objActual.getPosicionMenu().y);

                DibujoDebug.dibujarRectanguloContorno(g, objActual.getPosicionMenu().x, objActual.getPosicionMenu().y,
                        objActual.getPosicionFlotante().width + lado,
                        objActual.getPosicionFlotante().height + lado, Color.red);

            } else if (objActual.getCantidad() <= 0) {

                iterador.remove(); // ELminando el objeto que se acaba del inventario
            }

        }

        for (int i = 0; i < ElementosPrincipales.inventario.getConsumibles().size(); i++) {

            int idActual = ElementosPrincipales.inventario.getConsumibles().get(i).getId();

            Objeto objActual = ElementosPrincipales.inventario.getObjeto(idActual);

            // Dibujando el cuadro de la cantidad del objeto
            g.setColor(new Color(0xededed));

            String texto = "";
            if (objActual.getCantidad() < 10) {

                texto = "0" + objActual.getCantidad();

                if (objActual.getCantidad() <= 0) {
                    texto = "0";
                }

            } else {
                texto = "" + objActual.getCantidad();
            }

            if (objActual.getCantidad() > 0) {

                DibujoDebug.dibujarRectanguloRelleno(g, puntoInicial.x + i * (lado + margenGeneral) + lado - 12,
                        puntoInicial.y + 32 - 8, 12, 8);

                g.setColor(Color.black);

                g.setFont(g.getFont().deriveFont(9f));

                // texto de la cantidad del obj
                DibujoDebug.dibujarString(g, texto,
                        puntoInicial.x + i * (lado + margenGeneral) + lado - MedidorStrings.medirAnchoPixeles(g, texto),
                        puntoInicial.y + 31);

            }

        }

        g.setFont(g.getFont().deriveFont(12f));

        // Dibuja al objeto enxima del puntero del mouse
        /*if (this.objetoSeleccionado != null) {

            DibujoDebug.dibujarImagen(g, this.objetoSeleccionado.getSprite().getImagen(),
                    new Point(this.objetoSeleccionado.getPosicionFlotante().x,
                            this.objetoSeleccionado.getPosicionFlotante().y));

        }*/
    }

    private boolean clickIzquierdoObjeto(Objeto objeto) {

        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(objeto.getPosicionMenu()))
                && GestorPrincipal.sd.getRaton().isClickIzquierdo()) {

            return true;

        }

        return false;

    }

    private boolean clickDerechoObjeto(Objeto objeto) {

        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(objeto.getPosicionMenu()))
                && GestorPrincipal.sd.getRaton().isClickDerecho()) {

            return true;

        }

        return false;

    }

}
