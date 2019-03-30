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
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            double numerador = 0.0;
            double denominador = 0.0;
            Similitud[] similitudes = super.getNMasSimilares(noValorada);
            generacionRecomendaciones(noValorada, numerador, denominador, similitudes);
        }
    }



}
