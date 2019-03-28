package eus.ehu.euskoflix.packModelo;

import java.util.ArrayList;

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


}
