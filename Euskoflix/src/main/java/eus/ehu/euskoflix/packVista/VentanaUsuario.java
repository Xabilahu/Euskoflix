package eus.ehu.euskoflix.packVista;

import java.awt.*;
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
	private JPanel paneFilmVContainer;
	private JPanel paneRecomendadas;
	private JScrollPane scrollPaneRec;
	private JPanel paneFilmRContainer;
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
	 
	public VentanaUsuario(String[] pUsuario, Object[][] pPeliculasVistas, Object[][] pPeliculasRecomendadas) {
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
		this.initComponents(pPeliculasVistas, pPeliculasRecomendadas);
		lblUsuario.setText(pUsuario[0] + " " + pUsuario[1]);
        this.pack();
	 	this.setVisible(true);
	}
 
	 private void initComponents(Object[][] pPeliculasVistas, Object[][] pPeliculasRecomendadas) {

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
        scrollPaneVst.setViewportBorder(null);
        paneVistas = new JPanel();
        panelPelis.add(paneVistas);
        paneVistas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peliculas Vistas", 0, 0, new java.awt.Font("Times New Roman", 1, 11)));
        paneVistas.setLayout(new BorderLayout(0, 0));
        
        paneFilmVContainer = new JPanel();
        paneFilmVContainer.setBorder(null);
        scrollPaneVst.setViewportView(paneFilmVContainer);
        paneVistas.add(scrollPaneVst);
        paneFilmVContainer.setLayout(new GridLayout());

        this.generarPanelesPelis(true, pPeliculasVistas);

        paneRecomendadas = new JPanel();
        panelPelis.add(paneRecomendadas);
        paneRecomendadas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Peliculas Recomendadas", 0, 0, new java.awt.Font("Times New Roman", 1, 11)));
        paneRecomendadas.setLayout(new BorderLayout(0, 0));
        
        scrollPaneRec = new JScrollPane();
        paneRecomendadas.add(scrollPaneRec, BorderLayout.CENTER);
        
        paneFilmRContainer = new JPanel();
        scrollPaneRec.setViewportView(paneFilmRContainer);
        paneFilmRContainer.setLayout(new GridLayout(1, 5, 5, 5));

        this.generarPanelesPelis(false, pPeliculasRecomendadas);

        panelRecomendaciones = new JPanel();
        contentPane.add(panelRecomendaciones, BorderLayout.SOUTH);
        
        String[] options = {"Personas", "Peliculas", "Contenido"};
        comboBox = new JComboBox(options);
        panelRecomendaciones.add(comboBox);
        
        spinner = new JSpinner();
        spinner.getEditor().setPreferredSize(new Dimension(50,22));
        panelRecomendaciones.add(spinner);
        
        btnRecommend = new JButton("Obtener más recomendaciones");
        panelRecomendaciones.add(btnRecommend);
        
	 }

	 private void generarPanelesPelis(boolean pVistas, Object[][] pPeliculas) {
	    JPanel contenedor;
	    if (pVistas) {
	        contenedor = paneFilmVContainer;
        } else {
	        contenedor = paneFilmRContainer;
        }
	    for (Object[] obj : pPeliculas) {
            JPanel panel = new JPanel();
            contenedor.add(panel);
            JLabel icon = new JLabel();
            icon.setHorizontalAlignment(SwingConstants.CENTER);
            icon.setLayout(new BorderLayout(0, 0));
            icon.setIcon(new ImageIcon((Image) obj[0]));
            panel.add(icon);
        }
     }
	 
	 private class MarqueeLabel extends JLabel {  
//		   public int LEFT_TO_RIGHT = 1;  
//		   public  int RIGHT_TO_LEFT = 2;
		   private int Option;
		   private int Speed;

		   public MarqueeLabel(String text, int Option, int Speed) {  
		     this.Option = Option;  
		     this.Speed = Speed;  
		     this.setText(text);  
		   }

		   @Override
           protected void paintComponent(Graphics g) {
		     if (Option == 1) {  
		       g.translate((int) ((System.currentTimeMillis() / Speed) % (getWidth() * 2) - getWidth()), 0);  
		     } else if (Option == 2) {  
		       g.translate((int) (getWidth() - (System.currentTimeMillis() / Speed) % (getWidth() * 2)), 0);  
		     }  
		     super.paintComponent(g);  
		     repaint(5);  
		   }  
		 }  
	 

}
