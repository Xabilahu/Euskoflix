package eus.ehu.euskoflix.packPrincipal.windowTesting;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

import static javafx.concurrent.Worker.State.FAILED;
import static javafx.concurrent.Worker.State.SCHEDULED;

public class ReproductorVideo extends JFrame {

    private final JFXPanel jfxPanel = new JFXPanel();
    private final JPanel panel = new JPanel(new BorderLayout());
    private WebEngine engine;
    private String url;
    private Point mousePosition;

    public ReproductorVideo() {
        super();
        mousePosition = new Point();
        initComponents();
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    private void initComponents() {
        createScene();

        panel.add(jfxPanel, BorderLayout.CENTER);
        getContentPane().add(panel);

        this.setUndecorated(false);
        jfxPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    jfxPanel.setVisible(false);
                    ReproductorVideo.this.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        jfxPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePosition.x = e.getX();
                mousePosition.y = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        });

        jfxPanel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int windowX = ReproductorVideo.this.getLocation().x;
                int windowY = ReproductorVideo.this.getLocation().y;
                ReproductorVideo.this.move(windowX + (x - mousePosition.x), windowY + (y - mousePosition.y));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Platform.runLater(() -> {
                    Platform.setImplicitExit(false);
                    ReproductorVideo.this.jfxPanel.setVisible(false);
                    ReproductorVideo.this.engine.load(null);
                    ReproductorVideo.this.dispose();
                    System.gc();
                });
            }
        });

        setPreferredSize(new Dimension(640, 360));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        requestFocus();
        this.setAlwaysOnTop(true);
        setLocationRelativeTo(null);
    }

    private void createScene() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                WebView view = new WebView();
                engine = view.getEngine();
                engine.getLoadWorker()
                        .exceptionProperty()
                        .addListener((o, old, value) -> {
                            if (engine.getLoadWorker().getState() == FAILED) {
                                System.out.println("error");
                                /*SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        JOptionPane.showMessageDialog(
                                                panel,
                                                (value != null)
                                                        ? engine.getLocation() + "\n" + value.getMessage()
                                                        : engine.getLocation() + "\nUnexpected error.",
                                                "Loading error...",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                });*/
                            }
                        });
                engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED){
                        Document d = engine.getDocument();
                        NodeList nl = d.getElementsByTagName("div");
                        for (int i = 0 ; i < nl.getLength(); i++){
                            Element e = (Element) nl.item(i);
                            String classes = e.getAttribute("class");
                            if(classes != null &&
                                    (classes.contains("ytp-chrome-top")
                                    || classes.contains("ytp-gradient-bottom")
                                    || classes.contains("ytp-gradient-top")
                                    || classes.contains("ytp-chrome-bottom")
                                    )){
                                e.setAttribute("style","display:none !important");
                                e.setTextContent("");
                            }else if(classes != null && classes.contains("ytp-pause-overlay ")){
                                e.setTextContent("");
                                e.setAttribute("class","");
                            }
                        }
                        nl = d.getElementsByTagName("a");
                        for (int i = 0 ; i < nl.getLength(); i++){
                            Element e = (Element) nl.item(i);
                            e.setAttribute("href","#");
                            e.setTextContent("");
                        }
                        ReproductorVideo.this.setVisible(true);
                    }else if(oldValue == Worker.State.SUCCEEDED && newValue == SCHEDULED){
                        loadURL(ReproductorVideo.this.url);
                    }
                });
                jfxPanel.setScene(new Scene(view));
            }
        });
    }

    public void loadURL(final String url) {
        this.url = url;
        Platform.runLater(() -> {
            String tmp = toURL(url);

            if (tmp == null) {
                tmp = toURL("http://" + url);
            }

            engine.load(tmp);
        });
    }
}
