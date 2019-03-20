package eus.ehu.euskoflix.packModelo;

public class FiltradoProducto extends Filtrable {


    private static FiltradoProducto ourInstance = new FiltradoProducto();


    private FiltradoProducto() {
        //Se rellena la estructura con las similitudes entre productos
        //Meter por referencia
    }

    public static FiltradoProducto getInstance() {
        return ourInstance;

    }

    @Override
    public Similitud[] getNMasSimilares(int pId) {
        return new Similitud[0];
    }

    @Override
    public ListaPeliculas recomendar(int pId, int pNum) {
        return null;
    }
}
