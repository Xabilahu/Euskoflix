package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packModelo.*;

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

    private ArrayList<Tag> cargarTags() {
        ResultSet rst = BaseDatos.getBaseDatos().getTags();
        ArrayList<Tag> tags = new ArrayList<>();
        try{
            while (rst.next()) {
                tags.add(new Tag(rst.getInt("id_usuario"), rst.getInt("id_pelicula"), rst.getString("etiqueta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    private ArrayList<Valoracion> cargarValoraciones() {
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

}
