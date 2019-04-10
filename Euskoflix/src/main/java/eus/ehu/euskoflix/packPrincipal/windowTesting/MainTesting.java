package eus.ehu.euskoflix.packPrincipal.windowTesting;

import javax.swing.*;

public class MainTesting {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SimpleSwingBrowser browser = new SimpleSwingBrowser();
                //browser.setVisible(true);
                browser.loadURL("https://www.youtube.com/embed/TcMBFSGVi1c?autoplay=1&modestbranding=1&showinfo=0&rel=0&cc_load_policy=1&iv_load_policy=3&fs=0");
            }
        });
    }

}
