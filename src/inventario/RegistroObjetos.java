package inventario;

import inventario.consumibles.Consumibles;
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
                objeto = new Consumibles(idObjeto, "Pocion de Defensa", "");
                break;

            case 1:
                objeto = new Consumibles(idObjeto, "Pocion de Fuerza", "");
                break;

            case 2:
                objeto = new Consumibles(idObjeto, "Pocion de Vida", "");
                break;

            case 3:
                objeto = new Consumibles(idObjeto, "Manzana", "");
                break;

            case 4:
                objeto = new Consumibles(idObjeto, "Zanahoria", "");
                break;

            case 5:
                objeto = new Consumibles(idObjeto, "Galleta", "");
                break;

            // Objetos Equipables 20 - 39
            case 20:
                objeto = new Pistola(idObjeto, "Pistola", "", 3, 9, false, true, 0.4);
                break;

            case 21:
                objeto = new Escopeta(idObjeto, "Escopeta", "", 10, 12, false, true, 0.6);
                break;

            case 22:
                objeto = new RifleTipo1(idObjeto, "Rifle Tipo 1", "", 7, 9, false, true, 0.2);
                break;

            case 23:
                objeto = new RifleTipo2(idObjeto, "Rifle Tipo 2", "", 5, 10, false, true, 0.2);
                break;

            case 24:
                objeto = new Desarmado(idObjeto, "Puño Americano", "", 3, 5, false, true, 0.6);
                break;
        }

        return objeto;
    }

}
