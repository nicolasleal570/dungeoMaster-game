package control;

import graficos.SuperficieDibujo;
import herramientas.CargadorRecursos;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class Raton extends MouseAdapter {

    private final Cursor cursor;

    private Point posicion;

    private boolean clickIzquierdo;

    private boolean clickDerecho;

    public Raton(final SuperficieDibujo sd) {

        /* CAMBIANDOLE EL ICONO AL CURSOR */
        Toolkit configuracion = Toolkit.getDefaultToolkit();

        BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida("/recursos/iconos/cursor.png");

        Point punta = new Point(0, 0);

        this.cursor = configuracion.createCustomCursor(icono, punta, "Cursor modificado");

        this.posicion = new Point();

        this.actualizarPosicion(sd);

        this.clickIzquierdo = false;
        this.clickDerecho = false;

    }

    public void actualizar(SuperficieDibujo sd) {
        this.actualizarPosicion(sd);
    }

    public void dibujar(Graphics g) {

//        g.drawString("RX: " + this.posicion.getX(), 20, 110);
//        g.drawString("RY: " + this.posicion.getY(), 20, 125);
    }

    private void actualizarPosicion(final SuperficieDibujo sd) {

        Point posicionInicial = MouseInfo.getPointerInfo().getLocation();

        SwingUtilities.convertPointFromScreen(posicionInicial, sd);

        this.posicion.setLocation(posicionInicial.getX(), posicionInicial.getY());

    }

    public Rectangle getRectanguloPosicion() {

        final Rectangle area = new Rectangle(this.posicion.x, this.posicion.y, 10, 10);

        return area;

    }

    public Point getPosicion() {
        return posicion;
    }

    public Cursor getCursor() {
        return cursor;
    }

    // Se activa cuando pulsamos el raton
    @Override
    public void mousePressed(MouseEvent e) {

        // Si esta pisado el boton izquierdo
        if (SwingUtilities.isLeftMouseButton(e)) {

            this.clickIzquierdo = true;

        } else if (SwingUtilities.isRightMouseButton(e)) {

            this.clickDerecho = true;

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        // Si se suelta el boton izquierdo
        if (SwingUtilities.isLeftMouseButton(e)) {

            this.clickIzquierdo = false;

        } else if (SwingUtilities.isRightMouseButton(e)) {

            this.clickDerecho = false;

        }

    }

    public boolean isClickIzquierdo() {
        return clickIzquierdo;
    }

    public boolean isClickDerecho() {
        return clickDerecho;
    }

}
