package maquinaEstado.estados.menuJuego;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import herramientas.MedidorStrings;
import inventario.Objeto;
import inventario.equipables.Arma;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import principal.Constantes;
import principal.GestorPrincipal;
import principal.herramientas.EscaladorElementos;

public class MenuEquipo extends SeccionMenu {

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
    final Rectangle etiquetaArma = new Rectangle(tituloPanelEquipo.x + margenGeneral,
            tituloPanelEquipo.y + tituloPanelEquipo.height + margenGeneral,
            tituloPanelEquipo.width - margenGeneral * 2,
            margenGeneral * 2 + MedidorStrings.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "Arma"));

    final Rectangle contenedorArma = new Rectangle(etiquetaArma.x + 1,
            etiquetaArma.y + etiquetaArma.height,
            etiquetaArma.width - 2, Constantes.LADO_SPRITE);

    // Objeto que se seleciona para equipar
    Objeto objetoSeleccionado = null;

    /**
     * CONSTRUCTOR DEL MENU DE EQUIPO
     *
     * @param nombreSeccion
     * @param etiquetaMenu
     * @param em
     */
    public MenuEquipo(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);
    }

    @Override
    public void actualizar() {

        this.actualizarPosicionesMenu();

        this.actualizarSeleccionRaton();

        this.actualizarObjetoSeleccionado();

    }

    private void actualizarPosicionesMenu() {

        if (ElementosPrincipales.inventario.getArmas().isEmpty()) {
            return;
        }

        for (int i = 0; i < ElementosPrincipales.inventario.getArmas().size(); i++) {

            final Point puntoInicial = new Point(tituloPanelObjetos.x + margenGeneral,
                    tituloPanelObjetos.y + tituloPanelObjetos.height + margenGeneral);

            final int lado = Constantes.LADO_SPRITE;

            int idActual = ElementosPrincipales.inventario.getArmas().get(i).getId();
            Objeto objActual = ElementosPrincipales.inventario.getObjeto(idActual);

            objActual.setPosicionMenu(new Rectangle(puntoInicial.x + i * (lado + margenGeneral), puntoInicial.y, lado, lado));

        }

    }

    private void actualizarSeleccionRaton() {

        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelObjetos))) {

            if (ElementosPrincipales.inventario.getArmas().isEmpty()) {
                return;
            }

            for (Objeto obj : ElementosPrincipales.inventario.getArmas()) {

                if (GestorPrincipal.sd.getRaton().isClickIzquierdo()
                        && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(obj.getPosicionMenu()))) {

                    if (obj.getCantidad() > 0) {

                        this.objetoSeleccionado = obj;

                    } else {

                        this.objetoSeleccionado = null;
                    }

                }

            }

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelEquipo))) { // Cuando se va a equipar un arma

            // Poniendo el arma en la ranura de los equipados
            if (this.objetoSeleccionado != null && this.objetoSeleccionado instanceof Arma
                    && GestorPrincipal.sd.getRaton().isClickIzquierdo()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(contenedorArma))) {

                //Equipando el arma
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1((Arma) this.objetoSeleccionado);

                this.objetoSeleccionado.setCantidad(this.objetoSeleccionado.getCantidad() - 1);
                this.objetoSeleccionado = null;

            }

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.panelAtributos))) {

        }

    }

    private void actualizarObjetoSeleccionado() {

        Rectangle posicionRatonR = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (GestorPrincipal.sd.getRaton().isClickDerecho()
                && posicionRatonR.intersects(EscaladorElementos.escalarRectanguloArriba(this.contenedorArma))) {

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
        dibujarPanelObjetos(g, this.panelObjetos, this.tituloPanelObjetos, "Equipables");

        // dibuja las armas equipadas
        dibujarPanelEquipo(g, this.panelEquipo, this.tituloPanelEquipo, "Equipado");

        // dibuja los atributos
        dibujarPanelControles(g, this.panelAtributos, this.tituloPanelAtributos, "Controles");

    }

    private void dibujarObjetosEquipables(final Graphics g, final Rectangle tituloPanel) {

        if (ElementosPrincipales.inventario.getArmas().isEmpty()) {
            return;
        }

        final Point puntoInicial = new Point(tituloPanel.x + margenGeneral, tituloPanel.y + tituloPanel.height + margenGeneral);
        final int lado = Constantes.LADO_SPRITE;

        Iterator<Objeto> iterador = ElementosPrincipales.inventario.getArmas().iterator();

        while (iterador.hasNext()) {
            Objeto objActual = iterador.next();

            if (objActual.getCantidad() > 0) {

                DibujoDebug.dibujarImagen(g, objActual.getSprite().getImagen(),
                        objActual.getPosicionMenu().x, objActual.getPosicionMenu().y);

            } else if (objActual.getCantidad() <= 0) {

                iterador.remove(); // ELminando el objeto que se acaba del inventario
            }

        }

        for (int i = 0; i < ElementosPrincipales.inventario.getArmas().size(); i++) {

            int idActual = ElementosPrincipales.inventario.getArmas().get(i).getId();

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
        this.dibujarObjetosEquipables(g, tituloPanel);
    }

    private void dibujarPanelEquipo(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);

        //Ranura de los equipables
        g.setColor(new Color(0xededed));
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaArma);
        DibujoDebug.dibujarRectanguloContorno(g, this.contenedorArma);

        Point coordenadaImagen = new Point(contenedorArma.x + contenedorArma.width / 2 - Constantes.LADO_SPRITE / 2,
                contenedorArma.y);

        // Dibujo del objeto equipado
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getRectanguloPosicion();

        if (GestorPrincipal.sd.getRaton().isClickDerecho()
                && posicionRaton.intersects(EscaladorElementos.escalarRectanguloArriba(this.contenedorArma))) {

            ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1(null);

        } else {

            if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma1() != null) {

                DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getSprite().getImagen(), coordenadaImagen);

            }

        }

        g.setColor(new Color(23, 23, 23));
        DibujoDebug.dibujarString(g, "Arma", new Point(
                etiquetaArma.x + etiquetaArma.width / 2 - MedidorStrings.medirAnchoPixeles(g, "Arma") / 2,
                etiquetaArma.y + etiquetaArma.height - MedidorStrings.medirAltoPixeles(g, "Arma") / 2 - 5));

        //dibujar todos los objetos equipados
    }

    private void dibujarPanelControles(final Graphics g, final Rectangle panel, final Rectangle tituloPanel, final String nombrePanel) {

        dibujarPanel(g, panel, tituloPanel, nombrePanel);
        dibujarStringClickIzquierdo(g, panel, tituloPanel);
        dibujarStringClickDerecho(g, panel, tituloPanel);

    }

    private void dibujarStringClickIzquierdo(final Graphics g, final Rectangle panel, final Rectangle tituloPanel) {

        String control = "Click Izquierdo: ";
        String funcion = "Escoger Objeto";

        g.setFont(g.getFont().deriveFont(9f));
        DibujoDebug.dibujarString(g, control,
                panel.x + 2, panel.y + tituloPanel.height + MedidorStrings.medirAltoPixeles(g, control));

        DibujoDebug.dibujarString(g, funcion,
                panel.x + 15, panel.y + tituloPanel.height + MedidorStrings.medirAltoPixeles(g, funcion) * 2);

    }

    private void dibujarStringClickDerecho(final Graphics g, final Rectangle panel, final Rectangle tituloPanel) {

        String control = "Click Derecho: ";
        String funcion = "Eliminar Seleccion";

        g.setFont(g.getFont().deriveFont(9f));
        DibujoDebug.dibujarString(g, control,
                panel.x + 2, panel.y + tituloPanel.height + MedidorStrings.medirAltoPixeles(g, control) * 4);

        DibujoDebug.dibujarString(g, funcion,
                panel.x + 15, panel.y + tituloPanel.height + MedidorStrings.medirAltoPixeles(g, funcion) * 5);

    }

    public Objeto getObjetoSeleccionado() {
        return this.objetoSeleccionado;
    }

    public void eliminarObjetoSeleccionado() {
        this.objetoSeleccionado = null;

    }

}
