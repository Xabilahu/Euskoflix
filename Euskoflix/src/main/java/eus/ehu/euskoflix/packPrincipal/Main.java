package eus.ehu.euskoflix.packPrincipal;

import com.alee.laf.WebLookAndFeel;
import com.google.gson.*;
import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.Informacion;
import eus.ehu.euskoflix.packControlador.PropertiesManager;
import eus.ehu.euskoflix.packModelo.MatrizValoraciones;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        //new Main().makeTMBDRequest(5);
//        new Main().cargarFicheros();
        GestionDatos.getInstance().cargarDatos();
        System.out.println(MatrizValoraciones.getInstance().toString());
        //Cartelera.getInstance().print();
    }



    public void cargarFicheros() {
        StringBuilder str = new StringBuilder();
        InputStream is = Main.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("links"));
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            while (in.ready())
                str.append(in.readLine());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(str.toString());
    }

}
