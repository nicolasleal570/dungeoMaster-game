package gestorMapas;

import control.GestorControles;
import dijkstra.Dijkstra;
import entes.enemigos.Enemigo;
import entes.enemigos.RegistroEnemigos;
import herramientas.Calculadora;
import herramientas.CargadorRecursos;
import herramientas.Constantes;
import herramientas.DibujoDebug;
import herramientas.ElementosPrincipales;
import inventario.Objeto;
import inventario.ObjetoUnicoTiled;
import inventario.RegistroObjetos;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sprites.HojaSprites;
import sprites.Sprite;

public class MapaTiled {

    private int anchoMapaEnTiles, altoMapaEnTiles;
    private Point puntoInicial;
    private Point puntoSalida;

    private String siguienteMapa;
    private String mapaActual;
    private Rectangle zonaSalida;

    private ArrayList<CapaSprites> capasSprites;
    private ArrayList<CapaColisiones> capasColisiones;

    private final ArrayList<Rectangle> areasColisionOriginales;
    public ArrayList<Rectangle> areasColisionPorActualizacion;

    private final ArrayList<ObjetoUnicoTiled> objetosMapa;

    private final ArrayList<Enemigo> enemigosMapa;

    private final Sprite[] paletaSprites;

    private final Dijkstra d;

    private int enemigosMuertos = 0;
    private int numeroRonda = 1;
    private int nivelJugador = 1;

    private String contenido;
    private String visitado;

    private final Random random = new Random();

    // CREADOR DEL MAPA DEL JUEGO
    public MapaTiled(String ruta) {

        this.contenido = CargadorRecursos.leerArchivoTexto(ruta); // Archivo JSON
        JSONObject globalJson = this.getObjetoJson(contenido);

        // Ancho y alto del mapa en Tiles
        this.dimensionesMapa(globalJson);

        // Punto de spawn del jugador
        this.spawnJugador(globalJson);

        // Salida del Mapa
        this.zonaSalida = new Rectangle();
        this.salidaMapa(globalJson);

        // CAPAS DEL MAPA
        this.capasMapa(globalJson);

        // COMBINANDO LAS COLISIONES EN UN ARRAYLIST
        this.areasColisionOriginales = new ArrayList<>();
        this.colisionesMapa();

        d = new Dijkstra(new Point(10, 10), this.anchoMapaEnTiles, this.altoMapaEnTiles, this.areasColisionOriginales);

        // Averiguar total de Sprites existentes en todas las capas
        JSONArray coleccionesSprites = this.getArrayJson(globalJson.get("tilesets").toString()); // Array de los Sprites

        int totalSprites = this.totalSprites(coleccionesSprites);
        this.paletaSprites = new Sprite[totalSprites]; // Iniciando la paleta de los sprites

        // Asignando los Sprites necesarios a partir de las capas
        this.asignandoSprites(coleccionesSprites);

        // OBTENER OBJETOS
        this.objetosMapa = new ArrayList<>();
        this.objetosMapa(globalJson);

        // AGREGANDO LOS ENEMIGOS PRE ESTABLECIDOS
        this.enemigosMapa = new ArrayList<>();
        this.enemigosMapa(globalJson);

        //Areas de colision por cada actualizacion
        this.areasColisionPorActualizacion = new ArrayList<>();

    } // Fin del constructor

    // DIMENSIONES DEL MAPA CON JSON
    private void dimensionesMapa(JSONObject globalJson) {
        this.anchoMapaEnTiles = this.getIntDesdeJson(globalJson, "width"); // Ancho del mapa en tiles
        this.altoMapaEnTiles = this.getIntDesdeJson(globalJson, "height"); // Alto del mapa en tiles
    }

    // SPAWN DEL JUGADOR CON JSON
    private void spawnJugador(JSONObject globalJson) {
        JSONObject puntoInicialJson = this.getObjetoJson(globalJson.get("start").toString());
        this.puntoInicial = new Point(this.getIntDesdeJson(puntoInicialJson, "x"),
                this.getIntDesdeJson(puntoInicialJson, "y"));
    }

