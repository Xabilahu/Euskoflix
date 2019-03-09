package eus.ehu.euskoflix.packPrincipal;

import com.google.gson.*;
import eus.ehu.euskoflix.packModelo.Informacion;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        new Main().makeTMBDRequest(550);
    }

    private void makeTMBDRequest(int tmdbID) {
        String movieUrl = "https://api.themoviedb.org/3/movie/" + tmdbID + "?api_key=e9f7e8f9f25e5afa642449f0d4a1b4a7&language=es";
        String json = "";
        Gson gson = new Gson();
        try {
            //Movie general info query
            URL mov = new URL(movieUrl);
            HttpURLConnection con = (HttpURLConnection) mov.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder str = new StringBuilder();
            while (in.ready()) {
                str.append(in.readLine());
            }
            in.close();
            con.disconnect();
            json = str.toString();
            JsonParser parser = new JsonParser();
            JsonObject movie = parser.parse(json).getAsJsonObject();
            String titulo = movie.get("title").getAsString();
            String posterPath = movie.get("poster_path").getAsString();
            String sinopsis = movie.get("overview").getAsString();
            //Movie credits info query
            URL credits = new URL("https://api.themoviedb.org/3/movie/" + tmdbID + "/credits?api_key=e9f7e8f9f25e5afa642449f0d4a1b4a7");
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
            URL img = new URL("https://image.tmdb.org/t/p/w92" + posterPath);
            Image image = ImageIO.read(img);
            Informacion infoExtra = new Informacion(image, sinopsis, director);
            System.out.println("Titulo: " + titulo);
            System.out.println(infoExtra.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
