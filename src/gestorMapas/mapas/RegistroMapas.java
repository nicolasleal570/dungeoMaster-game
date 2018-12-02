/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorMapas.mapas;

import gestorMapas.MapaTiled;

/**
 *
 * @author nleal
 */
public class RegistroMapas {

    public static MapaTiled getMapa(final String nombreMapa) {

        MapaTiled mapa = null;

        switch (nombreMapa) {

            case "dungeon.json":
                mapa = new Home("/recursos/hojaMapa/" + nombreMapa);
                break;

            case "mapa-2.json":
                mapa = new Cuarto("/recursos/hojaMapa/" + nombreMapa);
                break;

        }

        return mapa;
    }

}
