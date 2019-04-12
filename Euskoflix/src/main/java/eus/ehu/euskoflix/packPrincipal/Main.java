package eus.ehu.euskoflix.packPrincipal;

import eus.ehu.euskoflix.packControlador.ControladorVista;

public class Main {

    //Full data charging and recommendations last around 16 sec
    public static void main(String[] args) {
        ControladorVista.getInstance().iniciarAplicacion();
        ControladorVista.getInstance().mostrarCargaDatos();
//        GestionDatos.getInstance().cargarDatos(TipoFichero.small);
//        CatalogoUsuarios.getInstance().login(new Usuario(1, "", "", "euskoflix"));
//        System.out.println("-------Filtro Persona-------");
//        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Persona, 100).toString());
//        System.out.println("-------Filtro Pelicula-------");
//        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Pelicula, 100).toString());
//        System.out.println("-------Filtro Contenido-------");
//        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Contenido, 100).toString());
    }

}
