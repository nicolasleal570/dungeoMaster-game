package mapas;

import control.GestorControles;
import entes.enemigos.Enemigo;
import entes.enemigos.RegistroEnemigos;
import herramientas.CargadorRecursos;
import herramientas.ElementosPrincipales;
import inventario.ContenedorObjetos;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import principal.Constantes;
import sprites.HojaSprites;
import sprites.Sprite;

public class Mapa {

    private final int width, height;

    private final String[] partes;

    private final Sprite[] paleta;

    private final boolean[] colisiones;

    public final ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
    public ArrayList<ContenedorObjetos> objMapa;
    public ArrayList<Enemigo> enemigos;

    private final int[] sprites;

    private final int margenX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
    private final int margenY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

    public Mapa(final String ruta) {

        String contenido = CargadorRecursos.leerArchivoTexto(ruta); // Importando el archivo de texto donde esta el mapa

        this.partes = contenido.split("\\*"); // Rompemos la cadena donde encuentre *

        this.width = Integer.parseInt(partes[0]); // Guardando el ancho
        this.height = Integer.parseInt(partes[1]); // Guardando el alto

        String hojaUtilizada = partes[2];
        String[] hojaSeparadas = hojaUtilizada.split(",");

        String paletaEntera = partes[3];
        String[] partesPaleta = paletaEntera.split("#");

        paleta = asignarSprites(partesPaleta, hojaSeparadas);

        // Se inician las colisiones del mapa
        String colisionesEnteras = partes[4];
        this.colisiones = extraerColisiones(colisionesEnteras);

        // Se inician los sprites del mapa
        String spritesEnteros = partes[5];
        String[] cadenaSprites = spritesEnteros.split(" ");

        sprites = extraerSprites(cadenaSprites);

        // Se inician los Contenedores de objetos del mapa
        String informacionObjetos = partes[6];
        objMapa = asignarObjetos(informacionObjetos);

        // Se inician los enemigos
        String informacionEnemigos = partes[7];
        this.enemigos = asignarEnemigos(informacionEnemigos);

    }

    private ArrayList<Enemigo> asignarEnemigos(String informacionEnemigos) {

        ArrayList<Enemigo> enemigos = new ArrayList<>();

        String[] informacionEnemigosSeparada = informacionEnemigos.split("#"); // Cada enemigo

        for (int i = 0; i < informacionEnemigosSeparada.length; i++) {

            //Informacion de cada enemigo
            String[] informacionEnemigoActual = informacionEnemigosSeparada[i].split(":");

            String[] coordenadas = informacionEnemigoActual[0].split(","); // Coordenadas x y

            String idEnemigo = informacionEnemigoActual[1]; // Id del enemigo

            Enemigo enemigo = RegistroEnemigos.getEnemigo(Integer.parseInt(idEnemigo)); // Creando al enemigo
            enemigo.setPosicion(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1])); // Situandolo en el mapa con las coordenadas

