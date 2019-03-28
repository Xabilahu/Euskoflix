package eus.ehu.euskoflix.packModelo;

public class FiltradoPersona extends Filtrable {

    private int userID;

    public FiltradoPersona(int pUserID) {
        //TODO: cargar filtrado
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
