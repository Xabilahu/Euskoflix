package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class MatrizValoraciones {

    private static MatrizValoraciones ourInstance;

    private float[] valores;
    private int[] filas;
    private int[] columnas;


    private MatrizValoraciones() {
    }

    public static MatrizValoraciones getInstance() {
        if (ourInstance == null) {
            ourInstance = new MatrizValoraciones();
        }
        return ourInstance;

    }

    public void cargarValoraciones() {
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
            int limiteBucle;
            if (pUsuario == this.filas.length - 1) {
                limiteBucle = this.columnas.length;
            } else {
                limiteBucle = this.filas[pUsuario + 1];
            }
            for (int i = pos; i < limiteBucle; i++) {
                if (this.columnas[i] == pPelicula) {
                    valoracion = this.valores[i];
                    break;
                }
            }
        }
        return valoracion;
    }

    public LinkedHashMap<Integer, Float> getValoracionesByPelicula(int pId) {
        LinkedHashMap<Integer, Float> resultado = new LinkedHashMap<>();
        //Buscamos el primer usuario que haya valorado alguna pelicula
        int usuarioActual = 0;
        for (int i = 1; i < this.filas.length; i++) {
            if (this.filas[i] != -1) {
                usuarioActual = i;
                break;
            }
        }
        boolean ultimo = false;
        //Vamos añadiendo los usuarios y sus valoraciones
        for (int i = 1; i < this.columnas.length; i++) {
            if (this.columnas[i] == pId) {
                for (int j = usuarioActual + 1; j < this.filas.length; j++) {
                    if (this.filas[j] != -1) {
                        usuarioActual = j;
                        break;
                    }
                }
                if (usuarioActual != this.filas.length - 1) {
                    resultado.put(usuarioActual - 1, this.valores[i]);
                } else if (!ultimo) {
                    ultimo = true;
                    resultado.put(usuarioActual - 1, this.valores[i]);
                } else {
                    resultado.put(usuarioActual, this.valores[i]);
                    break;
                }
                i = this.filas[usuarioActual] - 1;
            } else if (this.columnas[i] > pId) {
                for (int j = usuarioActual + 1; j < this.filas.length; j++) {
                    if (this.filas[j] != -1) {
                        usuarioActual = j;
                        break;
                    }
                }
                if (usuarioActual == this.filas.length - 1) {
                    break;
                }
                i = this.filas[usuarioActual] - 1;
            }
        }
        return resultado;
    }

    /**
     * This method is only used in jUnit
     */
    public float[] getValoraciones() {
        return this.valores;
    }

    /**
     * This method is only used in jUnit
     */
    public int[] getColumnas() {
        return this.columnas;
    }

    /**
     * This method is only used in jUnit
     */
    public int[] getFilas() {
        return this.filas;
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

    public void cargarValoracionesNormalizadas() {

    }
}
