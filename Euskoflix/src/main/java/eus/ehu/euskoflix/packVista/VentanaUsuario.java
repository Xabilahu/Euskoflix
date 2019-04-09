package eus.ehu.euskoflix.packVista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alee.laf.WebLookAndFeel;

import eus.ehu.euskoflix.packDatos.PropertiesManager;

public class VentanaUsuario extends JFrame {
	
	 private static final long serialVersionUID = 1L;
	 
	 private JPanel contentPane;
	 private JLabel nombre;
	 private JLabel apellido;
	 private JTable tablePeliculasVistas;
	 private JComboBox algoritmos;
	 private JButton recomendar;
	 
	 public VentanaUsuario(String pUsuario, String[][] pPeliculasVistas) {
		 if (!WebLookAndFeel.isInstalled()) {
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
	        setBounds((screenSize.width - 500) / 2, (screenSize.height - 500) / 2, 500, 500);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(new BorderLayout(0, 0));
	        setContentPane(contentPane);
	        
	        String[] cabeceraUsers = new String[]{"Pelicula", "Puntuación"};
	        TableModel modelPeliculasVistas = new DefaultTableModel(pPeliculasVistas, cabeceraUsers) {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        tablePeliculasVistas = new JTable(modelPeliculasVistas);
	        tablePeliculasVistas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	        tablePeliculasVistas.getColumnModel().getColumn(0).setPreferredWidth(400);
	        tablePeliculasVistas.getColumnModel().getColumn(1).setPreferredWidth(50);
	        tablePeliculasVistas.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	        
	        JPanel contentPane = new JPanel();
	        contentPane.setLayout(new BorderLayout());
	        JScrollPane scrollPane1 = new JScrollPane(contentPane);
	        contentPane.add(scrollPane1, BorderLayout.CENTER);
		 
	 }

}
