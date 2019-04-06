package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.*;

public class ListaEtiquetasFiltrado {

    private HashMap<Tag, Integer> vecesTags;
    //Por cada peli, almacenamos los tags que aparecen junto con su tfidf
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

    public void calcularRelevanciasSimilitudes(FiltradoContenido fc) {
        HashMap<Integer, LinkedList<Integer>> usersPelisACalcular = MatrizValoraciones.getInstance().getValoracionesByLimite(3.5);
        HashMap<Tag, Double> relevanciasLogged = new HashMap<>();
        vecesTags.keySet().forEach(tag -> relevanciasLogged.put(tag, 0.0));
        HashMap<Tag, Double> esqueleto = new HashMap<>(relevanciasLogged);
        int idLogged = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        usersPelisACalcular.get(idLogged).forEach(i ->
            this.tfidf.get(i).forEach((tag,tf) -> relevanciasLogged.put(tag,relevanciasLogged.get(tag) + tf))
        );
        usersPelisACalcular.forEach((i,lista) -> {
            if (i != idLogged) {
                HashMap<Tag,Double> relevancias = new HashMap<>(esqueleto);
                lista.forEach(peli -> this.tfidf.get(peli).forEach((tag,tf) -> relevancias.put(tag, relevancias.get(tag) + tf)));
                List<Double> rLogged = new LinkedList<>();
                List<Double> rOtro = new LinkedList<>();
                List<AbstractMap.SimpleEntry<Double,Double>> rIntersec = new LinkedList<>();
                relevanciasLogged.forEach((tag,r) -> {
                    if (r != 0.0 && relevancias.get(tag) != 0.0) {
                        rIntersec.add(new AbstractMap.SimpleEntry<Double, Double>(
                                r,relevancias.get(tag)
                        ));
                    } else if (r != 0.0 ) {
                        rLogged.add(r);
                    } else if (relevancias.get(tag) != 0.0) {
                        rOtro.add(relevancias.get(tag));
                    }
                });
                fc.addSimilitud(idLogged, new Similitud(idLogged, i, MatrizValoraciones.getInstance().coseno(rLogged, rOtro, rIntersec)));
            }
        });
    }
}
