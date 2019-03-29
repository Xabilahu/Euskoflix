package eus.ehu.euskoflix.packModelo;

import java.util.LinkedList;

public class Filtrado {

    private static Filtrado ourInstance = new Filtrado();
    private static Filtrable[] filtros;


    public static Filtrado getInstance() {
        return ourInstance;
    }

    private Filtrado() {
        filtros = new Filtrable[3];
    }

    private void cargarModeloPersona() {
        filtros[0] = new FiltradoPersona();
    }

    private void cargarModeloProductos() {
        filtros[1] = new FiltradoProducto();
    }

    private void cargarModeloContenido() {
        filtros[2] = new FiltradoContenido();
    }

    public ListaPeliculasRecomendadas recomendar(TipoRecomendacion pTipo, int pCantidad) {
        ListaPeliculasRecomendadas lp = null;
        switch (pTipo) {
            case Persona:
                if ((filtros[0] == null)) {
                    this.cargarModeloPersona();
                }
                lp = filtros[0].recomendar(pCantidad);
                break;
            case Pelicula:
                if ((filtros[1] == null)) {
                    this.cargarModeloProductos();
                }
                lp = filtros[1].recomendar(pCantidad);
                break;
            case Contenido:
                if ((filtros[2] == null)) {
                    this.cargarModeloContenido();
                }
                lp = filtros[2].recomendar(pCantidad);
                break;
        }
        return lp;
    }

}
