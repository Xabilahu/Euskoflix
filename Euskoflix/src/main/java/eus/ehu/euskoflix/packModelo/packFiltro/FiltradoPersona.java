package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;

import java.util.HashSet;

public class FiltradoPersona extends Filtrable {

    public FiltradoPersona() {
        super();
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);
    }

    @Override
    public ListaPeliculasValoraciones recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void calcularRecomendaciones() {
        int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        Similitud[] similitudes = super.getNMasSimilares(id);
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            super.generarValoracionRecomendada(true, noValorada, similitudes);
        }
    }
}
