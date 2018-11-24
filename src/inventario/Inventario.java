package inventario;

import inventario.consumibles.Consumibles;
import inventario.equipables.Arma;
import java.util.ArrayList;

public class Inventario {

    public final ArrayList<Objeto> objetos; // Todos los objetos del inventario

    public Inventario() {

        /* OBJETOS DEL JUEGO */
        this.objetos = new ArrayList<Objeto>();

    }

    public void recogerContenedorObjetos(final ContenedorObjetos co) {

        for (Objeto objeto : co.getObjetos()) {

            if (this.objetoExiste(objeto)) {

                this.incrementarObjeto(objeto, objeto.getCantidad());

            } else {

                this.objetos.add(objeto);

            }

        }

    }

    public void recogerObjeto(final ObjetoUnicoTiled objActual) {

        Objeto objeto = objActual.getObjeto();

        // Modificado para aumentar el numero de elementos en el inventario
        int cantidad = objActual.getCantidad();
        objeto.setCantidad(cantidad);

        if (this.objetoExiste(objeto)) {

            this.incrementarObjeto(objeto, objeto.getCantidad());

        } else {

            this.objetos.add(objeto);

        }

    }

    public boolean objetoExiste(Objeto objeto) {

        boolean existe = false;

        for (Objeto objetoActual : this.objetos) {

            if (objetoActual.getId() == objeto.getId()) {
                existe = true;
                break;
            }

        }

        return existe;

    }

    public boolean incrementarObjeto(final Objeto objeto, final int cantidad) {

        boolean incrementado = false;

        for (Objeto objActual : this.objetos) {

            if (objActual.getId() == objeto.getId()) {

                objActual.incrementarCantidad(cantidad);
                incrementado = true;

                break;
            }

        }
        return incrementado;
    }

    /* DEVUELVE TODOS LOS OBJETOS CONSUMIBLES */
    public ArrayList<Objeto> getConsumibles() {

        ArrayList<Objeto> consumibles = new ArrayList<>();

        for (Objeto obj : this.objetos) {

            if (obj instanceof Consumibles) { // Si el objeto es una instancia de COnusmible

                consumibles.add(obj);
            }

        }

        return consumibles;
    }

    /* DEVUELVE TODOS LOS OBJETOS EQUIPABLES */
    public ArrayList<Objeto> getArmas() {

        ArrayList<Objeto> armas = new ArrayList<>();

        for (Objeto obj : this.objetos) {

            if (obj instanceof Arma) { // Si el objeto es una instancia de COnusmible

                armas.add(obj);
            }

        }

        return armas;

    }

    public Objeto getObjeto(final int idObj) {

        for (Objeto objActual : this.objetos) {

            if (objActual.getId() == idObj) {
                return objActual;
            }
        }

        return null;
    }

}
