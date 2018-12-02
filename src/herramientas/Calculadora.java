package herramientas;

import java.awt.Point;

public class Calculadora {

    public static double getDistanciaEntrePuntos(final Point p1, final Point p2) {

        //Calcula la raiz cuadrada entre dos puntos
        return Math.sqrt(
                Math.pow((p2.getX() - p1.getX()), 2) // Elevando al cuadrado la operacion de p2 - p1
                + Math.pow((p2.getY() - p1.getY()), 2)
        );

    }
    
    public static int transformarTilePixel(final int tile){
        
        return tile * Constantes.LADO_SPRITE;
        
    }
    
    public static int transformarPixelTile(final int pixel){
        
        return pixel / Constantes.LADO_SPRITE;
        
    }

}
