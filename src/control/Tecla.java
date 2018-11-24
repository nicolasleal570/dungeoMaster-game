package control;

public class Tecla {

    private boolean pulsada = false;
    private long ultimaPulsacion = System.nanoTime();

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
