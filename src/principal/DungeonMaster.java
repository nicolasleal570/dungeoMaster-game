package principal;

import herramientas.Constantes;

public class DungeonMaster {

    public static void main(String[] args) {

        GestorPrincipal gp = new GestorPrincipal("Dungeon Master", Constantes.ANCHO_PANTALLA_COMPLETA, Constantes.ALTO_PANTALLA_COMPLETA); // Resolucion 16:9

        gp.iniciarJuego();
        gp.iniciarBuclePrincipal();

    }

}
