package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;

import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import eus.ehu.euskoflix.packControlador.PropertiesManager;

import eus.ehu.euskoflix.packControlador.ControladorVista;

import java.awt.event.*;
import java.io.IOException;

public class VentanaCargaDatos extends JFrame {

	private JPanel contentPane;
	private JTable tableUsers;
	private JTable tableFilms;

	/**
	 * Create the frame.
	 */
	public VentanaCargaDatos() {
		if(!WebLookAndFeel.isInstalled()){
			WebLookAndFeel.install();
		}
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResourceAsStream(PropertiesManager.getInstance().getPathToMainIcon())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setTitle("Euskoflix");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 500)/2, (screenSize.height - 500)/2, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		//Tab Usuarios
		TableModel modelUser = new DefaultTableModel(ControladorVista.getInstance().datosUsuario(),ControladorVista.getInstance().getCabeceraUsers()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
        tableUsers = new JTable(modelUser);
        tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableUsers.getColumnModel().getColumn(0).setPreferredWidth(65);
		tableUsers.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableUsers.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableUsers.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableUsers.getTableHeader().setReorderingAllowed(false);
		tableUsers.setRowSelectionAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tableUsers);
        JPanel panelUser = new JPanel();
        panelUser.setLayout(new BorderLayout());
        panelUser.add(scrollPane, BorderLayout.CENTER);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		try {
			tabbedPane.addTab("Usuarios", new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(PropertiesManager.getInstance().getPathToUserIcon()))), panelUser, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		this.setMinimumSize(new Dimension(450, 300));

		//Tab Pelis
		TableModel modelPeli = new DefaultTableModel(ControladorVista.getInstance().datosPelis(),ControladorVista.getInstance().getCabeceraFilms()) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableFilms = new JTable(modelPeli);
		tableFilms.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFilms.getColumnModel().getColumn(0).setPreferredWidth(120);
		tableFilms.getColumnModel().getColumn(1).setPreferredWidth(330);
		tableFilms.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableFilms.getTableHeader().setReorderingAllowed(false);
		tableFilms.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				JTable table =(JTable) mouseEvent.getSource();
				Point point = mouseEvent.getPoint();
				int row = table.rowAtPoint(point);
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
				    int id = Integer.parseInt(table.getModel().getValueAt(row,0).toString());
                    VentanaCargaDatos.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    InformacionExtraView i  = new InformacionExtraView(VentanaCargaDatos.this,true, id);
                    VentanaCargaDatos.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    i.setVisible(true);
				}
			}
		});
		JPanel panelFilm = new JPanel();
		panelFilm.setLayout(new BorderLayout());
		JScrollPane scrollPane1 = new JScrollPane(tableFilms);
		panelFilm.add(scrollPane1,BorderLayout.CENTER);
		try {
			tabbedPane.addTab("Peliculas", new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream(PropertiesManager.getInstance().getPathToMovieIcon()))), panelFilm, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
