package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class VentanaRegistro extends JDialog {

	private JPanel contentPane;
	private JPanel panelBotones;
	private JTextField txtApellido;
	private JTextField txtNombre;
	private JPasswordField pass1;
	private JPasswordField pass2;
	private JButton btnRegistro;
	private JLabel lblGif;
	private JPanel jPanel1;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblPass;
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
		java.awt.GridBagConstraints gridBagConstraints;

		lblGif = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		lblNombre = new javax.swing.JLabel();
		txtNombre = new javax.swing.JTextField();
		lblApellido = new javax.swing.JLabel();
		txtApellido = new javax.swing.JTextField();
		lblPass = new javax.swing.JLabel();
		pass1 = new javax.swing.JPasswordField();
		lblConfirmPass = new javax.swing.JLabel();
		pass2 = new javax.swing.JPasswordField();

		setLayout(new java.awt.BorderLayout());

		lblGif.setHorizontalAlignment(SwingConstants.CENTER);
		lblGif.setIcon(new ImageIcon(VentanaRegistro.this.getClass().getResource(PropertiesManager.getInstance().getPathToEuskoflixGif())));
		add(lblGif, java.awt.BorderLayout.NORTH);

		jPanel1.setLayout(new java.awt.GridBagLayout());

		lblNombre.setText("Nombre");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(lblNombre, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(txtNombre, gridBagConstraints);

		lblApellido.setText("Apellido");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(lblApellido, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(txtApellido, gridBagConstraints);

		lblPass.setText("Contraseña");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(lblPass, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(pass1, gridBagConstraints);

		lblConfirmPass.setText("Confirmar Contraseña");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
		gridBagConstraints.weightx = 0.3;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(lblConfirmPass, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		jPanel1.add(pass2, gridBagConstraints);

		contentPane.add(jPanel1, java.awt.BorderLayout.CENTER);

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
