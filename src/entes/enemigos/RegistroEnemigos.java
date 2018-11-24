package entes.enemigos;

import herramientas.ElementosPrincipales;

public class RegistroEnemigos {

    public static Enemigo getEnemigo(final int idEnemigo) {

        Enemigo enemigo = null;

        switch (idEnemigo) {

            case 1:
                enemigo = new Zombie(idEnemigo, "Zombie", 100, 1, 45, 1);
                break;

            case 2:
                enemigo = new Zombie2(idEnemigo, "Zombie 2", 120, 3, 86, 1);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 3:
                enemigo = new Zombie3(idEnemigo, "Zombie 3", 140, 5, 73, 1);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 0) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 4:
                enemigo = new Zombie4(idEnemigo, "Zombie 4", 160, 6, 93, 1);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }

                break;

            case 5:
                enemigo = new Zombie5(idEnemigo, "Zombie 5", 160, 6, 64, 1);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 6:
                enemigo = new Zombie6(idEnemigo, "Zombie 6", 200, 7, 42, 1);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

        }

        return enemigo;
    }

}
