package eus.ehu.euskoflix.packModelo;

import java.util.*;

public class FiltradoPersona extends Filtrable {

    public FiltradoPersona() {
        //TODO: cargar filtrado
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);
    }

    public ListaPeliculas filtradoPorPersona() {
        return null;
    }

    @Override
    public ListaPeliculas recomendar(int pNum) {
        int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        TreeSet<Map.Entry<Integer,Double>> recomendados = new TreeSet<>( (o1, o2) -> Double.compare(o1.getValue(),o2.getValue())*-1);
        ListaPeliculas ls  = new ListaPeliculas();
        Similitud[] similitudes = super.getNMasSimilares(id);
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            double numerador = 0.0;
            double denominador = 0.0;

            for (Similitud similitud : similitudes) {
                try {
                    //No hay que desnormalizar ni normalizar la valoracion porque la valoracion ya se encuentra en la muestra
                    //Solo en el coseno se normaliza para conseguir el ángulo de similitud teniendo en cuenta un dos muestras parecidas
                    numerador += Math.abs(
                            MatrizValoraciones.getInstance().getValoracion(similitud.getJ(),noValorada)
                            *similitud.getSim());
                    denominador+= similitud.getSim();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            recomendados.add(new AbstractMap.SimpleEntry<>(noValorada,
                   //CatalogoUsuarios.getInstance().getUsuarioLogueado().desnormalizar(numerador/denominador)
                    numerador/denominador
            ));
        }
        for (Map.Entry<Integer, Double> recomendado : recomendados) {
            ls.addPelicula(Cartelera.getInstance().getPeliculaPorIdSinMapeo(recomendado.getKey()));
        }
        return ls;
    }
}
