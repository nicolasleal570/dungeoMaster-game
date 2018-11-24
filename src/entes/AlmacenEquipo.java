package entes;

import inventario.RegistroObjetos;
import inventario.equipables.Arma;

public class AlmacenEquipo {

    private Arma arma;
    //municion

    public AlmacenEquipo(final Arma arma1) {

        this.arma = arma1;

    }

    public Arma getArma1() {
        if (this.arma == null) {
            return this.arma = (Arma) RegistroObjetos.getObjeto(24); // Devolviendo el arma desarmado
        }
        return this.arma;
    }

    public void cambiarArma1(final Arma arma1) {
        this.arma = arma1;
    }

}
