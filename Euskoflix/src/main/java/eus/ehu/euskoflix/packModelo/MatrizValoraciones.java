package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.Arrays;
import java.util.HashMap;
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

    public Similitud simPersonas(Usuario pPersona1, Usuario pPersona2) {
        double numerador = 0.0;
        HashMap<Integer,Float> comunes = new HashMap<>();
        for(int u1 = this.filas[pPersona1.getId()]; u1 < this.filas[pPersona1.getId() + 1]; u1++){
            comunes.put(this.columnas[u1], this.valores[u1]);
        }
        for (int u2 = this.filas[pPersona2.getId()]; u2 < this.filas[pPersona2.getId() + 1]; u2++) {
            if (comunes.containsKey(this.columnas[u2])) {
                numerador += (pPersona1.normalizar(comunes.get(this.columnas[u2])) - pPersona1.getMedia()) *
                        (pPersona2.normalizar(this.valores[u2]) - pPersona2.getMedia());
            }
        }
        return new Similitud(pPersona1.getId(), pPersona2.getId(), numerador/(pPersona1.getCuasiDesv() * pPersona2.getCuasiDesv()));
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

    public void cargarValoracionesNormalizadas(Usuario pUsuario) {
        double f = 0.0;
        int i;
        int limite;
        if (pUsuario.getId() != this.filas.length-1){
            limite = this.filas[pUsuario.getId()+1];
        }else{
            limite = this.valores.length;
        }
        for(i = this.filas[pUsuario.getId()]; i < limite; i++) {
            f += this.valores[i];
        }
        int n = i - this.filas[pUsuario.getId()];
        double media = f/(n);
        pUsuario.setMedia(media);
        f = 0.0;
        for (i = this.filas[pUsuario.getId()];i < limite; i++) {
            f += Math.pow(this.valores[i] - media, 2);
        }
        pUsuario.setCuasiDesv(Math.sqrt(f/n));
    }
}
