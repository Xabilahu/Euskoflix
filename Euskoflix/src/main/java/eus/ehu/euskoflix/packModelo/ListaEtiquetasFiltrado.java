package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.HashMap;
import java.util.Set;

public class ListaEtiquetasFiltrado {

    private HashMap<Tag, Integer> vecesTags;
    //Por cada peli, almacenamos los tags que aparecen junto con su tfidf
    //Se cargan los TF junto con las pelis, se calculan los TFIDF junto con la instanciación de this
    private HashMap<Integer, HashMap<Tag, Double>> tfidf;

    public ListaEtiquetasFiltrado() {
        this.vecesTags = GestionDatos.getInstance().cargarNt();
        this.tfidf = new HashMap<>();
    }


    public void add(int pId) {
        if (!tfidf.containsKey(pId)) {
            tfidf.put(pId, new HashMap<>());
        }
    }

    public void add(int pIdPelicula, Tag tag, double cantidad) {
        tfidf.get(pIdPelicula).put(tag, cantidad);
    }

    public void cargarIdfs() {
        this.tfidf.values().forEach(this::completarIDF);
    }


    private void completarIDF(HashMap<Tag, Double> map) {
        double totalTags = Cartelera.getInstance().getNumPeliculas();
        Set<Tag> tags = map.keySet();
        tags.forEach(tag -> map.put(tag, map.get(tag) * Math.log(totalTags / this.vecesTags.get(tag))));
    }
}
