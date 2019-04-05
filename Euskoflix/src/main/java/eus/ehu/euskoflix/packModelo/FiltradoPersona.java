package eus.ehu.euskoflix.packModelo;

import java.util.HashSet;

public class FiltradoPersona extends Filtrable {


    public FiltradoPersona() {
        super();
        CatalogoUsuarios.getInstance().cargarModeloPersona(this);
    }

    @Override
    public ListaPeliculasRecomendadas recomendar(int pNum) {
        return super.getNRecomendaciones(pNum);
    }

    @Override
    public void calcularRecomendaciones() {
        System.out.println("-------Filtrado Persona--------");
        int id = CatalogoUsuarios.getInstance().getUsuarioLogueado().getId();
        Similitud[] similitudes = super.getNMasSimilares(id);
        HashSet<Integer> noValoradas = Cartelera.getInstance().getPeliculasNoValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado());
        for (Integer noValorada : noValoradas) {
            super.generarValoracionRecomendada(true, noValorada, similitudes);
        }
    }
}
