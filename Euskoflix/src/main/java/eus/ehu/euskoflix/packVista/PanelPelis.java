package eus.ehu.euskoflix.packVista;

import eus.ehu.euskoflix.packControlador.ControladorVista;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Hashtable;

public class PanelPelis extends JPanel {

    private HashMap<Integer,Double> ratings;

    public void generarPanelesPelis(boolean valoraciones, Object[][] pPeliculas) {
        if (valoraciones) {
            this.ratings = new HashMap<>();
        }
        for (Object[] obj : pPeliculas) {
            JPanel panel = new JPanel(new BorderLayout());
            this.add(panel);
            Dimension dim;
            if (valoraciones) {
                dim = new Dimension(154,300);
            } else {
                dim = new Dimension(154,240);
            }
            panel.setPreferredSize(dim);
            panel.setMinimumSize(dim);
            panel.setMaximumSize(dim);
            JLabel icon = new JLabel();
            icon.setHorizontalAlignment(SwingConstants.CENTER);
            icon.setLayout(new BorderLayout(0, 0));
            icon.setIcon(new ImageIcon((Image) obj[0]));
            panel.add(icon, BorderLayout.CENTER);
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
            if (valoraciones) {
                JSlider slider = new JSlider();
                slider.setValue(5);
                slider.setMinimum(5);
                slider.setMaximum(50);
                slider.setMinorTickSpacing(1);
                slider.setMajorTickSpacing(5);
                Hashtable labelTable = new Hashtable();
                JLabel lbl1 = new JLabel("0.5");
                JLabel lbl2 = new JLabel("2");
                JLabel lbl3 = new JLabel("3");
                JLabel lbl4 = new JLabel("4");
                JLabel lbl5 = new JLabel("5");
                JLabel lbl6 = new JLabel("1");
                JLabel lbl7 = new JLabel("2.5");
                JLabel lbl8 = new JLabel("3.5");
                JLabel lbl9 = new JLabel("4.5");
                JLabel lbl10 = new JLabel("1.5");
                lbl1.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl2.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl3.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl4.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl5.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl6.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl7.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl8.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl9.setFont(new java.awt.Font("Times New Roman", 1, 10));
                lbl10.setFont(new java.awt.Font("Times New Roman", 1, 10));
                labelTable.put(new Integer(5), lbl1);
                labelTable.put(new Integer(10), lbl6);
                labelTable.put(new Integer(15), lbl10);
                labelTable.put(new Integer(20), lbl2);
                labelTable.put(new Integer(25), lbl7);
                labelTable.put(new Integer(30), lbl3);
                labelTable.put(new Integer(35), lbl8);
                labelTable.put(new Integer(40), lbl4);
                labelTable.put(new Integer(45), lbl9);
                labelTable.put(new Integer(50), lbl5);
                slider.setLabelTable(labelTable);
                slider.setPaintLabels(true);
                slider.setPaintTicks(true);
                slider.setValueIsAdjusting(true);
                panel.add(slider, BorderLayout.SOUTH);
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        ratings.put((int) obj[1], slider.getValue() / 10.0);
                    }
                });
            }
        }
    }

    public HashMap<Integer,Double> getRatings() {
        return this.ratings;
    }

}
