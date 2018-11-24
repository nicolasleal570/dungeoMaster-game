package herramientas;

import java.awt.Point;

public class CalculadoraDistancia {

    public static double getDistanciaEntrePuntos(final Point p1, final Point p2) {

        //Calcula la raiz cuadrada entre dos puntos
        return Math.sqrt(
                Math.pow((p2.getX() - p1.getX()), 2) // Elevando al cuadrado la operacion de p2 - p1
                + Math.pow((p2.getY() - p1.getY()), 2)
        );

    }

}
