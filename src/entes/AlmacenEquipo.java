package entes;

import herramientas.ElementosPrincipales;
import inventario.RegistroObjetos;
import inventario.consumibles.Consumibles;
import inventario.equipables.Arma;

public class AlmacenEquipo {

    private Arma arma;
    private Consumibles consumible;
    //municion

    public AlmacenEquipo(final Arma arma1) {

        this.arma = arma1;

    }

    public AlmacenEquipo(final Consumibles consumible) {

        this.consumible = consumible;

    }

    public Arma getArma1() {
        if (this.arma == null) {
            return this.arma = (Arma) RegistroObjetos.getObjeto(24); // Devolviendo el arma desarmado
        }
        return this.arma;
    }

    public Consumibles getConsumible() {
        return this.consumible;
    }

    public void cambiarArma1(final Arma arma1) {
        this.arma = arma1;
    }

    public void cambiarConsumible(final Consumibles consumible) {
        this.consumible = consumible;
    }

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
