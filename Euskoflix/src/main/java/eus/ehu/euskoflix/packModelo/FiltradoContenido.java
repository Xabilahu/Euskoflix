package eus.ehu.euskoflix.packModelo;

public class FiltradoContenido extends Filtrable {

    public FiltradoContenido() {

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

    }

    //HashMap < Tag,nt>
    //HashMap <Tag,ArrayList<Pelicula,int>>

}
