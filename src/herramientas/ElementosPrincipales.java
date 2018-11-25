package herramientas;

import entes.Jugador;
import inventario.Inventario;
import mapas.MapaTiled;

public class ElementosPrincipales {

    //public static Mapa mapa = new Mapa("/recursos/hojaMapa/mapa");
    // MAPA UNICO DEL JUEGO
    public static MapaTiled mapa = new MapaTiled("/recursos/hojaMapa/mapa-apocaliptico.json");

    // JUGADOR UNICO DEL JUEGO
    public static Jugador jugador = new Jugador();

    // INVENTARIO UNICO DEL JUEGO
    public static Inventario inventario = new Inventario();

}
