package eus.ehu.euskoflix.packModelo;

import java.util.HashMap;
import java.util.TreeSet;

public abstract class Filtrable {

    public static final int N = 50;
    private HashMap<Integer, TreeSet<Similitud>> matrizSimilitudes;

    public abstract Similitud[] getNMasSimilares(int pId);

    public abstract ListaPeliculas recomendar(int pId, int pNum);

    public void addSimilitud(Similitud pSimilitud) {

    }

    public void addSimilitudSimetrica(Similitud pSimilitud) {

    }

}
