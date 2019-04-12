package eus.ehu.euskoflix.packPrincipal.windowTesting;

import com.alee.extended.transition.ImageTransition;

import javax.swing.*;

public class MainTesting {

    public static void main(String[] args) {
        ImageTransition imageTransition = new ImageTransition(null, null);
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                ReprodutorVideo browser = new ReprodutorVideo();
                //browser.setVisible(true);
                browser.loadURL("https://www.youtube.com/embed/video_id?&theme=dark&autoplay=1&autohide=1&modestbranding=1&fs=0&showinfo=0&rel=0&controls=0&disablekb=1");
            }
        });
    }

}
