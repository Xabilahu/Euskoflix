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
        double x = 0.0;
        for (Double v : map.values()){
            x += Math.pow(v,2);
        }
        x = Math.sqrt(x);
        for (Tag t : map.keySet()) {
            map.put(t, map.get(t) / x);
        }
    }

    public void calcularRelevanciasSimilitudes(FiltradoContenido fc) {
        HashMap<Integer, LinkedList<Integer>> usersPelisACalcular = MatrizValoraciones.getInstance().getValoracionesByLimite(3.5);
        HashMap<Tag, Double> relevanciasLogged = new HashMap<>();
        vecesTags.keySet().forEach(tag -> relevanciasLogged.put(tag, 0.0));
        HashMap<Tag, Double> esqueleto = new HashMap<>(relevanciasLogged);
        int idLogged = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        this.calcularRelevancias(usersPelisACalcular,idLogged,relevanciasLogged);
        usersPelisACalcular.forEach((i, lista) -> {
            if (i != idLogged) {
                HashMap<Tag, Double> relevancias = new HashMap<>(esqueleto);
                this.calcularRelevancias(usersPelisACalcular, i, relevancias);
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

    private void calcularRelevancias(HashMap<Integer, LinkedList<Integer>> usersPelisACalcular, Integer i, HashMap<Tag, Double> relevancias) {
        HashSet<Tag> aCalcular = new HashSet<>();
        usersPelisACalcular.get(i).forEach(j -> aCalcular.addAll(this.tfidf.get(j).keySet()));
        aCalcular.forEach(tag -> this.tfidf.values().forEach(map -> map.forEach((t,val) -> {
            if (tag.equals(t)) {
                relevancias.put(tag, relevancias.get(tag) + val);
            }
        })));
    }
}