            enemigos.add(enemigo); // Agregando al enemigo al ArrayList

        }

        return enemigos;
    }

    private ArrayList<ContenedorObjetos> asignarObjetos(final String informacionObjetos) {

        final ArrayList<ContenedorObjetos> objetos = new ArrayList<>();

        // Separa los dos diferentes contenedores
        String[] contenedoresObj = informacionObjetos.split("#");

        for (String contenedorInvidual : contenedoresObj) {

            final ArrayList<Integer> idObj = new ArrayList<Integer>();
            final ArrayList<Integer> cantidadObj = new ArrayList<Integer>();

            // Separa las coordenadas de los objetos
            final String[] divisionInformacionObj = contenedorInvidual.split(":");

            // Separa las coordenadasX y Y
            final String[] coordenadas = divisionInformacionObj[0].split(",");
            final Point posicionContenedor = new Point(Integer.parseInt(coordenadas[0]), Integer.parseInt(coordenadas[1]));

            // Separa los objetos entre si
            final String[] objCantidades = divisionInformacionObj[1].split("/");

            for (String objActual : objCantidades) {

                // Separa el id del objeto y la cantidad
                final String[] datosObjetoActual = objActual.split("-");

                idObj.add(Integer.parseInt(datosObjetoActual[0]));
                cantidadObj.add(Integer.parseInt(datosObjetoActual[1]));

            }

            final int[] idObjArray = new int[idObj.size()];
            final int[] cantidadObjArray = new int[cantidadObj.size()];

            for (int i = 0; i < idObjArray.length; i++) {

                idObjArray[i] = idObj.get(i);
                cantidadObjArray[i] = cantidadObj.get(i);

            }

            final ContenedorObjetos contenedor = new ContenedorObjetos(posicionContenedor, idObjArray, cantidadObjArray);

            objetos.add(contenedor);

        }

        return objetos;
    }

    private Sprite[] asignarSprites(final String[] partesPaleta, final String[] hojaSeparadas) {

        Sprite[] paleta = new Sprite[partesPaleta.length];
        HojaSprites hoja = new HojaSprites("/recursos/texturas/" + hojaSeparadas[0] + ".png", 32, false);

        for (int i = 0; i < partesPaleta.length; i++) {

            String spriteTemportal = partesPaleta[i];
            String[] partesSprite = spriteTemportal.split("-");

            int indicePaleta = Integer.parseInt(partesSprite[0]);
            int indiceSpriteHoja = Integer.parseInt(partesSprite[2]);

            paleta[indicePaleta] = hoja.getSprite(indiceSpriteHoja);

        }

        return paleta;
    }

    private boolean[] extraerColisiones(final String cadenaColisiones) {

        boolean[] colisiones = new boolean[cadenaColisiones.length()];

        for (int i = 0; i < cadenaColisiones.length(); i++) {

            if (cadenaColisiones.charAt(i) == '0') {
                colisiones[i] = false;
            } else {
                colisiones[i] = true;
            }

        }

        return colisiones;
    }

    private int[] extraerSprites(final String[] cadenaSprites) {

        ArrayList<Integer> sprites = new ArrayList<Integer>();

        for (int i = 0; i < cadenaSprites.length; i++) {

            if (cadenaSprites[i].length() == 2) {
                sprites.add(Integer.parseInt(cadenaSprites[i]));
            } else {

                String uno = "";
                String dos = "";

                String error = cadenaSprites[i];

                uno += error.charAt(0);
                uno += error.charAt(1);

                dos += error.charAt(2);
                dos += error.charAt(3);

                sprites.add(Integer.parseInt(uno));
                sprites.add(Integer.parseInt(dos));

            }

        }

        int[] vectorSprites = new int[sprites.size()];

        for (int i = 0; i < sprites.size(); i++) {

            vectorSprites[i] = sprites.get(i);

        }

        return vectorSprites;
    }

    public void actualizar() {
        this.actualizarAreasColision();

        this.actualizarRecogidaObjetos();
    }

    private void actualizarRecogidaObjetos() {

        if (!this.objMapa.isEmpty()) {

            final Rectangle areaJugador = new Rectangle(ElementosPrincipales.jugador.getPosXInt(),
                    ElementosPrincipales.jugador.getPosYInt(),
                    Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            for (int i = 0; i < this.objMapa.size(); i++) {

                final ContenedorObjetos contenedor = this.objMapa.get(i);

                final Rectangle posicionContenedor = new Rectangle(contenedor.getPosicion().x * Constantes.LADO_SPRITE,
                        contenedor.getPosicion().y * Constantes.LADO_SPRITE,
                        Constantes.LADO_SPRITE,
                        Constantes.LADO_SPRITE);

                if (areaJugador.intersects(posicionContenedor) && GestorControles.teclado.recogiendo) {

                    ElementosPrincipales.inventario.recogerContenedorObjetos(contenedor);
                    this.objMapa.remove(i);

                }

            }

        }

    }

    private void actualizarAreasColision() {

        if (!this.areasColision.isEmpty()) { // Limpiamos el arreglo
            this.areasColision.clear();
        }

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {

                int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosXInt() + this.margenX;
                int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosYInt() + this.margenY;

                if (this.colisiones[x + y * this.width]) {

                    final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
                    this.areasColision.add(r);

                }

            }
        }

    }

    public void dibujar(Graphics g) {

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {

                BufferedImage img = paleta[sprites[x + y * this.width]].getImagen();

                int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosXInt() + this.margenX;
                int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosYInt() + this.margenY;

                g.drawImage(img, puntoX, puntoY, null);
            }
        }

        // Dibuja los contenedores con objetos en el mapa
        if (!this.objMapa.isEmpty()) {

            for (ContenedorObjetos contenedor : this.objMapa) {

                final int puntoX = contenedor.getPosicion().x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosXInt() + this.margenX;
                final int puntoY = contenedor.getPosicion().y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosYInt() + this.margenY;

                contenedor.dibujar(g, puntoX, puntoY);

            }

        }

        if (!this.enemigos.isEmpty()) {

            for (Enemigo enemigo : this.enemigos) {

                final int puntoX = enemigo.getPosicionXInt() * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosXInt() + this.margenX;
                final int puntoY = enemigo.getPosicionYInt() * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getPosYInt() + this.margenY;

                enemigo.dibujar(g, puntoX, puntoY);

            }

        }

    }

    public Rectangle getBordes(final int posX, final int posY) {

        int x = this.margenX - posX + ElementosPrincipales.jugador.getAnchoJugador();
        int y = this.margenY - posY + ElementosPrincipales.jugador.getAltoJugador();
        int ancho = this.width * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAnchoJugador() * 2;
        int alto = this.height * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAltoJugador() * 2;

        return new Rectangle(x, y, ancho, alto);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Sprite getSpritePaleta(final int indice) {
        return paleta[indice];
    }

    public Sprite getSpritePaleta(final int x, final int y) {
        return paleta[x + y * this.width];
    }

    public Sprite[] getPaleta() {
        return paleta;
    }

}
