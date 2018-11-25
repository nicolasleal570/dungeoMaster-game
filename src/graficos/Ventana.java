package graficos;

import herramientas.CargadorRecursos;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Ventana extends JFrame {

    private String titulo;
    private final ImageIcon icono;

    private JTextField nombre = new JTextField();

    /**
     * @param titulo de la ventana
     * @param sd superficie de dibujo (canvas)
     */
    public Ventana(final String titulo, final SuperficieDibujo sd) {
        this.titulo = titulo;

        BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslucida("/recursos/iconos/logo.png");
        this.icono = new ImageIcon(imagen);

        this.configurarVentana(sd);

    }

    // ESTABLECIENDO PARAMETROS DE LA VENTANA
    private void configurarVentana(final SuperficieDibujo sd) {
        this.setTitle(this.titulo);
        this.setIconImage(this.icono.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.add(sd, BorderLayout.CENTER);
        this.setUndecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

}
