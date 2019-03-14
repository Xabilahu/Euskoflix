package eus.ehu.euskoflix.packPrincipal;

import com.alee.laf.WebLookAndFeel;
import com.google.gson.*;
import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.Informacion;
import eus.ehu.euskoflix.packControlador.PropertiesManager;

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
        Cartelera.getInstance().print();
    }

    private void makeTMBDRequest(int tmdbID) {
        try {
            //Movie general info query
            URL mov = new URL(PropertiesManager.getInstance().getMovieApiRequestURL(tmdbID));
            HttpURLConnection con = (HttpURLConnection) mov.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder str = new StringBuilder();
            while (in.ready()) {
                str.append(in.readLine());
            }
            in.close();
            con.disconnect();
            String json = str.toString();
            JsonParser parser = new JsonParser();
            JsonObject movie = parser.parse(json).getAsJsonObject();
            String titulo = movie.get("title").getAsString();
            String posterPath = movie.get("poster_path").getAsString();
            String sinopsis = movie.get("overview").getAsString();
            //Movie credits info query
            //URL credits = new URL("https://api.themoviedb.org/3/movie/" + tmdbID + "/credits?api_key=e9f7e8f9f25e5afa642449f0d4a1b4a7");
            URL credits = new URL(PropertiesManager.getInstance().getCreditsApiRequestURL(tmdbID));
            con = (HttpURLConnection) credits.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            str = new StringBuilder();
            while(in.ready()) {
                str.append(in.readLine());
            }
            in.close();
            con.disconnect();
            JsonArray crew = parser.parse(str.toString()).getAsJsonObject().get("crew").getAsJsonArray();
            String director = "";
            for (JsonElement obj : crew) {
                JsonObject jObj = obj.getAsJsonObject();
                if (jObj.get("job").getAsString().equals("Director")) {
                    director = jObj.get("name").getAsString();
                    break;
                }
            }
            //Movie poster query
            //URL img = new URL("https://image.tmdb.org/t/p/w154" + posterPath);
            URL img  = new URL(PropertiesManager.getInstance().getPosterApiRequestURL(posterPath));
            Image image = ImageIO.read(img);
            Informacion infoExtra = new Informacion(image, sinopsis, director);
            System.out.println("Titulo: " + titulo);
            System.out.println(infoExtra.toString());
            //Testing image
            ImageIcon imageIcon = new ImageIcon();
            imageIcon.setImage(image);
            WebLookAndFeel.install ();
            JOptionPane.showMessageDialog(null,"","Image",JOptionPane.PLAIN_MESSAGE, imageIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
