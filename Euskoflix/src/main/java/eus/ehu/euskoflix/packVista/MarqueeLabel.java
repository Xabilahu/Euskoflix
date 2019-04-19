package eus.ehu.euskoflix.packVista;

import javax.swing.*;
import java.awt.*;

public class MarqueeLabel extends JLabel {
    //		   public int LEFT_TO_RIGHT = 1;
//		   public  int RIGHT_TO_LEFT = 2;
    private int Option;
    private int Speed;

    public MarqueeLabel(String text, int Option, int Speed) {
        this.Option = Option;
        this.Speed = Speed;
        this.setText(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (Option == 1) {
            g.translate((int) ((System.currentTimeMillis() / Speed) % (getWidth() * 2) - getWidth()), 0);
        } else if (Option == 2) {
            g.translate((int) (getWidth() - (System.currentTimeMillis() / Speed) % (getWidth() * 2)), 0);
        }
        super.paintComponent(g);
        repaint(5);
    }
}
