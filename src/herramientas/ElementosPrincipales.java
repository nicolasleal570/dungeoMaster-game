package herramientas;

import entes.Jugador;
import gestorMapas.MapaTiled;
import gestorMapas.mapas.RegistroMapas;
import inventario.Inventario;

public class ElementosPrincipales {

    //public static Mapa mapa = new Mapa("/recursos/hojaMapa/mapa");
    // MAPA UNICO DEL JUEGO
    public static MapaTiled mapa = RegistroMapas.getMapa("pradera.json"); // Mapa inicial

    // JUGADOR UNICO DEL JUEGO
    public static Jugador jugador = new Jugador();

    // INVENTARIO UNICO DEL JUEGO
    public static Inventario inventario = new Inventario();

}
