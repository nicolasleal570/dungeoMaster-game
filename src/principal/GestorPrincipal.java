package principal;

import control.GestorControles;
import graficos.SuperficieDibujo;
import graficos.Ventana;
import maquinaEstado.GestorEstados;

public class GestorPrincipal {

    private boolean enFuncionamiento = false;

    private String titulo;
    private int width, height;

    public static SuperficieDibujo sd;
    private Ventana ventana;
    private GestorEstados ge;

    private static int fps = 0,
            aps = 0;

    public GestorPrincipal(String titulo, int width, int height) {
        this.titulo = titulo;
        this.width = width;
        this.height = height;
    }

    public void iniciarJuego() {
        this.enFuncionamiento = true;
        this.inicializar();
    }

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

        /* BUCLE PRINCIPAL DEL JUEGO */
        while (this.enFuncionamiento) {

            final long inicioBucle = System.nanoTime(); // valor en nanosegundos    

            tiempoTranscurrido = inicioBucle - refenrenciaActualizacion; // CUANTO HA PASADO ENTRE "refenrenciaActualización" y "inicioBucle" 
            refenrenciaActualizacion = inicioBucle;

            deltaTiempo += tiempoTranscurrido / nanoSegPorActualizacion;

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

    public static int getFps() {
        return fps;
    }

    public static int getAps() {
        return aps;
    }

    public void inicializar() {
        this.sd = new SuperficieDibujo(this.width, this.height);
        this.ventana = new Ventana(titulo, sd);
        this.ge = new GestorEstados(sd); // Inicinado el gestor de estados
    }

    private void actualizar() {

        if (GestorControles.teclado.start) {

            ge.cambiarEstadoActual(0);

            if (GestorControles.teclado.inventarioActivo) {

                ge.cambiarEstadoActual(2);

            } else {
                ge.cambiarEstadoActual(1);
            }

        }

        this.ge.actualizar();
        this.sd.actualizar();

    }

    private void dibujar() {
        this.sd.dibujar(this.ge);

    }

}