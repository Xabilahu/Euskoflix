package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.HashMap;
import java.util.Set;

public class FiltradoContenido extends Filtrable {

    private HashMap<Tag, Integer> vecesTags;
    //Por cada peli, almacenamos los tags que aparecen junto con su tfidf
    //Se cargan los TF junto con las pelis, se calculan los TFIDF junto con la instanciación de this
    private HashMap<Integer, HashMap<Tag, Double>> tfidf;

    public FiltradoContenido() {
        this.vecesTags = GestionDatos.getInstance().cargarNt();
    }

    public void cargarTF(HashMap<Integer,HashMap<Tag, Double>> pTf) {
        this.tfidf = pTf;
    }

    @Override
    public Similitud[] getNMasSimilares(int pId) {
        return null;
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        return null;
    }

    @Override
    public void cargar() {
        this.tfidf.values().forEach(this::completarIDF);
        System.out.printf("");
    }

    private void completarIDF(HashMap<Tag, Double> map) {
        double totalTags = Cartelera.getInstance().getNumPeliculas();
        Set<Tag> tags = map.keySet();
        tags.forEach(tag -> {
            map.put(tag, map.get(tag) * Math.log(totalTags/this.vecesTags.get(tag)));
        });
    }

}
