package eus.ehu.euskoflix.packVista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

public class VentanaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel paneVistas;
    private JScrollPane scrollPaneVst;
    private JPanel panelPelis;
    private PanelPelis paneFilmVContainer;
    private JPanel paneRecomendadas;
    private JScrollPane scrollPaneRec;
    private PanelPelis paneFilmRContainer;
    private JPanel panelRecomendaciones;
    private JComboBox<String> comboBox;
    private JButton btnRecommend;
    private JSpinner spinner;
    private JPanel panelSuperior;
    private JButton btnBusqueda;
    private JTextField txtBusqueda;
    private JPanel panelBusqueda;
    private JLabel lblUsuario;
    private Component horizontalStrut;
    private JPanel panelUsuario;

    public VentanaUsuario(String[] pUsuario, Object[][] pPeliculasVistas, Object[][] pPeliculasRecomendadas, int pTotalPelis) {
        if (!WebLookAndFeel.isInstalled()) {
            WebLookAndFeel.install();
        }
        try {
            this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream(PropertiesManager.getInstance().getPathToMainIcon())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension dim = new Dimension(855,670);
        this.setMinimumSize(dim);
        this.setMaximumSize(dim);
        this.setPreferredSize(dim);
        this.setTitle("Euskoflix");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - dim.width) / 2, (screenSize.height - dim.height) / 2);
        contentPane = new JPanel();
        this.setContentPane(contentPane);
        this.initComponents(pPeliculasVistas, pPeliculasRecomendadas, pTotalPelis);
        lblUsuario.setText(pUsuario[0] + " " + pUsuario[1]);
        this.pack();
        this.setVisible(true);
    }

    private void initComponents(Object[][] pPeliculasVistas, Object[][] pPeliculasRecomendadas, int pTotalPelis) {

        contentPane.setLayout(new BorderLayout(0, 0));

        panelSuperior = new JPanel();
        contentPane.add(panelSuperior, BorderLayout.NORTH);
        panelSuperior.setLayout(new BorderLayout(0, 0));

        panelBusqueda = new JPanel();
        panelSuperior.add(panelBusqueda, BorderLayout.WEST);

        txtBusqueda = new JTextField();
        panelBusqueda.add(txtBusqueda);
        txtBusqueda.setColumns(30);

        btnBusqueda = new JButton("Buscar Película");
        panelBusqueda.add(btnBusqueda);

        panelUsuario = new JPanel();
        panelSuperior.add(panelUsuario, BorderLayout.EAST);
        panelUsuario.setLayout(new BorderLayout(0, 0));

        lblUsuario = new JLabel();
        panelUsuario.add(lblUsuario, BorderLayout.CENTER);
        lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);

        horizontalStrut = Box.createHorizontalStrut(10);
        panelUsuario.add(horizontalStrut, BorderLayout.EAST);

        panelPelis = new JPanel();
        contentPane.add(panelPelis, BorderLayout.CENTER);
        panelPelis.setLayout(new GridLayout(0, 1, 0, 2));

        scrollPaneVst = new JScrollPane();
        paneVistas = new JPanel();
        panelPelis.add(paneVistas);
        paneVistas.setBorder(BorderFactory.createTitledBorder(null, "Peliculas Vistas", 0, 0, new Font("Times New Roman", 1, 11)));
        paneVistas.setLayout(new BorderLayout());

        paneFilmVContainer = new PanelPelis();
        scrollPaneVst.setViewportView(paneFilmVContainer);
        paneVistas.add(scrollPaneVst);
        paneFilmVContainer.setLayout(new GridLayout(1,0, 5, 5));

        paneFilmVContainer.generarPanelesPelis(false, pPeliculasVistas);

        paneRecomendadas = new JPanel();
        panelPelis.add(paneRecomendadas);
        paneRecomendadas.setBorder(BorderFactory.createTitledBorder(null, "Peliculas Recomendadas", 0, 0, new Font("Times New Roman", 1, 11)));
        paneRecomendadas.setLayout(new BorderLayout());

        scrollPaneRec = new JScrollPane();
        paneRecomendadas.add(scrollPaneRec, BorderLayout.CENTER);

        paneFilmRContainer = new PanelPelis();
        scrollPaneRec.setViewportView(paneFilmRContainer);
        paneFilmRContainer.setLayout(new GridLayout(1, 0, 5, 5));

        paneFilmRContainer.generarPanelesPelis(false, pPeliculasRecomendadas);

        panelRecomendaciones = new JPanel();
        contentPane.add(panelRecomendaciones, BorderLayout.SOUTH);

        String[] options = {"Personas", "Peliculas", "Contenido"};
        comboBox = new JComboBox(options);
        panelRecomendaciones.add(comboBox);

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, pTotalPelis - pPeliculasVistas.length, 1);
        spinner = new JSpinner(model);
        spinner.getEditor().setPreferredSize(new Dimension(50,22));
        panelRecomendaciones.add(spinner);

        btnRecommend = new JButton("Obtener más recomendaciones");
        panelRecomendaciones.add(btnRecommend);

    }

    public void addBusquedaListener(ActionListener pListener) {
        this.btnBusqueda.addActionListener(pListener);
    }
    
    public void addRecomendacionListener(ActionListener pListener) {
        this.btnRecommend.addActionListener(pListener);
    }
    
    public String getBusqueda() {
        return this.txtBusqueda.getText();
    }
}
