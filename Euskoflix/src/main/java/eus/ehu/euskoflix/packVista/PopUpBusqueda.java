package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PopUpBusqueda extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelPelis;
    private JScrollPane scrollPane;
    private PanelPelis panelEncontradas;
    private JPanel panelBoton;
    private JButton btnCerrar;

    public PopUpBusqueda(Object[][] pPelisEncontradas) {
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
        this.initComponents(pPelisEncontradas);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(Object[][] pPelisEncontradas) {
        this.contentPane = new JPanel(new BorderLayout());
        this.panelPelis = new JPanel(new BorderLayout());
        this.scrollPane = new JScrollPane();
        this.panelEncontradas = new PanelPelis();
        this.panelEncontradas.setLayout(new GridLayout((int) Math.ceil(pPelisEncontradas.length / 5.0), 5, 5, 5));
        this.panelEncontradas.generarPanelesPelis(false, pPelisEncontradas);
        this.scrollPane.setViewportView(this.panelEncontradas);
        this.panelPelis.add(scrollPane, BorderLayout.CENTER);
        this.panelPelis.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peliculas Encontradas", 0, 0, new java.awt.Font("Times New Roman", 1, 11)));
        this.contentPane.add(panelPelis, BorderLayout.CENTER);
        this.panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.btnCerrar = new JButton("Cerrar");
        this.btnCerrar.setFont(new java.awt.Font("Times New Roman", 0, 14));
        this.panelBoton.add(this.btnCerrar);
        this.btnCerrar.addActionListener(e -> dispose());
        this.contentPane.add(this.panelBoton,BorderLayout.SOUTH);
        this.setContentPane(this.contentPane);
    }

}