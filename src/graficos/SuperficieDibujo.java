package graficos;

import control.GestorControles;
import control.Raton;
import herramientas.Constantes;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import maquinaEstado.GestorEstados;

public class SuperficieDibujo extends Canvas{

    private int width, height;

    private Raton raton;

    /**
     * @param width de la ventana
     * @param height de la ventana
     */
    public SuperficieDibujo(int width, int height) {
        this.width = width;
        this.height = height;

        this.raton = new Raton(this);

        this.setCursor(this.raton.getCursor());
        this.setIgnoreRepaint(true); // Redibuja de negro la pantalla cada vez que inicia 
        this.setPreferredSize(new Dimension(width, height));

        this.addKeyListener(GestorControles.teclado);
        this.addMouseListener(raton);

        this.setFocusable(true);
        this.requestFocus();

    }

    // ACTUALIZA AL RATON SOBRE EL CANVAS
    public void actualizar() {
        this.raton.actualizar(this);
    }

    // DIBUJA LAS IMAGENES SOBRE EL CANVAS SEGUN EL ESTADO
    public void dibujar(final GestorEstados ge) {

        BufferStrategy buffer = getBufferStrategy();

        if (buffer == null) {
            createBufferStrategy(4);
            return;
        }

        Graphics2D g = (Graphics2D) buffer.getDrawGraphics(); // Graficos mejorados para pantalla completa

        g.setFont(g.getFont().deriveFont(12f));

        g.setColor(Color.black);
        g.fillRect(0, 0, Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA);

        if (Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {

            g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);

        }

        ge.dibujar(g);

        //g.drawString("FPS: " + GestorPrincipal.getFps(), 20, 50);
        //g.drawString("APS: " + GestorPrincipal.getAps(), 20, 65);
        this.raton.dibujar(g);

        Toolkit.getDefaultToolkit().sync();

        g.dispose();

        buffer.show();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Raton getRaton() {
        return raton;
    }


}