    private void salidaMapa(JSONObject globalJson) {

        JSONObject salida = null;

        if (globalJson.get("salida").toString() != null) {
            salida = this.getObjetoJson(globalJson.get("salida").toString());

            this.puntoSalida = new Point(this.getIntDesdeJson(salida, "x") * 32,
                    this.getIntDesdeJson(salida, "y") * 32);

            this.mapaActual = salida.get("mapaActual").toString(); // Nombre del mapa actual
            this.siguienteMapa = salida.get("siguienteMapa").toString(); // Nombre del siguiente Mapa
        }

    }

    private void capasMapa(JSONObject globalJson) {
        JSONArray capas = this.getArrayJson(globalJson.get("layers").toString()); // Todas las capas en String

        // Arreglo con las capas del mapa
        this.capasSprites = new ArrayList<>();
        this.capasColisiones = new ArrayList<>();

        // Iniciar las capas, rellenarlas
        for (int i = 0; i < capas.size(); i++) {

            JSONObject datosCapa = this.getObjetoJson(capas.get(i).toString()); // Datos de la capa en String

            String tipo = datosCapa.get("type").toString(); // Tipo de la capa.

            // Informacion de los datos de las diferentes capas
            int anchoCapa = this.getIntDesdeJson(datosCapa, "width");
            int altoCapa = this.getIntDesdeJson(datosCapa, "height");
            int xCapa = this.getIntDesdeJson(datosCapa, "x");
            int yCapa = this.getIntDesdeJson(datosCapa, "y");

            switch (tipo) {
                case "tilelayer":
                    JSONArray sprites = this.getArrayJson(datosCapa.get("data").toString()); // Arreglo con todos los numeros de los sprites
                    int[] spritesCapa = new int[sprites.size()];

                    for (int j = 0; j < sprites.size(); j++) {

                        int codigoSprite = Integer.parseInt(sprites.get(j).toString()); // Todos los codigos de sprites

                        spritesCapa[j] = codigoSprite - 1; // Vaciando todos los sprites en el arreglo

                    }

                    this.capasSprites.add(new CapaSprites(anchoCapa, altoCapa, xCapa, yCapa, spritesCapa)); // Creando una nueva capa de Sprites

                    break;

                case "objectgroup":
                    JSONArray rectangulos = this.getArrayJson(datosCapa.get("objects").toString()); // Arreglo con todos los rectangulos de las colisiones
                    Rectangle[] rectangulosCapas = new Rectangle[rectangulos.size()];

                    for (int j = 0; j < rectangulos.size(); j++) {

                        JSONObject datosRectangulo = this.getObjetoJson(rectangulos.get(j).toString()); // Datos del rectangulo

                        int x = this.getIntDesdeJson(datosRectangulo, "x");
                        int y = this.getIntDesdeJson(datosRectangulo, "y");
                        int ancho = this.getIntDesdeJson(datosRectangulo, "width");
                        int alto = this.getIntDesdeJson(datosRectangulo, "height");

                        if (x == 0) {
                            x = 1;
                        }

                        if (y == 0) {
                            y = 1;
                        }

                        if (ancho == 0) {
                            ancho = 1;
                        }

                        if (alto == 0) {
                            alto = 1;
                        }

                        Rectangle rectangulo = new Rectangle(x, y, ancho, alto);

                        rectangulosCapas[j] = rectangulo;

                    }

                    this.capasColisiones.add(new CapaColisiones(anchoCapa, altoCapa, xCapa, yCapa, rectangulosCapas)); // Crea una nueva capa de colisiones

                    break;

            }
        }
    }

    private void colisionesMapa() {
        for (int i = 0; i < this.capasColisiones.size(); i++) {

            Rectangle[] rectangulos = this.capasColisiones.get(i).getColisionables();

            for (int j = 0; j < rectangulos.length; j++) {

                this.areasColisionOriginales.add(rectangulos[j]);

            }

        }
    }

