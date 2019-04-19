package eus.ehu.euskoflix.packVista;

import eus.ehu.euskoflix.packControlador.ControladorVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PanelPelis extends JPanel {

    public void generarPanelesPelis( Object[][] pPeliculas) {
        for (Object[] obj : pPeliculas) {
            JPanel panel = new JPanel();
            this.add(panel);
            Dimension dim = new Dimension(154,240);
            panel.setPreferredSize(dim);
            panel.setMinimumSize(dim);
            panel.setMaximumSize(dim);
            JLabel icon = new JLabel();
            icon.setLayout(new OverlayLayout(panel));
            icon.setHorizontalAlignment(SwingConstants.CENTER);
            icon.setLayout(new BorderLayout(0, 0));
            icon.setIcon(new ImageIcon((Image) obj[0]));
            panel.add(icon);
            JLabel titulo = new MarqueeLabel((String)obj[2], 2, 20);
            titulo.setVisible(false);
            JPanel panelTitulo = new JPanel(new BorderLayout());
            panelTitulo.add(titulo, BorderLayout.SOUTH);
            panelTitulo.setOpaque(false);
            titulo.setBackground(Color.WHITE);
            titulo.setOpaque(true);
            icon.add(panelTitulo);
            panel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ControladorVista.getInstance().crearInfoExtraView((int)obj[1]);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    titulo.setVisible(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    titulo.setVisible(false);
                }
            });
        }
    }

}
