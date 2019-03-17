package eus.ehu.euskoflix.packVista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.Informacion;

import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class VentanaDatosExtra extends JFrame {

	private JPanel contentPane;
	private int film;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void ventanaDatosExtra(int peli) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					VentanaDatosExtra frame = new VentanaDatosExtra(peli);
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
	public VentanaDatosExtra(int pFilm) {
		this.film = pFilm;
		Informacion infoExtra = ControladorVista.getInstance().getInformacionExtra(pFilm);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setMinimumSize(new Dimension(450, 300));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//Sinopsis
		JTextPane sinopsis = new JTextPane();
		contentPane.add(sinopsis, BorderLayout.WEST);
		sinopsis.setEditable(false);
		sinopsis.setText(infoExtra.getSinopsis());
		//Imagen
	      Image logo = infoExtra.getPoster();
          ImageIcon icon = new ImageIcon(logo);
          JLabel icono = new JLabel(icon);
          contentPane.add(icono, BorderLayout.EAST);
          icono.setSize(icon.getIconWidth(),icon.getIconHeight());

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		//Tags
		JPanel panelTags = new JPanel();
		tabbedPane.addTab("Tags", null, panelTags, null);
		JTable tableTags= new JTable(ControladorVista.getInstance().datosTags(pFilm),ControladorVista.getInstance().getCabeceraTags());//LAs tablas extra
		panelTags.add(tableTags);
		//Ratings
		JPanel panelRatings = new JPanel();
		tabbedPane.addTab("Valoraciones", null, panelRatings, null);
		JTable tableRatings= new JTable(ControladorVista.getInstance().datosRatings(pFilm),ControladorVista.getInstance().getCabeceraRatings());
		panelRatings.add(tableRatings);
	}

}
