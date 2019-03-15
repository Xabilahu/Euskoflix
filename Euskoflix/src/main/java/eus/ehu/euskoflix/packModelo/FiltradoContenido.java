package eus.ehu.euskoflix.packModelo;

public class FiltradoContenido extends Filtrable {
    private static FiltradoContenido ourInstance = new FiltradoContenido();

    public static FiltradoContenido getInstance() {
        return ourInstance;
    }

    private FiltradoContenido() {
    }

    @Override
    public Similitud[] getNMasSimilares(int pId) {
        return null;
    }

    @Override
    public ListaPeliculas recomendar(int pId, int pNum) {
        return null;
    }

    //HashMap < Tag,nt>
    //HashMap <Tag,ArrayList<Pelicula,int>>

}
