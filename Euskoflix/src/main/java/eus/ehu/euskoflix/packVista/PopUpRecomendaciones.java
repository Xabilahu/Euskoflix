package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class PopUpRecomendaciones extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelPelis;
    private JScrollPane scrollPane;
    private PanelPelis panelRecomendaciones;
    private JPanel panelBoton;
    private JButton btnCerrar;

    public PopUpRecomendaciones(int pIdUsuario, boolean valoraciones, Object[][] pPelisRecomendadas) {
        if (!WebLookAndFeel.isInstalled()) {
            WebLookAndFeel.install();
        }
        try {
            this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream(PropertiesManager.getInstance().getPathToMainIcon())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension dim = new Dimension(855,600);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        this.setTitle("Euskoflix");
        this.setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - dim.width) / 2, (screenSize.height - dim.height) / 2);
        this.initComponents(pIdUsuario, valoraciones, pPelisRecomendadas);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(int pIdUsuario, boolean valoraciones, Object[][] pPelisRecomendadas) {
        this.contentPane = new JPanel(new BorderLayout());
        this.panelPelis = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.panelRecomendaciones = new PanelPelis();
        this.panelRecomendaciones.setLayout(new GridLayout((int) Math.ceil(pPelisRecomendadas.length / 5.0), 5, 5, 5));
        this.panelRecomendaciones.generarPanelesPelis(valoraciones, pPelisRecomendadas);
        this.scrollPane.setViewportView(this.panelRecomendaciones);
        this.panelPelis.add(scrollPane, BorderLayout.CENTER);
        this.panelPelis.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peliculas Recomendadas", 0, 0, new java.awt.Font("Times New Roman", 1, 11)));
        this.contentPane.add(panelPelis, BorderLayout.CENTER);
        this.panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnCerrar = new JButton("Cerrar");
        this.btnCerrar.setFont(new java.awt.Font("Times New Roman", 0, 14));
        this.panelBoton.add(this.btnCerrar);
        this.btnCerrar.addActionListener(e -> {
            if (valoraciones) {
                HashMap<Integer,Double> ratings = this.panelRecomendaciones.getRatings();
                if (ratings.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe valorar al menos una pelicula para hacer uso del recomendador Euskoflix.", "Valoración fallida", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ControladorVista.getInstance().cargarValoracionesUsuarioNuevo(pIdUsuario, this.panelRecomendaciones.getRatings());
                    dispose();
                }
            } else {
                dispose();
            }
        });
        this.contentPane.add(this.panelBoton,BorderLayout.SOUTH);
        this.setContentPane(this.contentPane);
    }

}
