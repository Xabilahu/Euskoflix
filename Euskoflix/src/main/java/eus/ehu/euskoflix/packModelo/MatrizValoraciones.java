package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.util.*;
import java.util.function.BiConsumer;

public class MatrizValoraciones {

    private static MatrizValoraciones ourInstance;

    private HashMap<Integer,HashMap<Integer,Double>> valoraciones;


    private MatrizValoraciones() {
    }

    public static MatrizValoraciones getInstance() {
        if (ourInstance == null) {
            ourInstance = new MatrizValoraciones();
        }
        return ourInstance;

    }

    public void cargarValoraciones() {
        valoraciones = GestionDatos.getInstance().cargarValoraciones();

    }

    public Similitud simPersonas(Usuario pPersona1, Usuario pPersona2) {
       return coseno(pPersona1,pPersona2);
    }

    private Similitud coseno(Usuario pPersona1, Usuario pPersona2){
        double numerador = 0.0;
        double sumU1 = 0;
        double sumU2 = 0;
        // <- -> ?? (-1)
        // |_ 0
        // = 1
        for (Map.Entry<Integer, Double> entry: this.valoraciones.get(pPersona1.getId()).entrySet()) {
            if (this.valoraciones.get(pPersona2.getId()).containsKey(entry.getKey())){
                numerador += pPersona1.normalizar(entry.getValue())*pPersona2.normalizar(this.valoraciones.get(pPersona2.getId()).get(entry.getKey()));
              //  sumU1 += Math.pow(pPersona1.normalizar(entry.getValue()),2);
              //  sumU2 += Math.pow(pPersona2.normalizar(this.valoraciones.get(pPersona2.getId()).get(entry.getKey())),2);
            }
        }
        if (numerador != 0){
            for (Map.Entry<Integer, Double> entry: this.valoraciones.get(pPersona1.getId()).entrySet()){
                sumU1 += Math.pow(pPersona1.normalizar(entry.getValue()),2);
            }
            for (Map.Entry<Integer, Double> entry: this.valoraciones.get(pPersona2.getId()).entrySet()){
                sumU2 += Math.pow(pPersona2.normalizar(entry.getValue()),2);
            }

            sumU1 = Math.sqrt(sumU1);
            sumU2 = Math.sqrt(sumU2);
            return new Similitud(pPersona1.getId(), pPersona2.getId(), numerador/(sumU1*sumU2));
        }else{
            return new Similitud(pPersona1.getId(), pPersona2.getId(), -1);
        }
    }

    public Similitud simPelicula(int pPelicula1, int pPelicula2) {
        return null;
    }

    public double getValoracion(int pUsuario, int pPelicula) {
        double valoracion = -1f;
        HashMap<Integer,Double> valoracionesUsuario = this.valoraciones.get(pUsuario);
        if (valoracionesUsuario != null){
            Double valoracionUP = valoracionesUsuario.get(pPelicula);
            if (valoracionUP != null){
              valoracion = valoracionUP;
            }
        }
        return valoracion;
    }

    public LinkedHashMap<Integer, Double> getValoracionesByPelicula(int pId) {
        LinkedHashMap<Integer, Double> resultado = new LinkedHashMap<>();
        this.valoraciones.forEach((usuario, peliculasValoradas) -> {
            if (peliculasValoradas.containsKey(pId)){
                resultado.put(usuario,peliculasValoradas.get(pId));
            }
        });
        return resultado;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatrizValoraciones{");
        this.valoraciones.forEach((integer, integerDoubleHashMap) -> {
            sb.append("Usuario :");
            sb.append(integer);
            sb.append("\n");
            sb.append("\t{");
            integerDoubleHashMap.forEach((integer1, aDouble) -> {
                sb.append(integer1);
                sb.append("->");
                sb.append(aDouble);
                sb.append(", ");
            });
            sb.replace(sb.length()-2,sb.length(),"}");
            sb.append("\n");
        });
        return sb.toString();
    }

    public void cargarValoracionesNormalizadas(Usuario pUsuario) {
        double media = 0;
        double desvTipica = 0;

            int n= this.valoraciones.get(pUsuario.getId()).size();
            for (Double d: this.valoraciones.get(pUsuario.getId()).values()) {
                media+=d;
            }
            if (this.valoraciones.get(pUsuario.getId()) != null){
                media = media / n;
            }
            pUsuario.setMedia(media);
            for (Double d: this.valoraciones.get(pUsuario.getId()).values()) {
                desvTipica += Math.pow(d-media,2);
            }
            pUsuario.setCuasiDesv(Math.sqrt(desvTipica/n));
    }

    public boolean tieneValoraciones(int pId) {
        return this.valoraciones.get(pId) != null || this.valoraciones.get(pId).size() != 0;
    }
}
