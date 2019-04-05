package eus.ehu.euskoflix.packVista;

import com.alee.laf.WebLookAndFeel;

import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	    }try {
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
        
        this.add(icono);
        
        txtUser = new JTextField();
        txtUser.setBounds(25, 100, 150, 25);
        txtUser.setText("Nombre de usuario");
        txtUser.setHorizontalAlignment(JTextField.CENTER);
        txtUser.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {txtUser.setText("");}
			
			@Override
			public void focusLost(FocusEvent e) {	}
			
        });
        
        
        txtPass = new JTextField();
        txtPass.setBounds(225, 100, 150, 25);
        txtPass.setText("Contraseña");
        txtPass.setHorizontalAlignment(JTextField.CENTER);
        txtPass.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {txtPass.setText("");}
			
			@Override
			public void focusLost(FocusEvent e) {	}
			
        });
        
        entrar = new JButton("Entrar");
        entrar.setBounds(150, 135, 100, 25);
        panel.add(txtUser);
        panel.add(txtPass);
        panel.add(entrar);
        centrar();
        this.setVisible(true);
	
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
	
}
