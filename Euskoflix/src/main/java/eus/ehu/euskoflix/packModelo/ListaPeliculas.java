package eus.ehu.euskoflix.packModelo;

import java.util.Collection;

public class ListaPeliculas {

    private Collection<Pelicula> lista;

    public ListaPeliculas(){
        //TODO:inicializar lista cuando se decida la colección a utilizar
    }

    public void addPelicula(Pelicula pPeli) {
        this.lista.add(pPeli);
    }

}
