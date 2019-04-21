package eus.ehu.euskoflix.packDatos;

import eus.ehu.euskoflix.packModelo.*;
import eus.ehu.euskoflix.packModelo.packFiltro.Filtrado;
import eus.ehu.euskoflix.packModelo.packFiltro.ListaEtiquetasFiltrado;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
                p.fillInfoExtra(this.getInfoExtra(p.getId()));
                this.addTags(p);
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


    public Informacion getInfoExtra(int pPelicula) {
        Informacion i = new Informacion();
        ResultSet rst = BaseDatos.getBaseDatos().getInfoByPelicula(pPelicula);
        try {
            if (rst.next()) {
                i = new Informacion(ImageIO.read(new URL(PropertiesManager.getInstance().getPosterApiRequestURL(rst.getString("poster_path")))),
                        rst.getString("synopsis"), rst.getString("director"), rst.getString("trailer_path"));
            }
        } catch (Exception e) {
//            System.out.println("No se ha podido recuperar la información extra de la película: " + pPelicula.getTitulo());
        }
        return i;
    }

    public void addTags(Pelicula p) {
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

    public void registrar(String pNombre, String pApellido, String pPassword) {
        String pass = "";
        try {
            MessageDigest mg = MessageDigest.getInstance(PropertiesManager.getInstance().getEncryptionType());
            mg.reset();
            mg.update(pPassword.getBytes());
            pass = new BigInteger(1, mg.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error de encriptación.");
        }
        int id = CatalogoUsuarios.getInstance().getNumUsuarios() + 1;
        CatalogoUsuarios.getInstance().registrar(new Usuario(id, pNombre, pApellido, pass));
        BaseDatos.getBaseDatos().addNuevoUsuario(id, pass, pNombre, pApellido);
        try {
            System.out.println(GestionDatos.this.getClass().getResource(PropertiesManager.getInstance().getPathToFile("smallNombres")).toURI());
            System.out.println(id);
//            FileWriter fw = new FileWriter(new File(GestionDatos.this.getClass().getResource(PropertiesManager.getInstance().getPathToFile("smallNombres")).toURI()), true);
//            fw.write(pNombre + "," + pApellido + "," + pass);
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
