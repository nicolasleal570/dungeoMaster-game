package inventario;

import inventario.consumibles.Galleta;
import inventario.consumibles.Manzana;
import inventario.consumibles.PocionDefensa;
import inventario.consumibles.PocionFuerza;
import inventario.consumibles.PocionVida;
import inventario.consumibles.Zanahoria;
import inventario.equipables.Desarmado;
import inventario.equipables.Escopeta;
import inventario.equipables.Pistola;
import inventario.equipables.RifleTipo1;
import inventario.equipables.RifleTipo2;

public class RegistroObjetos {

    public final static int numObjetosConsumibles = 6; // Numero de objetos consumibles disponibles
    public final static int numObjetosEquipables = 5; // Numero de bojetos equipables disponibles

    public final static int numObjetosTotales = numObjetosConsumibles + numObjetosEquipables; // Total de objetos disponibles en el juego

    public static Objeto getObjeto(final int idObjeto) {

        Objeto objeto = null;

        switch (idObjeto) {

            // Objetos consumibles 0 - 19
            case 0:
                objeto = new PocionDefensa(idObjeto, "Pocion de Defensa", "", 1, 1, 15, 1);
                break;

            case 1:
                objeto = new PocionFuerza(idObjeto, "Pocion de Fuerza", "", 1, 15, 1, 1);
                break;

            case 2:
                objeto = new PocionVida(idObjeto, "Pocion de Vida", "", 1, 5, 5, 15);
                break;

            case 3:
                objeto = new Manzana(idObjeto, "Manzana", "", 1, 8, 4, 8);
                break;

            case 4:
                objeto = new Zanahoria(idObjeto, "Zanahoria", "", 1, 4, 8, 4);
                break;

            case 5:
                objeto = new Galleta(idObjeto, "Galleta", "", 1, 1, 1, 1);
                break;

            // Objetos Equipables 20 - 39
            case 20:
                objeto = new Pistola(idObjeto, "Pistola", "", 3, 9, false, true, 0.4);
                break;

            case 21:
                objeto = new Escopeta(idObjeto, "Escopeta", "", 25, 30, false, true, 0.2);
                break;

            case 22:
                objeto = new RifleTipo1(idObjeto, "M4", "", 7, 9, false, true, 0.25);
                break;

            case 23:
                objeto = new RifleTipo2(idObjeto, "AK-47", "", 5, 10, false, true, 0.25);
                break;

            case 24:
                objeto = new Desarmado(idObjeto, "Puño Americano", "", 3, 5, false, true, 0.5);
                break;
        }

        return objeto;
    }

}
