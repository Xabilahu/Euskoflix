package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packModelo.packFiltro.ListaPeliculasValoraciones;
import eus.ehu.euskoflix.packModelo.packFiltro.Similitud;

import java.util.*;

public class MatrizValoraciones {

    private static MatrizValoraciones ourInstance;
    private HashMap<Integer, HashMap<Integer, Double>> valoraciones;

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
        List<Double> valoracionU2 = new ArrayList<>();
        List<AbstractMap.SimpleEntry<Double, Double>> interseccion = new ArrayList<>();
        for (Double valoracion : this.valoraciones.get(pPersona1.getId()).values()) {
            valoracionU1.add(CatalogoUsuarios.getInstance().getUsuarioPorId(pPersona1.getId()).normalizar(valoracion));
        }
        for (Double valoracion : this.valoraciones.get(pPersona2.getId()).values()) {
            valoracionU2.add(CatalogoUsuarios.getInstance().getUsuarioPorId(pPersona2.getId()).normalizar(valoracion));
        }
        for (Map.Entry<Integer, Double> entry : this.valoraciones.get(pPersona1.getId()).entrySet()) {
            if (this.valoraciones.get(pPersona2.getId()).containsKey(entry.getKey())) {
                interseccion.add(new AbstractMap.SimpleEntry<>(
                        CatalogoUsuarios.getInstance().getUsuarioPorId(pPersona1.getId()).normalizar(entry.getValue()),
                        CatalogoUsuarios.getInstance().getUsuarioPorId(pPersona2.getId()).normalizar(this.valoraciones.get(pPersona2.getId()).get(entry.getKey()))
                ));
            }
        }
        if (!valoracionU1.isEmpty() && !valoracionU2.isEmpty() && !interseccion.isEmpty()) {
            similitud = coseno(valoracionU1, valoracionU2, interseccion);
        }
        return new Similitud(pPersona1.getId(), pPersona2.getId(), similitud);
    }

    public Similitud simPelicula(Pelicula pPelicula1, Pelicula pPelicula2) {
        double similitud = 0;
        List<Double> valoracionP1 = new ArrayList<>();
        List<Double> valoracionP2 = new ArrayList<>();
        List<AbstractMap.SimpleEntry<Double, Double>> interseccion = new ArrayList<>();
        for (HashMap<Integer, Double> usuario : this.valoraciones.values()) {
            if (usuario.containsKey(pPelicula1.getId()) && usuario.containsKey(pPelicula2.getId())) {
                valoracionP1.add(pPelicula1.normalizar(usuario.get(pPelicula1.getId())));
                valoracionP2.add(pPelicula2.normalizar(usuario.get(pPelicula2.getId())));
                interseccion.add(new AbstractMap.SimpleEntry<>(
                        pPelicula1.normalizar(usuario.get(pPelicula1.getId())),
                        pPelicula2.normalizar(usuario.get(pPelicula2.getId()))));
            } else if (usuario.containsKey(pPelicula1.getId())) {
                valoracionP1.add(pPelicula1.normalizar(usuario.get(pPelicula1.getId())));
            } else if (usuario.containsKey(pPelicula2.getId())) {
                valoracionP2.add(pPelicula2.normalizar(usuario.get(pPelicula2.getId())));
            }
        }
        if (!valoracionP1.isEmpty() && !valoracionP2.isEmpty() && !interseccion.isEmpty()) {
            similitud = coseno(valoracionP1, valoracionP2, interseccion);
        }
        return new Similitud(pPelicula1.getId(), pPelicula2.getId(), similitud);
    }

    public double coseno(List<Double> vn, List<Double> wn, List<AbstractMap.SimpleEntry<Double, Double>> interseccion) {
        double numerador = 0.0;
        double sum1 = 0.0;
        double sum2 = 0.0;

        for (AbstractMap.SimpleEntry<Double, Double> intersec : interseccion) {
            numerador += intersec.getKey() * intersec.getValue();
        }
        for (Double val : vn) {
            sum1 += val * val;
        }
        for (Double val : wn) {
            sum2 += val * val;
        }
        double res = numerador / (Math.sqrt(sum1) * Math.sqrt(sum2));
        if (Double.isNaN(res)) {
            res = 0.0;
        }
        return res;
    }

    public double getValoracion(int pUsuario, int pPelicula) {
        double valoracion = 0;
        HashMap<Integer, Double> valoracionesUsuario = this.valoraciones.get(pUsuario);
        if (valoracionesUsuario != null) {
            Double valoracionUP = valoracionesUsuario.get(pPelicula);
            if (valoracionUP != null) {
                valoracion = valoracionUP;
            }
        }
        return valoracion;
    }

    public LinkedHashMap<Integer, Double> getValoracionesByPelicula(int pId) {
        LinkedHashMap<Integer, Double> resultado = new LinkedHashMap<>();
        this.valoraciones.forEach((usuario, peliculasValoradas) -> {
            if (peliculasValoradas.containsKey(pId)) {
                resultado.put(usuario, peliculasValoradas.get(pId));
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
            sb.replace(sb.length() - 2, sb.length(), "}");
            sb.append("\n");
        });
        return sb.toString();
    }

    public HashMap<Integer, LinkedList<Integer>> getUsersPeliculasByLimite(double pLim) {
        HashMap<Integer, LinkedList<Integer>> resultado = new HashMap<>();
        this.valoraciones.forEach((i, map) -> {
            resultado.put(i, new LinkedList<>());
            map.forEach((p, val) -> {
                if (Double.compare(val, pLim) >= 0) {
                    resultado.get(i).add(p);
                }
            });
        });
        return resultado;
    }

    public void cargarMediasDesv(Usuario pUsuario) {
        double media = 0;
        double desvTipica = 0;

        double n = this.valoraciones.get(pUsuario.getId()).size();
        if (n != 0) {
            for (Double d : this.valoraciones.get(pUsuario.getId()).values()) {
                media += d;
            }
            media /= n;
        }
        pUsuario.setMedia(media);
        for (Double d : this.valoraciones.get(pUsuario.getId()).values()) {
            desvTipica += Math.pow(d - media, 2);
        }
        pUsuario.setCuasiDesv(Math.sqrt(desvTipica / (n - 1)));
    }

    public boolean tieneValoracionesUsuario(int pId) {
        return this.valoraciones.get(pId) != null && this.valoraciones.get(pId).size() != 0;
    }

    public void cargarMediasDesv(Pelicula pelicula) {
        double media = 0;
        double desvTipica = 0;
        ArrayList<Double> valoraciones = new ArrayList<>();
        int numUsuariosPelicula = 0;

        for (HashMap<Integer, Double> peliculas : this.valoraciones.values()) {
            if (peliculas.containsKey(pelicula.getId())) {
                numUsuariosPelicula++;
                media += peliculas.get(pelicula.getId());
                valoraciones.add(peliculas.get(pelicula.getId()));
            }
        }
        pelicula.setMedia(media / numUsuariosPelicula);
        for (Double valoracion : valoraciones) {
            desvTipica += Math.pow(valoracion - pelicula.getMedia(), 2);
        }
        pelicula.setCuasiDesv(Math.sqrt(desvTipica / numUsuariosPelicula));
    }

    public boolean tieneValoracionesPelicula(int id) {
        boolean valorada = false;
        Iterator<HashMap<Integer, Double>> itr = this.valoraciones.values().iterator();
        while (itr.hasNext() && !(valorada = itr.next().containsKey(id))) ;
        return valorada;
    }

    public Set<Integer> getPeliculasValoradas(int pId) {
        return this.valoraciones.get(pId).keySet();
    }

    public ListaPeliculasValoraciones getPeliculasVistas(int pId) {
        ListaPeliculasValoraciones lpv = new ListaPeliculasValoraciones();
        this.valoraciones.get(pId).forEach((i,v) -> lpv.add(i,v));
        return lpv;
    }

}
