package principal;

public class Constantes {

    public static final int LADO_SPRITE = 32;

    public static int ANCHO_JUEGO = 640;
    public static int ALTO_JUEGO = 360;

    public static int ANCHO_PANTALLA_COMPLETA = 1280;
    public static int ALTO_PANTALLA_COMPLETA = 720;
//    public static int ANCHO_PANTALLA_COMPLETA = ANCHO_JUEGO;
//    public static int ALTO_PANTALLA_COMPLETA = ALTO_JUEGO;

    public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;

    public static final int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
    public static final int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

    public static final int MARGEN_X = ANCHO_JUEGO / 2 - LADO_SPRITE / 2;
    public static final int MARGEN_Y = ALTO_JUEGO / 2 - LADO_SPRITE / 2;

}
