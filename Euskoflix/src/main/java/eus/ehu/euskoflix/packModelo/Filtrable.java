package eus.ehu.euskoflix.packModelo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class Filtrable {

    public static final int N = 50;
    private HashMap<Integer, TreeSet<Similitud>> matrizSimilitudes;

    public Filtrable() {
        this.matrizSimilitudes = new HashMap<>();
    }

    public abstract Similitud[] getNMasSimilares(int pId);

    public abstract ListaPeliculas recomendar(int pId, int pNum);

    public void addSimilitudSimetrica(Similitud pSimilitud) {
        this.addSimilitud(pSimilitud.getI(),pSimilitud);
        this.addSimilitud(pSimilitud.getJ(),pSimilitud);
    }


    private void addSimilitud(int pID, Similitud pSimilitud) {
        if (!this.matrizSimilitudes.containsKey(pID)) {
            this.matrizSimilitudes.put(pID, new TreeSet<Similitud>((o1, o2) -> Double.compare(o1.getSim(), o2.getSim()) * -1){{
                add(pSimilitud);
            }});
        } else {
            this.matrizSimilitudes.get(pID).add(pSimilitud);
        }
    }


}
