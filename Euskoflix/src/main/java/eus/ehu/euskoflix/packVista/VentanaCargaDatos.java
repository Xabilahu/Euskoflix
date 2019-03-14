package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import eus.ehu.euskoflix.packControlador.BaseDatos;
import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packModelo.GestionDatos;

public class VentanaCargaDatos extends JFrame {

	private JPanel contentPane;
	private JTable tableUsers;

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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Usuarios", null, panel, null);

		tableUsers = new JTable(ControladorVista.getInstance().datosUsuario(),ControladorVista.getInstance().getCabeceraUsers());
		JScrollPane scrollPane = new JScrollPane(tableUsers);
		panel.add(scrollPane);
	}

}
