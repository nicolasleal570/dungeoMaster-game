package inventario.equipables;

import entes.Jugador;
import java.awt.Rectangle;
import java.util.ArrayList;
import principal.Constantes;

public class Pistola extends Arma {

    public Pistola(int id, String nombre, String descripcion, int ataqueMinimo, int ataqueMaximo, boolean automatica, boolean penetrante, double ataquePorSegundo) {
        super(id, nombre, descripcion, ataqueMinimo, ataqueMaximo, automatica, penetrante, ataquePorSegundo);
    }

    /* ALCANCE DE LA PISTOLA */
    @Override
    public ArrayList<Rectangle> getAlcance(final Jugador jugador) {

        final ArrayList<Rectangle> alcance = new ArrayList<>();
        final int nAlcance = 2;

        // Rectangulo de alcance
        final Rectangle alcance1 = new Rectangle();

        //Direccion del jugador
        // 0 = abajo, 1 = arriba, 2 = derecha, 3 = izquierda
        if (jugador.getDireccion() == 0 || jugador.getDireccion() == 1) {

            alcance1.width = 1; // Ancho de un pixel
            alcance1.height = nAlcance * Constantes.LADO_SPRITE; // Alto del alcance
            alcance1.x = Constantes.CENTRO_VENTANA_X;

            if (jugador.getDireccion() == 0) {
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9;
            } else if (jugador.getDireccion() == 1) {
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9 - alcance1.height;
            }

        } else if (jugador.getDireccion() == 2 || jugador.getDireccion() == 3) {

            alcance1.width = nAlcance * Constantes.LADO_SPRITE; // Ancho del alcance
            alcance1.height = 1; // Alto de un pixel
            alcance1.y = Constantes.CENTRO_VENTANA_Y - 3;

            if (jugador.getDireccion() == 3) {
                alcance1.x = Constantes.CENTRO_VENTANA_X - alcance1.width;
            } else if (jugador.getDireccion() == 2) {
                alcance1.x = Constantes.CENTRO_VENTANA_X;
            }

        }

        alcance.add(alcance1);

        return alcance;
    }

}
