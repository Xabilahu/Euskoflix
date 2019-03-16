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
    }

    public void cargarValoraciones(){
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
        int pos = this.filas[pUsuario];
        float valoracion = -1f;
        if (pos != -1) {
            for (int i = pos; i < this.filas[pUsuario + 1]; i++) {
                if (this.columnas[i] == pPelicula) {
                    valoracion = this.valores[i];
                    break;
                }
            }
        }
        return valoracion;
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
