package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;
import eus.ehu.euskoflix.packModelo.packFiltro.TipoRecomendacion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PopUpRecomendaciones extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelPelis;
    private JScrollPane scrollPane;
    private PanelPelis panelRecomendaciones;
    private JPanel panelBoton;
    private JButton btnCerrar;

    public PopUpRecomendaciones(Object[][] pPelisRecomendadas) {
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
        this.initComponents(pPelisRecomendadas);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(Object[][] pPelisRecomendadas) {
        this.contentPane = new JPanel(new BorderLayout());
        this.panelPelis = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.panelRecomendaciones = new PanelPelis();
        this.panelRecomendaciones.setLayout(new GridLayout((int) Math.ceil(pPelisRecomendadas.length / 5.0), 5, 5, 5));
        this.panelRecomendaciones.generarPanelesPelis(pPelisRecomendadas);
        this.scrollPane.setViewportView(this.panelRecomendaciones);
        this.panelPelis.add(scrollPane, BorderLayout.CENTER);
        this.panelPelis.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peliculas Recomendadas", 0, 0, new java.awt.Font("Times New Roman", 1, 11)));
        this.contentPane.add(panelPelis, BorderLayout.CENTER);
        this.panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnCerrar = new JButton("Cerrar");
        this.btnCerrar.setFont(new java.awt.Font("Times New Roman", 0, 14));
        this.panelBoton.add(this.btnCerrar);
        this.btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.contentPane.add(this.panelBoton,BorderLayout.SOUTH);
        this.setContentPane(this.contentPane);
    }

}
