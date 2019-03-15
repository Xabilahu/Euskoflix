package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packDatos.BaseDatos;

import java.util.Arrays;

public class MatrizValoraciones {

    private static MatrizValoraciones ourInstance;

    private float[] valores;
    private int[] filas;
    private int[] columnas;


    public static MatrizValoraciones getInstance() {
        if (ourInstance== null) {
            ourInstance = new MatrizValoraciones();
        }
        return ourInstance;

    }

    private MatrizValoraciones() {
        //Rellenar
        valores = GestionDatos.getInstance().getValoraciones();
        filas = GestionDatos.getInstance().getValoracionesUsuarios();
        columnas = GestionDatos.getInstance().getValoracionesPeliculas();
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatrizValoraciones{");
        sb.append("valores=").append(Arrays.toString(valores));
        sb.append("\n");
        sb.append(", filas=").append(Arrays.toString(filas));
        sb.append("\n");
        sb.append(", columnas=").append(Arrays.toString(columnas));
        sb.append('}');
        return sb.toString();
    }
}