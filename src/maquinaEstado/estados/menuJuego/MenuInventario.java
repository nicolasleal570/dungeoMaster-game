package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import herramientas.MedidorStrings;
import inventario.Objeto;
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

    //Panel de los objetos
    final Rectangle panelObjetos = new Rectangle(em.fondo.x + margenGeneral,
            em.fondo.y + margenGeneral,
            248, em.fondo.height - margenGeneral * 2);

    final Rectangle tituloPanelObjetos = new Rectangle(panelObjetos.x, panelObjetos.y, panelObjetos.width, 24);

    // Panel del equipo
    final Rectangle panelEquipo = new Rectangle(panelObjetos.x + panelObjetos.width + margenGeneral,
            panelObjetos.y, 88, panelObjetos.height);

    final Rectangle tituloPanelEquipo = new Rectangle(panelEquipo.x, panelEquipo.y, panelEquipo.width, 24);

    // Panel de los atributos
    final Rectangle panelAtributos = new Rectangle(panelEquipo.x + panelEquipo.width + margenGeneral,
            panelEquipo.y, 132, panelEquipo.height);

    final Rectangle tituloPanelAtributos = new Rectangle(panelAtributos.x, panelAtributos.y, panelAtributos.width, 24);

    // Ranura donde se coloca el objeto
    final Rectangle etiquetaConsumible = new Rectangle(tituloPanelEquipo.x + margenGeneral,
            tituloPanelEquipo.y + tituloPanelEquipo.height + margenGeneral,
            tituloPanelEquipo.width - margenGeneral * 2,
            margenGeneral * 2 + MedidorStrings.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "Consumible"));

    final Rectangle contenedorConsumible = new Rectangle(etiquetaConsumible.x + 1,
            etiquetaConsumible.y + etiquetaConsumible.height,
            etiquetaConsumible.width - 2, Constantes.LADO_SPRITE);

    // Objeto que se seleciona para equipar
    Objeto objetoSeleccionado = null;

    public MenuInventario(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);

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

                if (GestorPrincipal.sd.getRaton().isClickIzquierdo()
                        && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(obj.getPosicionMenu()))) {

                    if (obj.getCantidad() > 0) {

                        this.objetoSeleccionado = obj;

                    } else {

                        this.objetoSeleccionado = null;
                    }

                }

            }

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelEquipo))) { // Cuando se va a equipar un Consumible

            // Poniendo el Consumible en la ranura de los equipados
            if (this.objetoSeleccionado != null && this.objetoSeleccionado instanceof Consumibles
                    && GestorPrincipal.sd.getRaton().isClickIzquierdo()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(contenedorConsumible))) {

                //Equipando el Consumible
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarConsumible((Consumibles) this.objetoSeleccionado);

                ElementosPrincipales.jugador.getAlmacenEquipo().consumir(); // Consumiendo la pocion

                this.objetoSeleccionado.setCantidad(this.objetoSeleccionado.getCantidad() - 1);
                this.objetoSeleccionado = null;

            }

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelAtributos))) {

        }

    }

    private void actualizarObjetoSeleccionado() {

        Rectangle posicionRatonR = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (GestorPrincipal.sd.getRaton().isClickDerecho()
                && posicionRatonR.intersects(EscaladorElementos.escalarRectanguloArriba(this.contenedorConsumible))) {

        }

        if (this.objetoSeleccionado != null) {

            if (GestorPrincipal.sd.getRaton().isClickDerecho()) {
                this.objetoSeleccionado = null;
                return;
            }

            Point posicionRaton = EscaladorElementos.escalarPuntoAbajo(GestorPrincipal.sd.getRaton().getPosicion());

            this.objetoSeleccionado.setPosicionFlotante(new Rectangle(posicionRaton.x, posicionRaton.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));

        }

    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd, EstructuraMenu em) {

        this.dibujarPaneles(g);

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

    private void dibujarPaneles(final Graphics g) {

        //Dibuja los equipables
        dibujarPanelObjetos(g, this.panelObjetos, this.tituloPanelObjetos, "Consumibles");

        // dibuja las Consumibles equipadas
        dibujarPanelEquipo(g, this.panelEquipo, this.tituloPanelEquipo, "Para consumir");

        // dibuja los atributos
        dibujarPanelAtributos(g, this.panelAtributos, this.tituloPanelAtributos, "Atributos");

    }

    private void dibujarObjetosEquipables(final Graphics g, final Rectangle panelObjetos, final Rectangle tituloPanel) {

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
        if (this.objetoSeleccionado != null) {

            DibujoDebug.dibujarImagen(g, this.objetoSeleccionado.getSprite().getImagen(),
                    new Point(this.objetoSeleccionado.getPosicionFlotante().x,
                            this.objetoSeleccionado.getPosicionFlotante().y));

        }

    }

    private void dibujarPanelObjetos(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);

        //dibujar todos los objetos equipables
        this.dibujarObjetosEquipables(g, panel, tituloPanel);
    }

    private void dibujarPanelEquipo(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);

        //Ranura de los equipables
        g.setColor(new Color(0xededed));
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaConsumible);
        DibujoDebug.dibujarRectanguloContorno(g, this.contenedorConsumible);

        Point coordenadaImagen = new Point(contenedorConsumible.x + contenedorConsumible.width / 2 - Constantes.LADO_SPRITE / 2,
                contenedorConsumible.y);

        // Dibujo del objeto equipado
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (GestorPrincipal.sd.getRaton().isClickDerecho()
                && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.contenedorConsumible))) {

            ElementosPrincipales.jugador.getAlmacenEquipo().cambiarConsumible(null);

        } else {

            if (ElementosPrincipales.jugador.getAlmacenEquipo().getConsumible() != null) {

                DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getConsumible().getSprite().getImagen(), coordenadaImagen);

            }

        }

        g.setColor(new Color(23, 23, 23));
        DibujoDebug.dibujarString(g, "Consumir", new Point(
                etiquetaConsumible.x + etiquetaConsumible.width / 2 - MedidorStrings.medirAnchoPixeles(g, "Consumir") / 2,
                etiquetaConsumible.y + etiquetaConsumible.height - MedidorStrings.medirAltoPixeles(g, "Consumir") / 2 - 5));

        //dibujar todos los objetos equipados
    }

    private void dibujarPanelAtributos(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);

        //dibujar todos los atributos del objeto
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
