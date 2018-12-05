package gestorMapas.mapas;

import gestorMapas.MapaTiled;

public class RegistroMapas {

    public static MapaTiled getMapa(final String nombreMapa) {

        MapaTiled mapa = null;

        switch (nombreMapa) {

            case "pradera.json":
                mapa = new Pradera("/recursos/hojaMapa/" + nombreMapa);
                break;

            case "desierto.json":
                mapa = new Desierto("/recursos/hojaMapa/" + nombreMapa);
                break;

            case "apocalipsis.json":
                mapa = new Apocalipsis("/recursos/hojaMapa/" + nombreMapa);
                break;

        }

        return mapa;
    }

}
