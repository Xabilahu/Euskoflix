package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packModelo.Cartelera;

import java.text.DecimalFormat;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class ListaPeliculasValoraciones {

    private TreeSet<Map.Entry<Integer, Double>> recomendaciones;

    public ListaPeliculasValoraciones() {
        this.recomendaciones = new TreeSet<>((o1, o2) -> Double.compare(o1.getValue(), o2.getValue()) * -1);
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

    public String[][] toStringArray() {
        String[][] res = new String[this.recomendaciones.size()][2];
        Iterator<Map.Entry<Integer,Double>> itr = this.recomendaciones.iterator();
        for (int i = 0; itr.hasNext(); i++) {
            Map.Entry<Integer,Double> entry = itr.next();
            res[i][0] = Cartelera.getInstance().getPeliculaPorIdSinMapeo(entry.getKey()).getTitulo();
            res[i][1] = entry.getValue().toString();
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
