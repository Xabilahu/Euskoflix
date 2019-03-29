package eus.ehu.euskoflix.packModelo;

import java.util.HashSet;

public class FiltradoPersona extends Filtrable {


    public FiltradoPersona() {
        //TODO: cargar filtrado
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void cargar() {
        int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        ListaPeliculas ls = new ListaPeliculas();
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
                            MatrizValoraciones.getInstance().getValoracion(similitud.getJ(), noValorada)
                                    * similitud.getSim());
                    denominador += similitud.getSim();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            super.addRecomendacion(noValorada,
                    //CatalogoUsuarios.getInstance().getUsuarioLogueado().desnormalizar(numerador/denominador)
                    numerador / denominador
            );
        }
    }
}
