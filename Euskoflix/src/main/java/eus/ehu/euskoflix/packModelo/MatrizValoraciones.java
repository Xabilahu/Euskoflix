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
        double similitud = 0;
        List<Double> valoracionU1 = new ArrayList<>();
        List<Double> valoracionU2 = new ArrayList<>(this.valoraciones.get(pPersona2.getId()).values());
        List<AbstractMap.SimpleEntry<Double,Double>> interseccion = new ArrayList<>();
        try{
            for (Double valoracion : this.valoraciones.get(pPersona1.getId()).values()){
                valoracionU1.add(pPersona1.normalizar(valoracion));
            }
            for (Double valoracion : this.valoraciones.get(pPersona2.getId()).values()){
                valoracionU2.add(pPersona2.normalizar(valoracion));
            }
            for (Map.Entry<Integer, Double> entry : this.valoraciones.get(pPersona1.getId()).entrySet()) {
                if (this.valoraciones.get(pPersona2.getId()).containsKey(entry.getKey())) {
                    interseccion.add(new AbstractMap.SimpleEntry<>(
                            pPersona1.normalizar(entry.getValue()),
                            pPersona2.normalizar(this.valoraciones.get(pPersona2.getId()).get(entry.getKey()))
                    ));
                }
            }
            if (!valoracionU1.isEmpty() && !valoracionU2.isEmpty() && !interseccion.isEmpty()){
                similitud = coseno(valoracionU1,valoracionU2,interseccion);
            }else{
                similitud = 0;
            }
        }catch (Exception ex){
            similitud = -1;
        }
        return new Similitud(pPersona1.getId(),pPersona2.getId(),similitud);
    }

    public Similitud simPelicula(Pelicula pPelicula1, Pelicula pPelicula2) {
        double similitud = 0;
        List<Double> valoracionP1 = new ArrayList<>();
        List<Double> valoracionP2 = new ArrayList<>();
        List<AbstractMap.SimpleEntry<Double,Double>> interseccion = new ArrayList<>();
        try {
            for (HashMap<Integer,Double> usuario:this.valoraciones.values()){
                if (usuario.containsKey(pPelicula1.getId()) && usuario.containsKey(pPelicula2.getId())){
                        valoracionP1.add(pPelicula1.normalizar(usuario.get(pPelicula1.getId())));
                        valoracionP2.add(pPelicula2.normalizar(usuario.get(pPelicula2.getId())));
                        interseccion.add(new AbstractMap.SimpleEntry<>(
                                pPelicula1.normalizar(usuario.get(pPelicula1.getId())),
                                pPelicula2.normalizar(usuario.get(pPelicula2.getId()))));
                }else if (usuario.containsKey(pPelicula1.getId())){
                    valoracionP1.add(pPelicula1.normalizar(usuario.get(pPelicula1.getId())));
                }else if (usuario.containsKey(pPelicula2.getId())){
                    valoracionP2.add(pPelicula2.normalizar(usuario.get(pPelicula2.getId())));
                }
            }
            if (!valoracionP1.isEmpty() && !valoracionP2.isEmpty() && !interseccion.isEmpty()){
                similitud = coseno(valoracionP1,valoracionP2,interseccion);
            }else{
                similitud = 0;
            }
        } catch (Exception e) {
            //TODO bueno , ya sabes
            similitud=-1;
           // e.printStackTrace();
        }

        return new Similitud(pPelicula1.getId(),pPelicula2.getId(),similitud);
    }

    private double coseno(List<Double> vn,List<Double> wn, List<AbstractMap.SimpleEntry<Double,Double>> interseccion){
        double numerador = 0.0;
        double sum1 = 0.0;
        double sum2 = 0.0;

        for (AbstractMap.SimpleEntry<Double,Double> intersec : interseccion){
            numerador += intersec.getKey()*intersec.getValue();
        }
        for (Double val : vn){
            sum1 += val*val;
        }
        for (Double val : wn){
            sum2 += val*val;
        }
        return numerador/(Math.sqrt(sum1)*Math.sqrt(sum2));
    }

    //TODO devolver valor absoluto o no
    private Similitud coseno(Usuario pPersona1, Usuario pPersona2){
        double numerador = 0.0;
        double sumU1 = 0;
        double sumU2 = 0;
        int peli=0;
        try {
            for (Map.Entry<Integer, Double> entry : this.valoraciones.get(pPersona1.getId()).entrySet()) {
                if (this.valoraciones.get(pPersona2.getId()).containsKey(entry.getKey())) {
                    peli = entry.getKey();
                    numerador += pPersona1.normalizar(entry.getValue()) * pPersona2.normalizar(this.valoraciones.get(pPersona2.getId()).get(entry.getKey()));
                }
            }
            if (numerador != 0) {
                for (Map.Entry<Integer, Double> entry : this.valoraciones.get(pPersona1.getId()).entrySet()) {
                    sumU1 += Math.pow(pPersona1.normalizar(entry.getValue()), 2);
                }
                for (Map.Entry<Integer, Double> entry : this.valoraciones.get(pPersona2.getId()).entrySet()) {
                    sumU2 += Math.pow(pPersona2.normalizar(entry.getValue()), 2);
                }

                sumU1 = Math.sqrt(sumU1);
                sumU2 = Math.sqrt(sumU2);
                return new Similitud(pPersona1.getId(), pPersona2.getId(), Math.abs(numerador / (sumU1 * sumU2)));
            } else {
                return new Similitud(pPersona1.getId(), pPersona2.getId(), 0);
            }
        } catch (Exception e){
            //TODO hacer algo con esto
            return new Similitud(pPersona1.getId(), pPersona2.getId(), 0);
        }
    }

    private double mapearDistancia(double pValor) {
        return 1-(pValor*(1.0/5));
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

    public HashMap<Integer,Double> getValoracionesByUsuario(int pId) {
        HashMap<Integer, Double> resultado = new HashMap<>();
        this.valoraciones.get(pId).forEach(((integer, aDouble) -> {
            try {
                resultado.put(integer, CatalogoUsuarios.getInstance().getUsuarioPorId(pId).normalizar(aDouble));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        return resultado;
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

    public boolean tieneValoracionesUsuario(int pId) {
        return this.valoraciones.get(pId) != null && this.valoraciones.get(pId).size() != 0;
    }

    public void cargarValoracionesNormalizadas(Pelicula pelicula) {
        double media = 0;
        double desvTipica = 0;
        ArrayList<Double> valoraciones = new ArrayList<>();
        int numUsuariosPelicula = 0;

        for(HashMap<Integer,Double> peliculas : this.valoraciones.values()){
            if (peliculas.containsKey(pelicula.getId())){
                numUsuariosPelicula++;
                media+=peliculas.get(pelicula.getId());
                valoraciones.add(peliculas.get(pelicula.getId()));
            }
        }
        pelicula.setMedia(media/numUsuariosPelicula);
       for(Double valoracion: valoraciones){
            desvTipica += Math.pow(valoracion-pelicula.getMedia(),2);
        }
        pelicula.setCuasiDesv(desvTipica/numUsuariosPelicula);
    }

    public boolean tieneValoracionesPelicula(int id) {
        boolean valorada = false;
        Iterator<HashMap<Integer,Double>> itr = this.valoraciones.values().iterator();
        while (itr.hasNext() && !(valorada = itr.next().containsKey(id)));
        return valorada;
    }
}
