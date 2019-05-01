package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packModelo.packFiltro.FiltradoProducto;

import java.util.ArrayList;
import java.util.HashSet;

public class Cartelera {

    private static Cartelera mCartelera;
    private ListaPeliculas lista;
    private ArrayList<Integer> idMapping;

    private Cartelera() {
        this.lista = new ListaPeliculas();
        this.idMapping = new ArrayList<>();
    }

    public static Cartelera getInstance() {
        if (mCartelera == null) {
            mCartelera = new Cartelera();
        }
        return mCartelera;
    }

    public void addPelicula(Pelicula pPeli) {
        this.idMapping.add(pPeli.getId());
        this.lista.addPelicula(pPeli);
    }

    public void print() {
//        lista.print();
    }

    public int getNumPeliculas() {
        return this.lista.getNumPeliculas();
    }

    public Pelicula getPeliculaPorId(int pId) {
        return this.lista.getPeliculaPorId(this.idMapping.get(pId));
    }

    public Pelicula getPeliculaPorIdSinMapeo(int pId) {
        return this.lista.getPeliculaPorId(pId);
    }

    /**
     * This method is only used in jUnit
     */
    public void reset() {
        mCartelera = new Cartelera();
    }

    public void cargarMediasDesviacionesPeliculas() {
        this.lista.cargarMediaDesviacionesPeliculas();
    }

    public void cargarModeloProductos(FiltradoProducto filtradoProducto) {
        lista.cargarModeloProducto(filtradoProducto);
    }

    public HashSet<Integer> getPeliculasNoValoradas(Usuario pUsuario) {
        return this.lista.getPeliculasNoValoradas(pUsuario);
    }

    public Integer[] peliculasToIntegerArray() {
        Integer[] res = new Integer[this.idMapping.size()];
        int i = 0;
        for (Integer x : this.idMapping)
            res[i++] = x;
        return res;
    }
    public ListaPeliculas buscarPeliculas(String pConsulta){
        return this.lista.filtrarPeliculas(pConsulta);
    }

}