    private int totalSprites(JSONArray coleccionesSprites) {

        int totalSprites = 0;

        for (int i = 0; i < coleccionesSprites.size(); i++) {

            JSONObject datosGrupo = this.getObjetoJson(coleccionesSprites.get(i).toString()); // Datos del grupo de sprites

            totalSprites += this.getIntDesdeJson(datosGrupo, "tilecount");

        }

        return totalSprites;
    }

    private void asignandoSprites(JSONArray coleccionesSprites) {

        for (int i = 0; i < coleccionesSprites.size(); i++) { // recorre todas las hojas de Sprites que existen

            JSONObject datosGrupo = this.getObjetoJson(coleccionesSprites.get(i).toString());

            String nombreImagen = datosGrupo.get("image").toString(); // Nombre de la hoja de Sprites
            int anchoTiles = this.getIntDesdeJson(datosGrupo, "tilewidth"); // Ancho de Sprites
            int altoTiles = this.getIntDesdeJson(datosGrupo, "tileheight"); // Alto de Sprites

            // Iniciando la hoja de Sprites
            HojaSprites hoja = new HojaSprites("/recursos/texturas/" + nombreImagen, anchoTiles, altoTiles, false);

            int primerSpriteColeccion = this.getIntDesdeJson(datosGrupo, "firstgid") - 1; // Comenzando con el primer sprite en 0
            int ultimoSpriteColeccion = primerSpriteColeccion + this.getIntDesdeJson(datosGrupo, "tilecount") - 1;

            // Recorriendo todas las hojas de Sprites
            for (int j = 0; j < this.capasSprites.size(); j++) {

                CapaSprites capaActual = this.capasSprites.get(j);

                int[] spritesCapa = capaActual.getSprites(); // Codigos de los sprites 

                // Asignando los Sprites
                for (int k = 0; k < spritesCapa.length; k++) {

                    int idSpriteActual = spritesCapa[k];

                    if (idSpriteActual >= primerSpriteColeccion && idSpriteActual <= ultimoSpriteColeccion) {

                        if (this.paletaSprites[idSpriteActual] == null) {
                            this.paletaSprites[idSpriteActual] = hoja.getSprite(idSpriteActual - primerSpriteColeccion);
                        }

                    }

                }

            }

        }

    }

    private void objetosMapa(JSONObject globalJson) {
        JSONArray coleccionObjetos = this.getArrayJson(globalJson.get("objetos").toString()); // Todos los objetos que se van a poner en el mapa
        for (int i = 0; i < coleccionObjetos.size(); i++) {

            JSONObject datosObjeto = this.getObjetoJson(coleccionObjetos.get(i).toString());

            int idObjeto = this.getIntDesdeJson(datosObjeto, "id");
            int cantidadObjeto = this.getIntDesdeJson(datosObjeto, "cantidad");
            int xObjeto = this.getIntDesdeJson(datosObjeto, "x");
            int yObjeto = this.getIntDesdeJson(datosObjeto, "y");
            Point posicionObjeto = new Point(xObjeto, yObjeto);

            Objeto objeto = RegistroObjetos.getObjeto(idObjeto);

            ObjetoUnicoTiled objUnico = new ObjetoUnicoTiled(posicionObjeto, objeto, cantidadObjeto);

            this.objetosMapa.add(objUnico); // Agregando todos los objetos que existan
        }
    }

    private void enemigosMapa(JSONObject globalJson) {
        JSONArray coleccionEnemigos = this.getArrayJson(globalJson.get("enemigos").toString()); // Todos los objetos que se van a poner en el mapa

        for (int i = 0; i < coleccionEnemigos.size(); i++) {

            JSONObject datosEnemigo = this.getObjetoJson(coleccionEnemigos.get(i).toString());

            int idEnemigo = this.getIntDesdeJson(datosEnemigo, "id");
            int xEnemigo = this.getIntDesdeJson(datosEnemigo, "x");
            int yEnemigo = this.getIntDesdeJson(datosEnemigo, "y");

            Point posicionEnemigo = new Point(xEnemigo, yEnemigo);

            Enemigo enemigo = RegistroEnemigos.getEnemigo(idEnemigo);
            enemigo.setPosicion(posicionEnemigo.x, posicionEnemigo.y);
            enemigo.setDefensaActual(0);
            enemigo.setNivel(1);

            this.enemigosMapa.add(enemigo); // Agregando todos los objetos que existan
        }
    }

