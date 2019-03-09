package eus.ehu.euskoflix.packModelo;

public class Cartelera {

    private static Cartelera mCartelera;
    private ListaPeliculas lista;

    private Cartelera(){
        this.lista = new ListaPeliculas();
    }

    public static Cartelera getInstance(){
        if (mCartelera == null) {
            mCartelera = new Cartelera();
        }
        return mCartelera;
    }

    public void cargarDatos() {

    }

}
