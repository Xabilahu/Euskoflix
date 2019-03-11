package eus.ehu.euskoflix.packModelo;

import java.util.ArrayList;

public class ListaPeliculas {

    private ArrayList<Pelicula> lista;

    public ListaPeliculas(){
        this.lista = new ArrayList<Pelicula>();
        this.lista.add(0, null); //Las peliculas serán insertadas por ID (1..N)
    }

    public void addPelicula(Pelicula pPeli) {
        this.lista.add(pPeli);
    }

    public void print() {
        boolean first = true;
        for (Pelicula p : lista)
            if (first)
                first = false;
            else
                p.print();
    }

}
