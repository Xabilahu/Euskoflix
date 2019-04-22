package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.event.ActionListener;

public class VentanaRegistro extends JDialog {

	private JPanel contentPane;
	private JPanel panelRegistro;
	private JPanel panelBotones;
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JPasswordField pass1;
	private JPasswordField pass2;
	private JButton btnRegistro;
	private JLabel lblGif;
	private JPanel panelNombre;
	private JLabel lblNombre;
	private JPanel panelApellido;
	private JPanel panelPass;
	private JLabel lblApellido;
	private JLabel lblPass;
	private JPanel panelConfirmPass;
	private JLabel lblConfirmPass;
	private JButton btnCancelar;
	
	public VentanaRegistro() {
		if (!WebLookAndFeel.isInstalled()) {
			WebLookAndFeel.install();
		}
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.initComponents();
		this.setVisible(true);
	}

	private void initComponents() {
		lblGif = new JLabel();
		lblGif.setHorizontalAlignment(SwingConstants.CENTER);
		lblGif.setIcon(new ImageIcon(VentanaRegistro.this.getClass().getResource(PropertiesManager.getInstance().getPathToGif())));
		contentPane.add(lblGif, BorderLayout.NORTH);

		panelRegistro = new JPanel();
		contentPane.add(panelRegistro, BorderLayout.CENTER);
		panelRegistro.setLayout(new GridLayout(4,1));

		panelNombre = new JPanel();
		panelRegistro.add(panelNombre, "1, 2, fill, fill");

		lblNombre = new JLabel("Nombre");
		panelNombre.add(lblNombre);
		Component horizontalStrut_2 = Box.createHorizontalStrut(82);
		panelNombre.add(horizontalStrut_2);

		panelApellido = new JPanel();
		panelRegistro.add(panelApellido, "1, 3, fill, fill");

		lblApellido = new JLabel("Apellido");
		panelApellido.add(lblApellido);

		Component horizontalStrut = Box.createHorizontalStrut(80);
		panelApellido.add(horizontalStrut);

		txtApellido = new JTextField();
		panelApellido.add(txtApellido);
		txtApellido.setColumns(20);

		txtNombre = new JTextField();
		panelNombre.add(txtNombre);
		txtNombre.setColumns(20);

		panelPass = new JPanel();
		panelRegistro.add(panelPass, "1, 4, fill, fill");

		lblPass = new JLabel("Contraseña");
		panelPass.add(lblPass);

		Component horizontalStrut_1 = Box.createHorizontalStrut(62);
		panelPass.add(horizontalStrut_1);

		pass1 = new JPasswordField();
		pass1.setColumns(20);
		panelPass.add(pass1);

		panelConfirmPass = new JPanel();
		panelRegistro.add(panelConfirmPass, "1, 5, fill, fill");

		lblConfirmPass = new JLabel("Confirmar Contraseña");
		panelConfirmPass.add(lblConfirmPass);

		pass2 = new JPasswordField();
		pass2.setColumns(20);
		panelConfirmPass.add(pass2);

		panelBotones = new JPanel();
		contentPane.add(panelBotones, BorderLayout.SOUTH);

		btnRegistro = new JButton("Registrar");
		panelBotones.add(btnRegistro);

		btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnCancelar);
	}

	public void addCancelarLisener(ActionListener pListener) {
		this.btnCancelar.addActionListener(pListener);
	}

	public void addRegistrarListener(ActionListener pListener) {
		this.btnRegistro.addActionListener(pListener);
	}

	public String getPassword() {
		String pwd1 = new String(this.pass1.getPassword()), pwd2 = new String(this.pass2.getPassword());
		if (pwd1.equals(pwd2) && !pwd1.isEmpty()) {
			return pwd1;
		} else if(pwd1.isEmpty() || pwd2.isEmpty()) {
			JOptionPane.showMessageDialog(this.contentPane, "Debe introducir la contraseña.", "Error de registro", JOptionPane.ERROR_MESSAGE);
			if (pwd1.isEmpty()) this.pass1.requestFocus();
			else this.pass2.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this.contentPane, "Las contraseñas no coinciden.", "Error de registro", JOptionPane.ERROR_MESSAGE);
			this.pass1.requestFocus();
		}
		return null;
	}

	public String getNombre() {
		String nombre = this.txtNombre.getText();
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this.contentPane, "Debe introducir su nombre.", "Error de registro", JOptionPane.ERROR_MESSAGE);
			this.txtNombre.requestFocus();
			return null;
		}
		return nombre;
	}

	public String getApellido() {
		String apellido = this.txtApellido.getText();
		if (apellido.isEmpty()) {
			JOptionPane.showMessageDialog(this.contentPane, "Debe introducir su apellido.", "Error de registro", JOptionPane.ERROR_MESSAGE);
			this.txtApellido.requestFocus();
			return null;
		}
		return apellido;
	}
	//TODO: setModal, when modal no listeners are added in ControladorVista
}
