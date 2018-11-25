package maquinaEstado.estados.menuMuerteJugador;

import graficos.SuperficieDibujo;
import herramientas.Constantes;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import herramientas.MedidorStrings;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import maquinaEstado.EstadoJuego;

public class GestorMuerteJugador implements EstadoJuego {

    private final SuperficieDibujo sd;

    private Rectangle panelBienvenida;

    private final int margenGeneral = 8;

    // VENTANA QUE APARECE CUANDO SE MUERE EL JUGADOR
    public GestorMuerteJugador(SuperficieDibujo sd) {

        this.sd = sd;

        this.panelBienvenida = new Rectangle(2, 2, Constantes.ANCHO_JUEGO, Constantes.ALTO_JUEGO);

    }

    @Override
    public void actualizar() {
    }

    // DIBUJA LAS ESTADISTICAS DEL JUGADOR
    @Override
    public void dibujar(Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, panelBienvenida, new Color(23, 23, 23));

        g.setColor(new Color(0xededed));
        g.setFont(g.getFont().deriveFont(28f));

        int x1 = this.panelBienvenida.width / 2 - MedidorStrings.medirAnchoPixeles(g, "- Dungeon Master Ha Terminado -") / 2;
        int y1 = this.panelBienvenida.y + MedidorStrings.medirAltoPixeles(g, "- Dungeon Master Ha Terminado -") / 2 + margenGeneral;

        DibujoDebug.dibujarString(g, "- Dungeon Master Ha Terminado -", x1, y1); // Titulo

        g.setFont(g.getFont().deriveFont(16f));

        this.dibujarItemListaSubrayado(g, "Jugador: " + ElementosPrincipales.jugador.getNombre(), 0, x1, y1); // Jugador
        this.dibujarItemListaNormal(g, "Experiencia Recogida: " + ElementosPrincipales.jugador.getExperiencia(), 20, x1, y1);
        this.dibujarItemListaNormal(g, "Nivel del Jugador: " + ElementosPrincipales.jugador.getNivel(), 40, x1, y1);
        this.dibujarItemListaNormal(g, "Zombies Muertos: " + ElementosPrincipales.jugador.getEnemigosMuertos(), 60, x1, y1); // Zombies Muertos
        this.dibujarItemListaNormal(g, "Ronda Alcanzada: " + ElementosPrincipales.mapa.getNumeroRonda(), 80, x1, y1); // Ronda de Zombies
        this.dibujarItemListaNormal(g, "Pulsar ESC para salir del juego", 0, x1,
                this.panelBienvenida.height - MedidorStrings.medirAltoPixeles(g, "Pulsar ESC para salir del juego") - margenGeneral * 2);
    }

    private void dibujarItemListaSubrayado(Graphics g, String texto, int margenInferior, int x1, int y1) {

        int x2 = this.panelBienvenida.x + margenGeneral;
        int y2 = this.panelBienvenida.y + y1 + margenGeneral + 20 + margenInferior;

        DibujoDebug.dibujarString(g, texto, x2, y2);

        DibujoDebug.dibujarRectanguloRelleno(g, x2, y2 + 2, MedidorStrings.medirAnchoPixeles(g, texto), 1); // Subrayado

    }

    private void dibujarItemListaNormal(Graphics g, String texto, int margenInferior, int x1, int y1) {

        int x2 = this.panelBienvenida.x + margenGeneral;
        int y2 = this.panelBienvenida.y + y1 + margenGeneral + 20 + margenInferior;

        DibujoDebug.dibujarString(g, texto, x2, y2);

    }
}
