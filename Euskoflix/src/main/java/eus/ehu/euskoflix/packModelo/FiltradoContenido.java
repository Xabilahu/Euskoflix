package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.HashMap;
import java.util.Set;

public class FiltradoContenido extends Filtrable {


    private ListaEtiquetasFiltrado tfidf;

    public FiltradoContenido() {
    }

    public void cargarTF(ListaEtiquetasFiltrado pTf) {
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
        this.tfidf.cargarIdfs();
    }

}
