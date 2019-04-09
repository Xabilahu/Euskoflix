package eus.ehu.euskoflix.packDatos;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eus.ehu.euskoflix.packModelo.*;
import eus.ehu.euskoflix.packModelo.packFiltro.Filtrado;
import eus.ehu.euskoflix.packModelo.packFiltro.ListaEtiquetasFiltrado;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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

    public void cargarDatos(TipoFichero pTipo) {
        BaseDatos.getBaseDatos().iniciarBD(pTipo);
        this.cargarPeliculasTagsTf();
        this.cargarEstructuraEtiquetas();
        this.cargarUsuarios();
        MatrizValoraciones.getInstance().cargarValoraciones();
        CatalogoUsuarios.getInstance().cargarMediasDesviacionesUsuarios();
        Cartelera.getInstance().cargarMediasDesviacionesPeliculas();
    }

    private void cargarEstructuraEtiquetas() {
        ResultSet rst = BaseDatos.getBaseDatos().getTagsPeliculas();
        ListaEtiquetasFiltrado lef = Filtrado.getInstance().getFiltradoContenido().getTfidf();
        try {
            while (rst.next()) {
                int peli = rst.getInt("id_pelicula");
                Tag t = new Tag(rst.getString("etiqueta"));
                lef.addPeliculaAEtiqueta(t, peli);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarPeliculasTagsTf() {
        ResultSet pelis = BaseDatos.getBaseDatos().getPeliculas();
        ListaEtiquetasFiltrado tf = new ListaEtiquetasFiltrado();
        try {
            while (pelis.next()) {
                Pelicula p = new Pelicula(pelis.getInt("id"), pelis.getString("titulo"), pelis.getInt("idTMDB"));
                Cartelera.getInstance().addPelicula(p);
                this.getTags(p);
                tf.add(p.getId());
                p.getLista().rellenarTf(tf, p.getId());
            }
            Filtrado.getInstance().cargarTf(tf);
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
            filas = new int[CatalogoUsuarios.getInstance().getNumUsuarios()];
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
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
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
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
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

    public void getTags(Pelicula p) {
        ResultSet rst = BaseDatos.getBaseDatos().getTagsByPelicula(p.getId());
        try {
            while (rst.next()) {
                Tag t = new Tag(rst.getString("etiqueta"), rst.getInt("veces"));
                p.addTag(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public HashMap<Integer, HashMap<Integer, Double>> cargarValoraciones() {
        HashMap<Integer, HashMap<Integer, Double>> resultado = new HashMap<>();
        ResultSet rst = BaseDatos.getBaseDatos().getValoraciones();
        try {
            while (rst.next()) {
                int usuario = rst.getInt("id_usuario");
                int pelicula = rst.getInt("id_pelicula");
                double valoracion = rst.getDouble("valoracion");
                HashMap<Integer, Double> pelis = resultado.get(usuario);
                if (pelis == null) {
                    pelis = new HashMap<>();
                    pelis.put(pelicula, valoracion);
                    resultado.put(usuario, pelis);
                } else {
                    pelis.put(pelicula, valoracion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public HashMap<Tag, Integer> cargarNt() {
        ResultSet rst = BaseDatos.getBaseDatos().getNt();
        HashMap<Tag, Integer> nt = new HashMap<>();
        try {
            while (rst.next()) {
                nt.put(new Tag(rst.getString(1)), rst.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nt;
    }
    
    public String getUsuarioPorId(int pId) {
    	ResultSet rst = BaseDatos.getBaseDatos().getUsuarioPorId(pId);
    	JsonObject jsonUsuario = new JsonObject();
    	try {
    		if (rst.next()) {
    			
    			String nombre = rst.getString("nombre");
    			jsonUsuario.addProperty("nombre", nombre);
    			String apellido = rst.getString("apellido");
    			jsonUsuario.addProperty("apellido", apellido);
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
        }
        return jsonUsuario.toString();
    }

}