    // ACTUALIZA EL MAPA
    public void actualizar() {

        actualizarAreasColision();
        actualizarSalidaMapa();
        actualizarRecogidaObjetos();
        actualizarEnemigos();
        actualizarAtaques();
        actualizarNivelJugador();

        Point punto = new Point(ElementosPrincipales.jugador.getPosXInt(),
                ElementosPrincipales.jugador.getPosYInt());
        Point puntoCoincidente = d.getCoordenadasNodoCoincidente(punto);
        d.reiniciarYEvaluar(puntoCoincidente);

    }

    // ACTUALIZA LOS ATAQUES EN EL MAPA
    private void actualizarAtaques() {

        if (this.enemigosMapa.isEmpty()) { // No permite tener al jugador desarmado
            return;
        }

        if (GestorControles.teclado.atacando) { // Cuando se pulsa el espacio se ataca

            ArrayList<Enemigo> enemigosAlcanzados = new ArrayList<>();

            if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma() != null) {

                if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma().isPenetrante()) {

                    for (Enemigo enemigo : this.enemigosMapa) {

                        if (ElementosPrincipales.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {

                            enemigosAlcanzados.add(enemigo); // Enemigo disparado

                        }

                    }

                } else {

                    Enemigo enemigoMasCercano = null;
                    Double distanciaMasCercana = null;

                    for (Enemigo enemigo : this.enemigosMapa) {

                        if (ElementosPrincipales.jugador.getAlcanceActual().get(0).intersects(enemigo.getArea())) {

                            Point puntoJugador = new Point(ElementosPrincipales.jugador.getPosXInt() / Constantes.LADO_SPRITE,
                                    ElementosPrincipales.jugador.getPosYInt() / Constantes.LADO_SPRITE);

                            Point puntoEnemigo = new Point(enemigo.getPosicionXInt(), enemigo.getPosicionYInt());

                            Double distanciaActual = Calculadora.getDistanciaEntrePuntos(puntoJugador, puntoEnemigo);

                            if (enemigoMasCercano == null) {
                                enemigoMasCercano = enemigo;
                                distanciaMasCercana = distanciaActual;
                            } else if (distanciaActual > distanciaMasCercana) {

                                // Enemigo mas cercano
                                enemigoMasCercano = enemigo;
                                distanciaMasCercana = distanciaActual;

                            }

                        }

                    }

                    enemigosAlcanzados.add(enemigoMasCercano);

                }

                ElementosPrincipales.jugador.getAlmacenEquipo().getArma().atacar(enemigosAlcanzados);

            }

        }

        Iterator<Enemigo> iterador = this.enemigosMapa.iterator();

