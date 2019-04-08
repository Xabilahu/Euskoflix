package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.Tag;

import java.util.HashSet;

public class FiltradoContenido extends Filtrable {

    private ListaEtiquetasFiltrado tfidf;

    public FiltradoContenido() {
        super();
    }

    public void cargarTF(ListaEtiquetasFiltrado pTf) {
        this.tfidf = pTf;
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void calcularRecomendaciones() {
        this.tfidf.cargarIdfs();
        this.tfidf.calcularRelevanciasSimilitudes(this);
        Similitud[] similitudes = super.getSimilares(CatalogoUsuarios.getInstance().getUsuarioLogueado().getId());
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            super.generarValoracionRecomendada(true, noValorada, similitudes);
        }
    }

    /**
     * This method is only used in jUnit
     */
    public ListaEtiquetasFiltrado getTfidf() {
        return tfidf;
    }

    public void addPeliculaAEtiqueta(Tag pTag, int peli){
        this.tfidf.addPeliculaAEtiqueta(pTag, peli);
    }

    /*public void cargarEstructuraEtiquetas(HashMap<Tag, HashSet<Integer>> pEstruct) {
        this.tfidf.cargarEstructuraEtiquetas(pEstruct);
    }*/
}
