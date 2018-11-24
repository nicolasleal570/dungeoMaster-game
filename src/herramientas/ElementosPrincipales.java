package herramientas;

import entes.Jugador;
import inventario.Inventario;
import mapas.MapaTiled;

public class ElementosPrincipales {

    //public static Mapa mapa = new Mapa("/recursos/hojaMapa/mapa");
    public static MapaTiled mapa = new MapaTiled("/recursos/hojaMapa/mapa-apocaliptico.json");

    public static Jugador jugador = new Jugador();

    public static Inventario inventario = new Inventario();

}
