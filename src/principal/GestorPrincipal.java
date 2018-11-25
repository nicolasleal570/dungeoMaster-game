package principal;

import control.GestorControles;
import graficos.SuperficieDibujo;
import graficos.Ventana;
import herramientas.ElementosPrincipales;
import maquinaEstado.GestorEstados;

public class GestorPrincipal {

    private boolean enFuncionamiento = false;

    private String titulo;
    private int width, height;

    public static SuperficieDibujo sd;
    private Ventana ventana;
    private GestorEstados ge;

    private static int fps = 0, aps = 0;

    private static int segundosJuego = 0;
    private static int minutosJuego = 0;

    /**
     *
     * @param titulo del juego
     * @param width de la pantalla
     * @param height de la pantalla
     */
    public GestorPrincipal(String titulo, int width, int height) {
        this.titulo = titulo;
        this.width = width;
        this.height = height;
    }

    // INICIA EL BUCLE DEL JUEGO
    public void iniciarJuego() {
        this.enFuncionamiento = true;
        this.inicializar();
    }

    // BUCLE DEL JUEGO 
    public void iniciarBuclePrincipal() {
        int actualizacionAcumulada = 0;
        int framesAcumulados = 0;

        final int nanoSegPorSeg = 1000000000; // UN SEGUNDO TRANSFORMADO A NANOSEGUNDO
        final byte actualizacionPorSegObjetivo = 60; // ACTUALIZACIONES POR SEGUNDO FPS
        final double nanoSegPorActualizacion = nanoSegPorSeg / actualizacionPorSegObjetivo; // CUANTOS NANOSEGUNDOS TRANSCURREN PARA QUE SE ACTUALICE 60 VECES POR SEGUNDO

        long refenrenciaActualizacion = System.nanoTime(); // TIEMPO EN NANOSEGUNDOS
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido; // 
        double deltaTiempo = 0; // CANTIDAD DE TIEMPO HASTA QUE SE REALIZA UNA ACTUALIZACION

        double tiempoJuego = 0;

        long referenciaJuego = System.nanoTime();

        /* BUCLE PRINCIPAL DEL JUEGO */
        while (this.enFuncionamiento) {

            final long inicioBucle = System.nanoTime(); // valor en nanosegundos    

            final long inicioBucleJuego = System.nanoTime();

            tiempoTranscurrido = inicioBucle - refenrenciaActualizacion; // CUANTO HA PASADO ENTRE "refenrenciaActualización" y "inicioBucle" 

            refenrenciaActualizacion = inicioBucle;

            deltaTiempo += tiempoTranscurrido / nanoSegPorActualizacion;

            // Calculando cuando tiempo dura el jugador 
            tiempoJuego = (inicioBucleJuego - referenciaJuego) / 1000000000;
            this.segundosJuego = (int) tiempoJuego;

            while (deltaTiempo >= 1) {
                this.actualizar();
                actualizacionAcumulada++;
                deltaTiempo--;
            }

            this.dibujar(); //MUESTRA LOS GRÁFICOS
            framesAcumulados++;

            if (System.nanoTime() - referenciaContador > nanoSegPorSeg) {

                aps = actualizacionAcumulada;
                fps = framesAcumulados;

                actualizacionAcumulada = 0;
                framesAcumulados = 0;

                referenciaContador = System.nanoTime();
            }

        }

    }

    // CALCULO DE LOS SEGUNDOS JUGADOS
    public static int getSegundosJuego() {
        return segundosJuego;
    }

    // CALCULO DE LOS MINUTOS JUGADOS
    public static int getMinutosJuego() {
        return minutosJuego;
    }

    // GETTER DE LOS FPS
    public static int getFps() {
        return fps;
    }

    // GETTER DE LAS ACTUALIZACIONES POR SEGUNDO
    public static int getAps() {
        return aps;
    }

    // INICIA LA VENTANA DEL JUEGO
    public void inicializar() {
        this.sd = new SuperficieDibujo(this.width, this.height);
        this.ventana = new Ventana(titulo, sd);
        this.ge = new GestorEstados(sd); // Inicinado el gestor de estados
    }

    // CAMBIA LOS ESTADOS DEL JUEGO
    private void actualizar() {

        if (GestorControles.teclado.start) {

            ge.cambiarEstadoActual(0);

            if (GestorControles.teclado.inventarioActivo) {

                ge.cambiarEstadoActual(2);

            } else {
                ge.cambiarEstadoActual(1);
            }

            if (ElementosPrincipales.jugador.getVidaActual() <= 0) {
                ge.cambiarEstadoActual(3);
            }

        }

        this.ge.actualizar();
        this.sd.actualizar();

    }

    // MUESTRA LOS GRAFICOS
    private void dibujar() {
        this.sd.dibujar(this.ge);

    }

}
