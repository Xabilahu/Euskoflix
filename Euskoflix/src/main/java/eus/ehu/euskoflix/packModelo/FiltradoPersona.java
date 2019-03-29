package eus.ehu.euskoflix.packModelo;

import java.util.*;

public class FiltradoPersona extends Filtrable {

    ListaPeliculasRecomendadas recomendados;

    public FiltradoPersona() {
        //TODO: cargar filtrado
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);
    }

    public ListaPeliculas filtradoPorPersona() {
        return null;
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        if (this.recomendados == null){
            int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
            this.recomendados = new ListaPeliculasRecomendadas();
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
                this.recomendados.add(noValorada,
                        //CatalogoUsuarios.getInstance().getUsuarioLogueado().desnormalizar(numerador/denominador)
                        numerador/denominador
                );
            }
        }
        return this.recomendados.getNRecomendaciones(pNum);
    }
}
