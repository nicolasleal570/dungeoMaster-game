package control;

public class Tecla {

    private boolean pulsada = false;
    private long ultimaPulsacion = System.nanoTime();

    // CREA UNA TECLA Y EVALUA LAS PULSACIONES Y SI ESTA PISADA O NO
    public void teclaPulsada() {

        this.pulsada = true;
        this.ultimaPulsacion = System.nanoTime();

    }

    public void teclaLiberada() {
        this.pulsada = false;
    }

    public boolean isPulsada() {
        return pulsada;
    }

    public long getUltimaPulsacion() {
        return ultimaPulsacion;
    }

}
