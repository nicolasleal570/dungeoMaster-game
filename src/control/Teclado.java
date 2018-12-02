package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

/**
 *
 * @author nleal
 */
public class Teclado implements KeyListener {

    // TIPOS DE TECLA
    public Tecla arriba = new Tecla();
    public Tecla abajo = new Tecla();
    public Tecla izquierda = new Tecla();
    public Tecla derecha = new Tecla();

    public boolean inventarioActivo = false;
    public boolean recogiendo = false;
    public boolean atacando = false;
    public boolean entrarPuerta = false;

    public boolean start = false;

    // Se activa cuando se pisa una tecla
    @Override
    public void keyPressed(KeyEvent e) {

        // Switch de todas las teclas del juego para sus diferentes funciones
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

            case KeyEvent.VK_R:
                this.entrarPuerta = true;
                break;

            case KeyEvent.VK_SPACE:
                this.atacando = true;
                break;

            case KeyEvent.VK_Z:
                this.start = true;
                break;

            case KeyEvent.VK_ESCAPE:
                int confirm = JOptionPane.showConfirmDialog(null, "Desea cerrar el juego?");
                if (confirm == 1) {
                    return;
                } else if (confirm == 0) {
                    System.exit(0);
                }

        }

    }

    // TECLA LIBERADA
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

            case KeyEvent.VK_R:
                this.entrarPuerta = false;
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
