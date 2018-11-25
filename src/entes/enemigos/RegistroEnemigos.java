package entes.enemigos;

import herramientas.ElementosPrincipales;

public class RegistroEnemigos {

    /**
     *
     * @param idEnemigo tipo del enemigo
     */
    public static Enemigo getEnemigo(final int idEnemigo) {

        Enemigo enemigo = null;

        // DEVUELVE UN ENEMIGO SEGUN EL TIPO QUE SE LE DE
        switch (idEnemigo) {

            case 1:
                enemigo = new Zombie(idEnemigo, "Zombie", 100, 3, 6, 45, 1, 0.6);
                break;

            case 2:
                enemigo = new Zombie2(idEnemigo, "Zombie 2", 120, 4, 8, 86, 1, 0.5);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 3:
                enemigo = new Zombie3(idEnemigo, "Zombie 3", 140, 5, 7, 73, 1, 0.4);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 0) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 4:
                enemigo = new Zombie4(idEnemigo, "Zombie 4", 160, 6, 9, 93, 1, 0.3);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }

                break;

            case 5:
                enemigo = new Zombie5(idEnemigo, "Zombie 5", 160, 6, 8, 64, 1, 0.25);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

            case 6:
                enemigo = new Zombie6(idEnemigo, "Zombie 6", 200, 7, 9, 42, 1, 0.2);

                if (ElementosPrincipales.mapa.getEnemigosMuertos() > 1) {
                    enemigo.setNivel(ElementosPrincipales.mapa.getEnemigosMuertos());
                }
                break;

        }

        return enemigo;
    }

}
