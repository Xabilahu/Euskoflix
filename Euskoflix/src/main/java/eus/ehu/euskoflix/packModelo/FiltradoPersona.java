package eus.ehu.euskoflix.packModelo;

public class FiltradoPersona extends Filtrable {
    private static FiltradoPersona ourInstance = new FiltradoPersona();

    private FiltradoPersona() {
    }

    public static FiltradoPersona getInstance() {
        return ourInstance;
    }

    public ListaPeliculas filtradoPorPersona() {
        return null;
    }

    @Override
    public Similitud[] getNMasSimilares(int pId) {
        return null;
    }

    @Override
    public ListaPeliculas recomendar(int pId, int pNum) {
        return null;
    }
}
