package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;


public class VentanaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel panel;
    private JTextField txtUser;
    private JTextField txtPass;
    private JButton entrar;

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
        txtUser.setText("Nombre de usuario");
        txtUser.setHorizontalAlignment(JTextField.CENTER);

        txtPass = new JTextField();
        txtPass.setBounds(225, 100, 150, 25);
        txtPass.setText("Contraseña");
        txtPass.setHorizontalAlignment(JTextField.CENTER);

        entrar = new JButton("Entrar");
        entrar.setBounds(150, 135, 100, 25);
        panel.add(txtUser);
        panel.add(txtPass);
        panel.add(entrar);
        centrar();
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
    
    public void anadirActionListener(ActionListener pLoguearseListener) {
    	this.entrar.addActionListener(pLoguearseListener);
    }

    public JTextField getTxtUser() { return this.txtUser; }

    public JTextField getTxtPass() { return this.txtPass; }
    
    public int getUsuario() {
    	int username = -1;
    	try {
    		username = Integer.parseInt(this.txtUser.getText());
    	}catch(NumberFormatException e){
    		JOptionPane.showMessageDialog(this,
	                "El nombre de usuario debe ser tu ID.", "Error login",
	                JOptionPane.INFORMATION_MESSAGE);
    	}
    	return username; }
    
    public String getContra() { return this.txtPass.getText(); }

}
