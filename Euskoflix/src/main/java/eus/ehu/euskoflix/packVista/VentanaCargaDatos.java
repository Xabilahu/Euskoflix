package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;

import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.events.MouseEvent;

import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packModelo.Cartelera;

import java.awt.event.*;
import java.lang.Object;
import java.util.EventObject;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;

public class VentanaCargaDatos extends JFrame {

	private JPanel contentPane;
	private JPanel contentPaneExtra;
	private JTable tableUsers;
	private JTable tableFilms;
	private JFrame frameInfo;
	private JTable tableTags;
	private JTable tableRatings;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		GestionDatos.getInstance().cargarDatos();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCargaDatos frame = new VentanaCargaDatos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaCargaDatos() {
		WebLookAndFeel.install();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
        tableUsers = new JTable(ControladorVista.getInstance().datosUsuario(),ControladorVista.getInstance().getCabeceraUsers());
        JScrollPane scrollPane = new JScrollPane(tableUsers);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Usuarios", null, panel, null);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		this.setMinimumSize(new Dimension(450, 300));
		//Pelis
		JPanel panel1 = new JPanel();
		tabbedPane.addTab("Peliculas", null, panel1, null);
		tableFilms = new JTable(ControladorVista.getInstance().datosPelis(),ControladorVista.getInstance().getCabeceraFilms());
		JScrollPane scrollPane1 = new JScrollPane(tableFilms);
		panel1.add(scrollPane1);
		//abrir ventana de info de peli
		tableFilms.addMouseListener(new MouseAdapter() {
			
	         @Override
			public void mousePressed(MouseEvent e) {
	        	 if (e.getClickCount()== 2) {
		               String[] seleccionado = e.getSource();
		               int IdPeli = Integer.parseInt(seleccionado[0]);
		             //Ventana nueva
		               frameInfo = new JFrame();
		               frameInfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		       		   frameInfo.setBounds(100, 100, 450, 300);
		               frameInfo.setVisible(true);
		               //Tabla Rating 
		               tableRatings= new JTable(ControladorVista.getInstance().datosRatings(),ControladorVista.getInstance().getCabeceraRatings());
		               JScrollPane scrollPaneExtra1 = new JScrollPane(tableRatings);
		               frameInfo.add(scrollPaneExtra1, BorderLayout.CENTER);
		               //Tabla Tag
		               tableTags= new JTable(ControladorVista.getInstance().datosTags(),ControladorVista.getInstance().getCabeceraTags());//LAs tablas extra
		               contentPaneExtra = new JPanel();
		       			contentPaneExtra.setBorder(new EmptyBorder(5, 5, 5, 5));
		       			contentPaneExtra.setLayout(new BorderLayout(0, 0));
		       			frameInfo.setContentPane(contentPaneExtra);
		       			JScrollPane scrollPaneExtra = new JScrollPane(tableTags);
		       			
		       			
		       			
		       			//foto en el centro
		       			Image logo = ImageIO.read(in);
		                ImageIcon icon = new ImageIcon(logo);
		                JLabel icono = new JLabel(icon);
		                icono.setSize(icon.getIconWidth(),icon.getIconHeight());
		                frameInfo.add(icono,BorderLayout.CENTER);
		                //sinopsis en el este
		                JTextArea sinopsis = new JTextArea(Cartelera.getInstance().getPeliculaPorId(IdPeli).getInfo().toString());
		                frameInfo.add(sinopsis,BorderLayout.EAST);
		                frameInfo.setMinimumSize(new Dimension(450,300));
			}

			
		
	}
	}
