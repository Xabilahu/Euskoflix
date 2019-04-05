package eus.ehu.euskoflix.packModelo;

import java.util.HashMap;

public class ListaTags {

    private HashMap<Tag, Tag> lista;

    public ListaTags() {
        this.lista = new HashMap<>();
    }

    public void addTag(Tag pTag) {
        if (!this.lista.containsKey(pTag)) this.lista.put(pTag, pTag);
    }

    public int numTags() {
        return this.lista.size();
    }

    public String[][] tagsToStringArray() {
        String[][] resultado = new String[this.lista.size()][2];
        int i = 0;
        for (Tag t : this.lista.keySet()) {
            resultado[i][0] = t.getNombre();
            resultado[i++][1] = String.valueOf(t.getCantidad());
        }
        return resultado;
    }

    public void rellenarTf(ListaEtiquetasFiltrado pTf, int pIdPelicula) {
        double denominador = 0.0;
        for (Tag tag : this.lista.keySet()) {
            denominador += tag.getCantidad() * tag.getCantidad();
        }
        denominador = Math.sqrt(denominador);
        final double tmp = denominador;
        this.lista.keySet().forEach(tag -> {
            pTf.add(pIdPelicula, tag, (double) tag.getCantidad() / tmp);
        });
    }
}
