package eus.ehu.euskoflix.packModelo;

public class Busqueda {

    private static Busqueda mBusqueda;

    public Busqueda() {
    }

    public static Busqueda getInstance() {
        if (mBusqueda == null) {
            mBusqueda = new Busqueda();
        }
        return mBusqueda;
    }

    public ListaPeliculas buscar(String pConsulta, Tipo pTipo){
        return null;
    }

}
