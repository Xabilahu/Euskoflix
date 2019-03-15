package eus.ehu.euskoflix.packControlador;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packModelo.*;

import javax.xml.transform.Result;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionDatos {

    private static GestionDatos mGestionDatos;

    public GestionDatos() {
    }

    public static GestionDatos getInstance() {
        if (mGestionDatos == null) {
            mGestionDatos = new GestionDatos();
        }
        return mGestionDatos;
    }

    public void cargarDatos() {
        BaseDatos.getBaseDatos().iniciarBD();
        this.cargarPeliculas();
        this.cargarUsuarios();
        MatrizValoraciones.getInstance().cargarValoraciones();
    }

    private void cargarPeliculas() {
        ResultSet pelis = BaseDatos.getBaseDatos().getPeliculas();
        try {
            while (pelis.next()) {
                Pelicula p = new Pelicula(pelis.getInt("id"), pelis.getString("titulo"), null);
                Cartelera.getInstance().addPelicula(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarUsuarios() {
        ResultSet usuarios = BaseDatos.getBaseDatos().getUsuarios();
        try {
            while (usuarios.next()) {
                Usuario u = new Usuario(usuarios.getInt("id"), usuarios.getString("nombre"), usuarios.getString("apellido"), usuarios.getString("contrasena"));
                CatalogoUsuarios.getInstance().addUsuario(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Tag> cogerTags() {
        ResultSet rst = BaseDatos.getBaseDatos().getTags();
        return getTags(rst);
    }

    private ArrayList<Valoracion> cogerValoraciones() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        ArrayList<Valoracion> ratings = new ArrayList<>();
        try {
            while (rst.next()) {
                ratings.add(new Valoracion(rst.getInt("id_usuario"), rst.getInt("id_pelicula"), rst.getFloat("valoracion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    public ArrayList<Tag> getTagsByPelicula(int pId) {
        ResultSet rst = BaseDatos.getBaseDatos().getTagsByPelicula(pId);
        return getTags(rst);
    }

    private ArrayList<Tag> getTags(ResultSet rst) {
        ArrayList<Tag> tags = new ArrayList<>();
        try {
            while (rst.next()) {
                tags.add(new Tag(rst.getInt("id_usuario"), rst.getInt("id_pelicula"), rst.getString("etiqueta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }


    public float[] getValoraciones() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        float[] valoraciones = null;
            try {
                valoraciones = new float[BaseDatos.getBaseDatos().getNumValoraciones()];
                for (int i = 0; rst.next();i++){
                    valoraciones[i] = rst.getFloat("valoracion");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return valoraciones;
    }

    public int[] getValoracionesUsuarios() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoracionesUsuarios();
        ResultSet rst2 = BaseDatos.getBaseDatos().getNumValoracionesUsuario();
        int[] filas = null;
        try{
            filas = new int[BaseDatos.getBaseDatos().getNumUsuariosQueValoran()];
            int id =  rst.getInt("id_usuario");
            for (int i = 0 ; i < filas.length; i++){
                if (i+1 == id){
                    rst2.next();
                    filas[i] = i+rst2.getInt("suma");
                    rst.next();
                    id = rst.getInt("id_usuario");
                }else{
                    filas[i] = 0;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return filas;
    }

    public int[] getValoracionesPeliculas() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        int[] columnas = null;
        try {
            columnas = new int[BaseDatos.getBaseDatos().getNumPeliculasValoradas() + 1];
            for (int i = 0; rst.next();i++){
                columnas[i] = rst.getInt("id_pelicula");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnas;
    }


    public void makeTMBDRequest(int tmdbID) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
