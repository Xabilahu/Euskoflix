package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.MatrizValoraciones;
import eus.ehu.euskoflix.packModelo.Tag;

import java.util.*;

public class ListaEtiquetasFiltrado {

    private HashMap<Tag, Integer> vecesTags;
    //Por cada peli, almacenamos los tags que aparecen junto con su tfidf
    private HashMap<Integer, HashMap<Tag, Double>> tfidf;
    private HashMap<Tag, HashSet<Integer>> peliculasPorEtiqueta;

    public ListaEtiquetasFiltrado(HashMap<Tag,Integer> pVecesTags) {
        this.vecesTags = pVecesTags;
        this.tfidf = new HashMap<>();
        this.peliculasPorEtiqueta = new HashMap<>();
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
        //This code normalizes the tfidf vectors by tag
//        this.peliculasPorEtiqueta.keySet().forEach(tag -> {
//            double x = 0.0;
//            for (Integer i: this.tfidf.keySet()) {
//                if (this.tfidf.get(i).containsKey(tag)) x += this.tfidf.get(i).get(tag);
//            }
//            final double y = Math.sqrt(x);
//            this.tfidf.values().forEach(map -> {
//                if (map.containsKey(tag)) map.put(tag,map.get(tag)/y);
//            });
//        });
    }

    private void completarIDF(HashMap<Tag, Double> map) {
        double totalTags = Cartelera.getInstance().getNumPeliculas();
        Set<Tag> tags = map.keySet();
        tags.forEach(tag -> map.put(tag, map.get(tag) * Math.log(totalTags / this.vecesTags.get(tag))));
        double x = 0.0;
        for (Double v : map.values()) {
            x += Math.pow(v, 2);
        }
        x = Math.sqrt(x);
        for (Tag t : map.keySet()) {
            map.put(t, map.get(t) / x);
        }
    }

    public void calcularRelevanciasSimilitudes(FiltradoContenido fc) {
        HashMap<Integer, LinkedList<Integer>> usersPelisACalcular = MatrizValoraciones.getInstance().getUsersPeliculasByLimite(3.5);
        HashMap<Tag, Double> relevanciasLogged = new HashMap<>();
        vecesTags.keySet().forEach(tag -> relevanciasLogged.put(tag, 0.0));
        HashMap<Tag, Double> esqueleto = new HashMap<>(relevanciasLogged);
        int idLogged = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        HashMap<Tag, Double> almacenadas = new HashMap<>();
        this.calcularRelevancias(usersPelisACalcular, idLogged, relevanciasLogged, almacenadas);
        usersPelisACalcular.keySet().forEach(i -> {
            if (i != idLogged) {
                HashMap<Tag, Double> relevancias = new HashMap<>(esqueleto);
                this.calcularRelevancias(usersPelisACalcular, i, relevancias, almacenadas);
                List<Double> rLogged = new LinkedList<>();
                List<Double> rOtro = new LinkedList<>();
                List<AbstractMap.SimpleEntry<Double, Double>> rIntersec = new LinkedList<>();
                relevanciasLogged.forEach((tag, r) -> {
                    if (r != 0.0 && relevancias.get(tag) != 0.0) {
                        rLogged.add(r);
                        rOtro.add(relevancias.get(tag));
                        rIntersec.add(new AbstractMap.SimpleEntry<Double, Double>(
                                r, relevancias.get(tag)
                        ));
                    } else if (r != 0.0) {
                        rLogged.add(r);
                    } else if (relevancias.get(tag) != 0.0) {
                        rOtro.add(relevancias.get(tag));
                    }
                });
                fc.addSimilitud(idLogged, new Similitud(idLogged, i, MatrizValoraciones.getInstance().coseno(rLogged, rOtro, rIntersec)));
            }
        });
    }

    private void calcularRelevancias(HashMap<Integer, LinkedList<Integer>> usersPelisACalcular, Integer i, HashMap<Tag, Double> relevancias, HashMap<Tag, Double> almacenadas) {
        HashSet<Tag> aCalcular = new HashSet<>();
        usersPelisACalcular.get(i).forEach(j -> aCalcular.addAll(this.tfidf.get(j).keySet()));
        aCalcular.forEach(tag -> {
            if (almacenadas.containsKey(tag) && almacenadas.get(tag) != 0.0) {
                relevancias.put(tag, almacenadas.get(tag));
            } else {
                this.peliculasPorEtiqueta.get(tag).forEach(j -> relevancias.put(tag, relevancias.get(tag) + this.tfidf.get(j).get(tag)));
            }
        });
        almacenadas.putAll(relevancias);
    }

    public void addPeliculaAEtiqueta(Tag pTag, int idPelicula) {
        if (!this.peliculasPorEtiqueta.containsKey(pTag)) {
            this.peliculasPorEtiqueta.put(pTag, new HashSet<>());
        }
        this.peliculasPorEtiqueta.get(pTag).add(idPelicula);
    }

}
