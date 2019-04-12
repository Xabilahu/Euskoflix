package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;

import java.util.HashSet;

public class FiltradoProducto extends Filtrable {

    public FiltradoProducto() {
        super();
    }

    @Override
    public ListaPeliculasValoraciones recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void calcularRecomendaciones() {
        Cartelera.getInstance().cargarModeloProductos(this);
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            Similitud[] similitudes = super.getNMasSimilares(noValorada);
            generarValoracionRecomendada(false, noValorada, similitudes);
        }
    }


}
