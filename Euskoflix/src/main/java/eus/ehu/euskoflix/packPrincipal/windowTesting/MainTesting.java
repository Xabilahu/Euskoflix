package eus.ehu.euskoflix.packPrincipal.windowTesting;

import javax.swing.*;

public class MainTesting {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SimpleSwingBrowser browser = new SimpleSwingBrowser();
                //browser.setVisible(true);
                browser.loadURL("https://www.youtube.com/embed/VaiHTvifGt0?&theme=dark&autoplay=1&autohide=1&modestbranding=1&fs=0&showinfo=0&rel=0&controls=0&disablekb=1");
            }
        });
    }

}
