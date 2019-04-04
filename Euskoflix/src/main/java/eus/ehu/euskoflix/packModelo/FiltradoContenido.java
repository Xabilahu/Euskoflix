package eus.ehu.euskoflix.packModelo;

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
    public void calcularRecomendaciones() {
        this.tfidf.cargarIdfs();
    }

}
