package eus.ehu.euskoflix.packModelo;

public class MatrizValoraciones {

    private static MatrizValoraciones ourInstance = new MatrizValoraciones();

    private float[] valores;
    private int[] filas;
    private int[] columnas;


    public static MatrizValoraciones getInstance() {
        return ourInstance;
    }

    private MatrizValoraciones() {
        //Rellenar

    }

    public Similitud simPersonas(int pPersona1, int pPersona2) {
        return null;
    }

    public Similitud simPelicula(int pPelicula1, int pPelicula2) {
        return null;
    }

    public float getValoracion(int pUsuario, int pPelicula) {
        return 0.0f;
    }
}
