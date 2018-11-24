package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author nleal
 */
public class Teclado implements KeyListener {

    public Tecla arriba = new Tecla();
    public Tecla abajo = new Tecla();
    public Tecla izquierda = new Tecla();
    public Tecla derecha = new Tecla();

    public boolean inventarioActivo = false;
    public boolean recogiendo = false;
    public boolean atacando = false;

    public boolean start = false;

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_W:
                arriba.teclaPulsada();
                break;

            case KeyEvent.VK_S:
                abajo.teclaPulsada();
                break;
            case KeyEvent.VK_A:
                izquierda.teclaPulsada();
                break;
            case KeyEvent.VK_D:
                derecha.teclaPulsada();
                break;
            case KeyEvent.VK_I:
                inventarioActivo = !inventarioActivo;
                break;

            case KeyEvent.VK_E:
                this.recogiendo = true;
                break;

            case KeyEvent.VK_SPACE:
                this.atacando = true;
                break;

            case KeyEvent.VK_Z:
                this.start = true;
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_W:
                arriba.teclaLiberada();
                break;

            case KeyEvent.VK_S:
                abajo.teclaLiberada();
                break;

            case KeyEvent.VK_A:
                izquierda.teclaLiberada();
                break;

            case KeyEvent.VK_D:
                derecha.teclaLiberada();
                break;

            case KeyEvent.VK_E:
                this.recogiendo = false;
                break;

            case KeyEvent.VK_SPACE:
                this.atacando = false;
                break;

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
