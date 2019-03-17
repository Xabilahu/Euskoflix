package eus.ehu.euskoflix.packControlador;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packModelo.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
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
                Pelicula p = new Pelicula(pelis.getInt("id"), pelis.getString("titulo"), pelis.getInt("idTMDB"));
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

    public String[][] getTagsByPelicula(int pId) {
        ResultSet rst = BaseDatos.getBaseDatos().getTagsByPelicula(pId);
        String[][] resultado = new String[BaseDatos.getBaseDatos().getNumTagsByPelicula(pId)][2];
        return getStrings(rst, resultado);
    }

    public String[][] getValoracionesByPelicula(int pId) {
        ResultSet rst = BaseDatos.getBaseDatos().getValoracionByPelicula(pId);
        String[][] resultado = new String[BaseDatos.getBaseDatos().getNumValoracionesByPelicula(pId)][2];
        return getStrings(rst, resultado);
    }

    private String[][] getStrings(ResultSet rst, String[][] resultado) {
        try {
            int i = 0;
            while (rst.next()) {
                resultado[i][0] = rst.getString(1);
                resultado[i++][1] = rst.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public float[] getValoraciones() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        float[] valoraciones = null;
        try {
            valoraciones = new float[BaseDatos.getBaseDatos().getNumValoraciones() + 1];
            for (int i = 1; rst.next(); i++) {
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
        try {
            boolean primero = true;
            filas = new int[BaseDatos.getBaseDatos().getNumUsuariosQueValoran() + 1];
            rst.next();
            rst2.next();
            int id = rst.getInt("id_usuario");
            int idAnterior = 0;
            filas[idAnterior] = 0;
            for (int i = 1; i < filas.length; i++) {
                if (i == id) {
                    if (primero) {
                        filas[i] = 1;
                        primero = false;
                    } else {
                        filas[i] = filas[idAnterior] + rst2.getInt("suma");
                        rst2.next();
                    }
                    idAnterior = id;
                    if (rst.next()) {
                        id = rst.getInt("id_usuario");
                    }
                } else {
                    filas[i] = -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filas;
    }

    public int[] getValoracionesPeliculas() {
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        int[] columnas = null;
        try {
            columnas = new int[BaseDatos.getBaseDatos().getNumPeliculasValoradas() + 1];
            for (int i = 1; rst.next(); i++) {
                columnas[i] = rst.getInt("id_pelicula");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnas;
    }


    public Informacion getInfoExtra(Pelicula pPelicula) {

        Informacion i = new Informacion();
        try {
            int tmdbID = pPelicula.getTmdbId();
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
            URL credits = new URL(PropertiesManager.getInstance().getCreditsApiRequestURL(tmdbID));
            con = (HttpURLConnection) credits.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            str = new StringBuilder();
            while (in.ready()) {
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
            URL img = new URL(PropertiesManager.getInstance().getPosterApiRequestURL(posterPath));
            Image image = ImageIO.read(img);
            i = new Informacion(image, sinopsis, director);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return i;
    }

  /*  public void makeTMBDRequest(int tmdbID) {
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
    }*/

}
