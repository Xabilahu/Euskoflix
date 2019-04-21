package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class VentanaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panel;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton entrar;
    private JButton registrar;

    public VentanaLogin() {
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
        setBounds((screenSize.width - 500) / 2, (screenSize.height - 500) / 2, 400, 200);
        this.initComponents();
        centrar();
    }

    private void initComponents() {
        panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        InputStream in = VentanaLogin.class.getResourceAsStream(PropertiesManager.getInstance().getPathToLoginLogo());
        Image logo = null;
        try {
            logo = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(logo);
        JLabel icono = new JLabel(icon);
        icono.setBounds(60, 10, icon.getIconWidth(), icon.getIconHeight());
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                icono.requestFocus();
            }
        });
        this.add(icono);

        txtUser = new JTextField();
        txtUser.setBounds(25, 100, 150, 25);
        txtUser.setText("ID de usuario");
        txtUser.setHorizontalAlignment(JTextField.CENTER);

        txtPass = new JPasswordField();
        txtPass.setBounds(225, 100, 150, 25);
        txtPass.setText("Contraseña");
        txtPass.setHorizontalAlignment(JTextField.CENTER);

        entrar = new JButton("Entrar");
        entrar.setBounds(90, 135, 100, 25);

        registrar = new JButton("Registrar");
        registrar.setBounds(210, 135, 100, 25);
        panel.add(txtUser);
        panel.add(txtPass);
        panel.add(entrar);
        panel.add(registrar);
        //TODO: layout
    }

    private void centrar() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize(); //Tamaño del frame actual (ancho x alto)
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    public void anadirFocusListener(FocusListener pListenForFocusUser, FocusListener pListenForFocusPass) {
        this.txtUser.addFocusListener(pListenForFocusUser);
        this.txtPass.addFocusListener(pListenForFocusPass);
    }

    public void addKeyListener(KeyListener pListener) {
        this.txtPass.addKeyListener(pListener);
    }

    public void addLoginListener(ActionListener pLoguearseListener) {
    	this.entrar.addActionListener(pLoguearseListener);
    }

    public void addRegistroListener(ActionListener pListener) {
        this.registrar.addActionListener(pListener);
    }

    public JTextField getTxtUser() { return this.txtUser; }

    public JPasswordField getTxtPass() {
        return this.txtPass;
    }
    
    public int getUsuario() {
    	int username = Integer.MIN_VALUE;
    	try {
    		username = Integer.parseInt(this.txtUser.getText());
    	}catch(NumberFormatException e){
    		JOptionPane.showMessageDialog(this,
	                "El nombre de usuario debe ser tu ID.", "Error login",
	                JOptionPane.INFORMATION_MESSAGE);
    	}
    	return username; }
    
    public String getContra() {
        return new String(this.txtPass.getPassword());
    }

}
