package eus.ehu.euskoflix.packModelo.packFiltro;

import java.text.DecimalFormat;
import java.util.*;

public class ListaPeliculasValoraciones {

    private TreeSet<Map.Entry<Integer, Double>> recomendaciones;

    public ListaPeliculasValoraciones() {
        this.recomendaciones = new TreeSet<>((o1, o2) -> Double.compare(o1.getValue(), o2.getValue()) * -1);
    }

    public static ListaPeliculasValoraciones generarHíbrido(ListaPeliculasValoraciones pLista1, ListaPeliculasValoraciones pLista2, ListaPeliculasValoraciones pLista3) {
        HashMap<Integer, Double> rec = new HashMap<>();
        ListaPeliculasValoraciones lpv = new ListaPeliculasValoraciones();
        pLista1.recomendaciones.forEach(entry -> rec.put(entry.getKey(), entry.getValue()/3.0));
        pLista2.recomendaciones.forEach(entry -> {
            if (rec.containsKey(entry.getKey())) {
                rec.put(entry.getKey(), rec.get(entry.getKey()) + (entry.getValue() / 3.0));
            }
        });
        pLista3.recomendaciones.forEach(entry -> {
            if (rec.containsKey(entry.getKey())) {
                lpv.add(entry.getKey(), rec.get(entry.getKey()) + (entry.getValue() / 3.0));
            }
        });
        return lpv;
    }

    public void add(int pId, double pValoracion) {
        this.recomendaciones.add(new AbstractMap.SimpleEntry<>(pId, pValoracion));
    }

    public ListaPeliculasValoraciones getNRecomendaciones(int pNum) {
        ListaPeliculasValoraciones ls = new ListaPeliculasValoraciones();
        Iterator itr = recomendaciones.iterator();
        for (int i = 0; i < pNum && itr.hasNext(); i++) {
            AbstractMap.SimpleEntry<Integer, Double> current = (AbstractMap.SimpleEntry<Integer, Double>) itr.next();
            ls.add(current.getKey(), current.getValue());
        }
        return ls;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("##0.0000");
        this.recomendaciones.forEach(parPeliValor -> {
            sb.append("Pelicula : ");
            sb.append(parPeliValor.getKey());
            sb.append(" valoracion: ");
            sb.append(df.format(parPeliValor.getValue()));
            sb.append("\n");
        });
        return sb.toString();
    }

    public Integer[] toIntegerArray() {
        Integer[] res = new Integer[this.recomendaciones.size()];
        int i = 0;
        for (Map.Entry<Integer,Double> entry : this.recomendaciones.descendingSet()){
            res[i++] = entry.getKey();
        }
        return res;
    }

    /**
     * This method is only used in jUnit
     */
    public TreeSet<Map.Entry<Integer, Double>> getRecomendaciones() {
        return recomendaciones;
    }
}
