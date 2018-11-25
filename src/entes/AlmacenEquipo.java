package entes;

import herramientas.ElementosPrincipales;
import inventario.RegistroObjetos;
import inventario.consumibles.Consumibles;
import inventario.equipables.Arma;

public class AlmacenEquipo {

    private Arma arma;
    private Consumibles consumible;

    /**
     * @param arma equipada
     */
    public AlmacenEquipo(final Arma arma) {

        this.arma = arma;

    }

    // GETTER
    public Arma getArma() {
        if (this.arma == null) {
            return this.arma = (Arma) RegistroObjetos.getObjeto(24); // Devolviendo el arma desarmado
        }
        return this.arma;
    }

    // GETTER
    public Consumibles getConsumible() {
        return this.consumible;
    }

    // SETTER
    public void cambiarArma(final Arma arma) {
        this.arma = arma;
    }

    // SETTER
    public void cambiarConsumible(final Consumibles consumible) {
        this.consumible = consumible;
    }

    // CUANDO EL JUGADOR UTILIZA UNA POCION 
    public void consumir() {

        if (this.consumible != null) {

            float defensa = this.consumible.getDefensaExtra() + ElementosPrincipales.jugador.getDefensaActual();
            int fuerza = this.consumible.getFuerzaExtra() + ElementosPrincipales.jugador.getFuerza();
            float vida = this.consumible.getVidaExtra() + ElementosPrincipales.jugador.getVidaActual();

            /* VALIDANDO LA DEFENSA */
            if (ElementosPrincipales.jugador.getDefensaActual() < 100) {

                ElementosPrincipales.jugador.setDefensaActual(defensa);

            } else if (ElementosPrincipales.jugador.getDefensaActual() >= 100) {

                ElementosPrincipales.jugador.setDefensaActual(100);

            }

            // Estableciendo la fuerza
            ElementosPrincipales.jugador.setFuerza(fuerza);

            /* VALIDANDO LA VIDA */
            if (ElementosPrincipales.jugador.getVidaActual() < 100) {

                ElementosPrincipales.jugador.setVidaActual(vida);

            } else if (ElementosPrincipales.jugador.getVidaActual() >= 100) {

                ElementosPrincipales.jugador.setVidaActual(100);

            }

        }

    }
}
