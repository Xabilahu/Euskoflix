package eus.ehu.euskoflix.packModelo;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

public class FiltradoProducto extends Filtrable {


    ListaPeliculasRecomendadas recomendados;


    public FiltradoProducto() {
        //TODO: encapsularlo en Cartelera y cambiar su ListaPeliculas a un HashMap
        Cartelera.getInstance().cargarModeloProductos(this);
       /* Cartelera c = Cartelera.getInstance();
        MatrizValoraciones m = MatrizValoraciones.getInstance();
        for (int i = 1; i < c.getNumPeliculas(); i++) {
            Pelicula p = c.getPeliculaPorId(i);
            for (int j = i + 1; j < c.getNumPeliculas(); j++) {
                super.addSimilitudSimetrica(m.simPelicula(p, c.getPeliculaPorId(j)));
            }
            System.out.println("Done : pelicula" + p.getId());
        }*/
    }



    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        if (this.recomendados == null){
            this.recomendados = new ListaPeliculasRecomendadas();
            int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
            HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());

            for (Integer noValorada : noValoradas) {
                double numerador = 0.0;
                double denominador = 0.0;
                Similitud[] similitudes = super.getNMasSimilares(noValorada);
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
                recomendados.add(noValorada,
                        //CatalogoUsuarios.getInstance().getUsuarioLogueado().desnormalizar(numerador/denominador)
                        numerador/denominador
                );
            }
        }
        return this.recomendados.getNRecomendaciones(pNum);
    }
}