        while (iterador.hasNext()) {

            Enemigo enemigo = iterador.next();

            if (enemigo.getVidaActual() <= 0) {

                this.enemigosMuertos++;
                ElementosPrincipales.jugador.setEnemigosMuertos(this.enemigosMuertos);

                this.actualizandoParametrosJugador(enemigo); // Actualizando todos los parametros del jugador segun vaya avanzando

                this.objetosMapa.add(this.crearDrops(enemigo));
                iterador.remove(); // eliminando enemigos muertos

            }

            if (this.enemigosMuertos > 0) {
                enemigo.setNivel(this.enemigosMuertos); // Subiendo de nivel a los enemigos
            } else {
                enemigo.setNivel(1);
            }

        }

    }

    // ACTUALIZA LOS VALORES DEL JUGADOR
    private void actualizandoParametrosJugador(Enemigo enemigo) {

        if (ElementosPrincipales.jugador.getVidaActual() >= 100) {

            ElementosPrincipales.jugador.setVidaActual(100);

        } else {

            if (ElementosPrincipales.jugador.getVidaActual() > 0) {

                ElementosPrincipales.jugador.setVidaActual(10 + ElementosPrincipales.jugador.getVidaActual());
            }

        }

        // Sumando la experiencia con cada muerte de enemigos
        int aleatorio = 1 + this.random.nextInt(10);

        float experiencia = ElementosPrincipales.jugador.getExperiencia() + (enemigo.getNivel() * aleatorio);
        ElementosPrincipales.jugador.setExperiencia(experiencia);

    }

    // ACTUALIZA LAS COLISIONES
    private void actualizarAreasColision() {

        if (!this.areasColisionPorActualizacion.isEmpty()) {

            this.areasColisionPorActualizacion.clear();

        }

        for (int i = 0; i < this.areasColisionOriginales.size(); i++) {

            Rectangle rInicial = this.areasColisionOriginales.get(i);

            int puntoX = rInicial.x - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
            int puntoY = rInicial.y - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

            final Rectangle rFinal = new Rectangle(puntoX, puntoY, rInicial.width, rInicial.height);

            this.areasColisionPorActualizacion.add(rFinal);

        }

    }

    private void actualizarSalidaMapa() {

        int puntoX = this.puntoSalida.x - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
        int puntoY = this.puntoSalida.y - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

        this.zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
    }

    // ACTUALIZA LOS OBJETOS RECOGIDOS
    private void actualizarRecogidaObjetos() {

        if (!this.objetosMapa.isEmpty()) {

            final Rectangle areaJugador = new Rectangle(ElementosPrincipales.jugador.getPosXInt(),
                    ElementosPrincipales.jugador.getPosYInt(),
                    Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

            for (int i = 0; i < this.objetosMapa.size(); i++) {

                final ObjetoUnicoTiled objetoActual = this.objetosMapa.get(i);

                final Rectangle posicionObjetoActual = new Rectangle(objetoActual.getPosicion().x,
                        objetoActual.getPosicion().y,
                        Constantes.LADO_SPRITE,
                        Constantes.LADO_SPRITE);

                if (areaJugador.intersects(posicionObjetoActual)
                        && GestorControles.teclado.recogiendo) {

                    // Comprobando que el jugador tiene menos de 10 objetos
                    /*if (ElementosPrincipales.inventario.getObjeto(i) != null
                            && ElementosPrincipales.inventario.getObjeto(i).getCantidad() <= 10) {
                        System.out.println("Recogido");
                    } else {
                        System.out.println("Maximo posibles");
                    }*/
                    ElementosPrincipales.inventario.recogerObjeto(objetoActual);
                    this.objetosMapa.remove(i);

                }

            }

        }

    }

    // ACTUALIZA EL NIVEL DEL JUGADOR
    private void actualizarNivelJugador() {

        if (ElementosPrincipales.jugador.getExperiencia() > 100 * ElementosPrincipales.jugador.getNivel()) {

            this.nivelJugador++;
            if (this.nivelJugador > 1) {
                ElementosPrincipales.jugador.setNivel(this.nivelJugador);
            }
        }

    }

    // ACTUALIZA LOS ENEMIGOS DEL MAPA
    private void actualizarEnemigos() {
        if (!enemigosMapa.isEmpty()) {
            for (Enemigo enemigo : this.enemigosMapa) {

                enemigo.cambiarSiguienteNodo(d.encontrarSiguienteNodoParaEnemigo(enemigo));
                enemigo.actualizar(enemigosMapa);

                // Haciendo que el jugador pierda vida cuando choca con un enemigo
                if (enemigo.jugadorColisionandoContraEnemigo()) {

                    enemigo.atacar(ElementosPrincipales.jugador);

                }

            }
        } else {

            // Le damos mas vida al personaje cada vez que mata un enemigo
            float promedio = ElementosPrincipales.jugador.getVidaActual() * (float) 0.10;
            float vidaNueva = ElementosPrincipales.jugador.getVidaActual() + promedio;

            if (vidaNueva >= 100) {

                ElementosPrincipales.jugador.setVidaActual(100);

            } else if (vidaNueva < 100) {

                ElementosPrincipales.jugador.setVidaActual(vidaNueva);

            }

            // Agregando nuevos enemigos para la siguiente orda
            for (int i = 0; i < this.crearEnemigosSiguienteOrda().size(); i++) {

                this.enemigosMapa.add(this.crearEnemigosSiguienteOrda().get(i));

            }

            //Sumando rondas al contador
            if (!this.enemigosMapa.isEmpty()) {
                this.sumarRonda();
            }

        }

    }

    public void dibujar(Graphics g) {

        // Dibujando el mapa
        for (int i = 0; i < this.capasSprites.size(); i++) {

            int[] spritesCapa = this.capasSprites.get(i).getSprites(); // Codigo de cada Sprite

            for (int y = 0; y < this.altoMapaEnTiles; y++) {
                for (int x = 0; x < this.anchoMapaEnTiles; x++) {

                    int idSpriteActual = spritesCapa[x + y * this.anchoMapaEnTiles];
                    if (idSpriteActual != -1) {

                        int puntoX = x * Constantes.LADO_SPRITE
                                - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
                        int puntoY = y * Constantes.LADO_SPRITE
                                - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

                        if (puntoX < 0 - Constantes.LADO_SPRITE
                                || puntoX > Constantes.ANCHO_JUEGO
                                || puntoY < 0 - Constantes.LADO_SPRITE
                                || puntoY > Constantes.ALTO_JUEGO - 64) {
                            continue;
                        }
                        DibujoDebug.dibujarImagen(g, this.paletaSprites[idSpriteActual].getImagen(), puntoX, puntoY);

                    }

                }
            }

        }

        /* DIBUJANDO TODOS LOS OBJETOS DEL MAPA */
        for (int i = 0; i < this.objetosMapa.size(); i++) {

            ObjetoUnicoTiled objActual = this.objetosMapa.get(i);

            int puntoX = objActual.getPosicion().x
                    - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
            int puntoY = objActual.getPosicion().y
                    - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

            DibujoDebug.dibujarImagen(g, objActual.getObjeto().getSprite().getImagen(), puntoX, puntoY);

        }

        /* DIBUJANDO LOS ENEMIGOS */
        for (int i = 0; i < this.enemigosMapa.size(); i++) {

            Enemigo enemigo = this.enemigosMapa.get(i);

            int puntoX = enemigo.getPosicionXInt()
                    - ElementosPrincipales.jugador.getPosXInt() + Constantes.MARGEN_X;
            int puntoY = enemigo.getPosicionYInt()
                    - ElementosPrincipales.jugador.getPosYInt() + Constantes.MARGEN_Y;

            enemigo.dibujar(g, puntoX, puntoY);

        }

        DibujoDebug.dibujarRectanguloContorno(g, this.zonaSalida, Color.red); // Zona de salida del mapa

    }

    /* DEVUELVE UN OBJETO JSON */
    private JSONObject getObjetoJson(final String codigoJson) {

        JSONParser lector = new JSONParser();
        JSONObject objetoJSON = null;

        try {

            Object recuperado = lector.parse(codigoJson);
            objetoJSON = (JSONObject) recuperado;

        } catch (ParseException e) {
            System.out.println("Posicion " + e.getPosition());
            System.out.println(e);
            e.printStackTrace();

        }

        return objetoJSON;
    }

    /* DEVUELVE UN ARREGLO DE TIPO JSON */
    private JSONArray getArrayJson(final String codigoJson) {

        JSONParser lector = new JSONParser();
        JSONArray arrayJSON = null;

        try {

            Object recuperado = lector.parse(codigoJson);
            arrayJSON = (JSONArray) recuperado;

        } catch (ParseException e) {
            System.out.println("Posicion " + e.getPosition());
            System.out.println(e);
            e.printStackTrace();

        }

        return arrayJSON;
    }

    private int getIntDesdeJson(final JSONObject objetoJSON, final String clave) {

        return Integer.parseInt(objetoJSON.get(clave).toString());

    }

    public Point getPosicionInicial() {
        return this.puntoInicial;
    }

    public Rectangle getBordes(final int posicionX, final int posicionY) {

        int x = Constantes.MARGEN_X - posicionX + ElementosPrincipales.jugador.getAnchoJugador();
        int y = Constantes.MARGEN_Y - posicionY + ElementosPrincipales.jugador.getAltoJugador();

        int ancho = this.anchoMapaEnTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAnchoJugador() * 2;
        int alto = this.altoMapaEnTiles * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.getAltoJugador() * 2;

        return new Rectangle(x, y, ancho, alto);

    }

    public int getEnemigosMuertos() {
        return enemigosMuertos;
    }

    public int getNumeroRonda() {
        return numeroRonda;
    }

    // CREA ENEMIGOS CUANDO TODOS SE MUEREN
    public ArrayList<Enemigo> crearEnemigosSiguienteOrda() {

        String contenido = CargadorRecursos.leerArchivoTexto(Constantes.RUTA_MAPA_TILED); // Archivo JSON

        JSONObject globalJson = this.getObjetoJson(contenido);

        ArrayList<Enemigo> enemigos = new ArrayList<>();

        JSONArray coleccionEnemigos = this.getArrayJson(globalJson.get("enemigos").toString()); // Todos los objetos que se van a poner en el mapa

        for (int i = 0; i < coleccionEnemigos.size(); i++) {

            JSONObject datosEnemigo = this.getObjetoJson(coleccionEnemigos.get(i).toString());

            int idEnemigo = 1 + this.random.nextInt(6);
            int xEnemigo = this.getIntDesdeJson(datosEnemigo, "x");
            int yEnemigo = this.getIntDesdeJson(datosEnemigo, "y");

            Point posicionEnemigo = new Point(xEnemigo, yEnemigo);

            Enemigo enemigo = RegistroEnemigos.getEnemigo(idEnemigo);
            enemigo.setPosicion(posicionEnemigo.x, posicionEnemigo.y);

            enemigos.add(enemigo); // Agregando todos los objetos que existan
        }

        return enemigos;

    }

    // SUMADOR DE RONDAS
    public void sumarRonda() {
        this.numeroRonda += 1;
    }

    // CREA UN DROP CUANDO SE MUERE UN ENEMIGO
    private ObjetoUnicoTiled crearDrops(Enemigo enemigo) {

        Point posObjeto = new Point(enemigo.getPosicionXInt(), enemigo.getPosicionYInt()); // Posicion del objeto segun el zombie

        int probabilidad = this.random.nextInt(10);

        int idObjeto = 0;
        int consumibles = this.random.nextInt(RegistroObjetos.numObjetosConsumibles);
        int equipables = 20 + this.random.nextInt(RegistroObjetos.numObjetosEquipables - 1);
        int cantidad = 0;

        // Haciendo la probabilidad de que te salga un obj equipable o consumible
        if (probabilidad >= 0 && probabilidad <= 5) {

            idObjeto = consumibles;
            cantidad = 1 + this.random.nextInt(3);

        } else if (probabilidad > 5 && probabilidad <= 10) {

            idObjeto = equipables;
            cantidad = 1;

        }

        Objeto objeto = RegistroObjetos.getObjeto(idObjeto);

        ObjetoUnicoTiled objetoUnico = new ObjetoUnicoTiled(posObjeto, objeto, cantidad);

        return objetoUnico;
    }

    public String getMapaActual() {
        return mapaActual;
    }

    public String getSiguienteMapa() {
        return siguienteMapa;
    }

    public Rectangle getZonaSalida() {
        return zonaSalida;
    }

    public Point getPuntoSalida() {
        return puntoSalida;
    }

}
