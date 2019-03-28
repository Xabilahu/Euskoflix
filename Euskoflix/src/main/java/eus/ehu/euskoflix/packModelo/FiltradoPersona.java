package eus.ehu.euskoflix.packModelo;

public class FiltradoPersona extends Filtrable {

    public FiltradoPersona() {
        //TODO: cargar filtrado
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);

    }

    public ListaPeliculas filtradoPorPersona() {
        return null;
    }

    @Override
    public ListaPeliculas recomendar(int pId, int pNum) {
        return null;
    }
}
