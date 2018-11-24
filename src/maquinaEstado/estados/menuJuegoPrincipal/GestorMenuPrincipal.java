package maquinaEstado.estados.menuJuegoPrincipal;

import graficos.SuperficieDibujo;
import herramientas.DibujoDebug;
import herramientas.MedidorStrings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import maquinaEstado.EstadoJuego;
import principal.Constantes;

public class GestorMenuPrincipal implements EstadoJuego {

    private final SuperficieDibujo sd;

    private Rectangle panelBienvenida;

    public GestorMenuPrincipal(SuperficieDibujo sd) {
        this.sd = sd;

        this.panelBienvenida = new Rectangle(2, 2, Constantes.ANCHO_JUEGO - 4, Constantes.ALTO_JUEGO - 4);

    }

    @Override
    public void actualizar() {
    }

    @Override
    public void dibujar(Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, panelBienvenida, new Color(0x1BA160));

        g.setColor(new Color(23, 23, 23));
        g.setFont(g.getFont().deriveFont(32f));

        String text = "Bienvenido a Dungeon Master";

        int x1 = this.panelBienvenida.width / 2 - MedidorStrings.medirAnchoPixeles(g, text) / 2;
        int y1 = this.panelBienvenida.height / 2 - MedidorStrings.medirAltoPixeles(g, text) / 2;

        DibujoDebug.dibujarString(g, text, x1, y1);

        g.setFont(g.getFont().deriveFont(16f));

        String text2 = "Pulza Z para comenzar";
        int x2 = this.panelBienvenida.width / 2 - MedidorStrings.medirAnchoPixeles(g, text) / 2;
        int y2 = this.panelBienvenida.height / 2 - MedidorStrings.medirAltoPixeles(g, text) / 2 + 32;

        DibujoDebug.dibujarString(g, text2, x2, y2);

    }

    public void dibujarStringBienvenida(final Graphics g, final String text, final int margenY) {

        g.setFont(g.getFont().deriveFont(16f));

        int x1 = this.panelBienvenida.width / 2 - MedidorStrings.medirAnchoPixeles(g, text) / 2;
        int y1 = this.panelBienvenida.height / 2 - MedidorStrings.medirAltoPixeles(g, text) / 2 + margenY;

        DibujoDebug.dibujarString(g, text, x1, y1);

    }

}
