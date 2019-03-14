package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packControlador.GestionDatos;

public class VentanaCargaDatos extends JFrame {

	private JPanel contentPane;
	private JTable tableUsers;
	private JTable tableFilms;

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
	}

}
