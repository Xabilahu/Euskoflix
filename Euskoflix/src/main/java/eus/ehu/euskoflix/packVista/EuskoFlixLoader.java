package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packControlador.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class EuskoFlixLoader extends JFrame {


    public EuskoFlixLoader(){
        WebLookAndFeel.install ();
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());

        InputStream in = EuskoFlixLoader.class.getResourceAsStream(PropertiesManager.getInstance().getPathToLogo());
        try {
            Image logo = ImageIO.read(in);
            ImageIcon icon = new ImageIcon(logo);
            JLabel icono = new JLabel(icon);
            icono.setSize(icon.getIconWidth(),icon.getIconHeight());

            this.add(icono,BorderLayout.CENTER);
            JProgressBar progressBar = new JProgressBar();
            progressBar.setBackground(Color.white);
            progressBar.setIndeterminate(true);
            progressBar.setLayout(new BorderLayout());

            JLabel texto = new JLabel("Cargando...");
            texto.setHorizontalAlignment(JLabel.CENTER);
            texto.setVerticalAlignment(JLabel.CENTER);
            progressBar.add(texto,BorderLayout.CENTER);

            this.add(progressBar,BorderLayout.SOUTH);
            Dimension d = new Dimension(icon.getIconWidth(),icon.getIconHeight()+progressBar.getHeight());
            System.out.println(d);
            this.setMinimumSize(d);
            this.setMaximumSize(d);
            this.setPreferredSize(d);
            centrar();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void centrar() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize(); //Tamaño del frame actual (ancho x alto)
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public static void main(String[] args) {
        new EuskoFlixLoader();
    }

}
