package eus.ehu.euskoflix.packModelo;

import java.util.HashSet;

public class FiltradoProducto extends Filtrable {


    public FiltradoProducto() {
        //TODO: encapsularlo en Cartelera y cambiar su ListaPeliculas a un HashMap
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
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void cargar() {
        Cartelera.getInstance().cargarModeloProductos(this);
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
