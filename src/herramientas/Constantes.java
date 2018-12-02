package herramientas;

public class Constantes {

    // LADOS DE UN SPRITE
    public static final int LADO_SPRITE = 32;

    // DIMENSIONES REALES DEL JUEGO
    public static int ANCHO_JUEGO = 640;
    public static int ALTO_JUEGO = 360;

    // JUEGO ESCALADO
    public static int ANCHO_PANTALLA_COMPLETA = 1280;
    public static int ALTO_PANTALLA_COMPLETA = 720;

    // FACTOR DE ESCALADO
    public static double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
    public static double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;

    // CENTRO DE LA VENTANA 
    public static final int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
    public static final int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

    // MARGENES DE LA VENTANA
    public static final int MARGEN_X = ANCHO_JUEGO / 2 - LADO_SPRITE / 2;
    public static final int MARGEN_Y = ALTO_JUEGO / 2 - LADO_SPRITE / 2;

    // RUTAS DEL JUEGO
    public static final String RUTA_MAPA_TILED = "/recursos/hojaMapa/dungeon.json";

}
