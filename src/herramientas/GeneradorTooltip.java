package herramientas;

import java.awt.Point;

public class GeneradorTooltip {

    public static Point generarTooltip(final Point puntoInicial) {

        final int x = puntoInicial.x;
        final int y = puntoInicial.y;

        final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);

        final Point centroCanvasEscalado = new Point(EscaladorElementos.escalarPuntoArriba(centroCanvas).x,
                EscaladorElementos.escalarPuntoArriba(centroCanvas).y);

        final int margenCursor = 5;

        final Point puntoFinal = new Point();

        if (x <= centroCanvasEscalado.x) { // El raton esta a la izquierda de la pantalla

            if (y <= centroCanvasEscalado.y) { // El raton esta arriba de la pantalla

                puntoFinal.x = x + Constantes.LADO_CURSOR + margenCursor;
                puntoFinal.y = y + Constantes.LADO_CURSOR + margenCursor;

            } else { // El raton esta abajo de la pantalla
                puntoFinal.x = x + Constantes.LADO_CURSOR + margenCursor;
                puntoFinal.y = y - Constantes.LADO_CURSOR - margenCursor;
            }

        } else { // El raton esta a la derecha de la pantalla 

            if (y <= centroCanvasEscalado.y) { // El raton esta arriba de la pantalla

                puntoFinal.x = x - Constantes.LADO_CURSOR - margenCursor;
                puntoFinal.y = y + Constantes.LADO_CURSOR + margenCursor;

            } else { // El raton esta abajo de la pantalla

                puntoFinal.x = x - Constantes.LADO_CURSOR - margenCursor;
                puntoFinal.y = y - Constantes.LADO_CURSOR - margenCursor;

            }

        }

        return puntoFinal;
    }

    public static String getPosicionTooltip(final Point puntoInicial) {

        final int x = puntoInicial.x;
        final int y = puntoInicial.y;

        final Point centroCanvas = new Point(Constantes.CENTRO_VENTANA_X, Constantes.CENTRO_VENTANA_Y);

        final Point centroCanvasEscalado = new Point(EscaladorElementos.escalarPuntoArriba(centroCanvas).x,
                EscaladorElementos.escalarPuntoArriba(centroCanvas).y);

        String posicion = "";

        if (x <= centroCanvasEscalado.x) { // El raton esta a la izquierda de la pantalla

            if (y <= centroCanvasEscalado.y) { // El raton esta arriba de la pantalla

                posicion = "no"; // noroeste

            } else { // El raton esta abajo de la pantalla

                posicion = "so"; // Suroeste
            }

        } else { // El raton esta a la derecha de la pantalla 

            if (y <= centroCanvasEscalado.y) { // El raton esta arriba de la pantalla

                posicion = "ne"; // noreste

            } else { // El raton esta abajo de la pantalla

                posicion = "se"; // Sureste

            }

        }

        return posicion;
    } 
    
}
