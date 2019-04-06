package eus.ehu.euskoflix.packModelo;

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
    public Similitud[] getNMasSimilares(int pId) {
        return null;
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void calcularRecomendaciones() {
        this.tfidf.cargarIdfs();
        this.tfidf.calcularRelevanciasSimilitudes(this);
        Similitud[] similitudes = super.getNMasSimilares(CatalogoUsuarios.getInstance().getUsuarioLogueado().getId());
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            super.generarValoracionRecomendada(true, noValorada, similitudes);
        }
    }

}
