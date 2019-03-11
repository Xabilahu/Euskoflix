package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packControlador.BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public ListaPeliculas buscar(String pConsulta, Tipo pTipo){
        return null;
    }

    public void cargarDatos() {
        BaseDatos.getBaseDatos().iniciarBD();
        this.cargarPeliculas();
        this.cargarUsuarios();
        this.cargarTags();
        this.cargarValoraciones();
    }

    private void cargarPeliculas() {
        ResultSet pelis = BaseDatos.getBaseDatos().pedirTabla("SELECT * FROM pelicula;");
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
        ResultSet usuarios = BaseDatos.getBaseDatos().pedirTabla("SELECT * FROM usuario;");
        try {
            while (usuarios.next()) {
                Usuario u = new Usuario(usuarios.getString("nombre"), usuarios.getString("apellido"), usuarios.getString("contrasena"));
                CatalogoUsuarios.getInstance().addUsuario(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarTags() {
        //TODO:implementar tras resolver la duda
    }

    private void cargarValoraciones() {
        //TODO:implementar tras resolver la duda
    }

}
