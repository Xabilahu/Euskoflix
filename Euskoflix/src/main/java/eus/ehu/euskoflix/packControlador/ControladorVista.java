package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packModelo.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControladorVista {
    private static ControladorVista mControladorVista;

    private ControladorVista() {
    }

    public static ControladorVista getInstance() {
        if (mControladorVista == null) {
            mControladorVista = new ControladorVista();
        }
        return mControladorVista;
    }

    public void cargarDatos(boolean pTest) {
        GestionDatos.getInstance().cargarDatos(pTest);
    }

    public String[][] datosUsuario() {
        CatalogoUsuarios users = CatalogoUsuarios.getInstance();
        int size = users.getNumUsuarios();
        String[][] resulUsers = new String[size][4];
        Usuario userNew;
        for (int i = 0; i < size - 1; i++) {
            userNew = users.getUsuarioPorId(i + 1);
            resulUsers[i][0] = String.valueOf(userNew.getId());
            resulUsers[i][1] = userNew.getNombre();
            resulUsers[i][2] = userNew.getApellido();
            resulUsers[i][3] = userNew.getPassword();
        }
        return resulUsers;
    }

    public String[] getCabeceraUsers() {
        return new String[]{"ID", "Nombre", "Apellido", "Password"};
    }

    //Pestana Tags
    public String[][] datosTags(int id) {
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(id);
        GestionDatos.getInstance().getTags(p);
        return p.tagsToStringArray();
    }

    public String[] getCabeceraTags() {
        return new String[]{"Tag", "Apariciones"};
    }

    //Pestana Ratings
    public String[][] datosRatings(int pId) {
        LinkedHashMap<Integer, Float> ratings = MatrizValoraciones.getInstance().getValoracionesByPelicula(pId);
        String[][] resultado = new String[ratings.size()][2];
        CatalogoUsuarios cat = CatalogoUsuarios.getInstance();
        int i = 0;
        for (Map.Entry<Integer, Float> entry : ratings.entrySet()) {
            resultado[i][0] = cat.getUsuarioPorId(entry.getKey()).getNombre();
            resultado[i++][1] = entry.getValue().toString();
        }
        return resultado;
    }

    public String[] getCabeceraRatings() {
        return new String[]{"NombreUsuario", "Rating"};
    }

    //Pestana peliculas y su cabecera
    public String[][] datosPelis() {
        Cartelera films = Cartelera.getInstance();
        int size = films.getNumPeliculas();
        String[][] resulFilms = new String[size][2];
        Pelicula filmNew;
        for (int i = 0; i < size; i++) {
            filmNew = films.getPeliculaPorId(i);
            resulFilms[i][0] = String.valueOf(filmNew.getId());
            resulFilms[i][1] = filmNew.getTitulo();
        }
        return resulFilms;
    }

    public String[] getCabeceraFilms() {
        return new String[]{"ID", "Titulo"};
    }


    /**
     * @return [titulo, director, sinopsis, poster]
     */
    public Object[] getInfoPelicula(int pId) {
        Object[] result = new Object[4];
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(pId);
        result[0] = p.getTitulo();
        result[1] = p.getDirector();
        result[2] = p.getSinopsis();
        result[3] = p.getPoster();
        return result;
    }

}
