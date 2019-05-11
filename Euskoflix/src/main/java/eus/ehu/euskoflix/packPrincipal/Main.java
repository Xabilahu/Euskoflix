package eus.ehu.euskoflix.packPrincipal;

import eus.ehu.euskoflix.packControlador.ControladorVista;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.MatrizValoraciones;
import eus.ehu.euskoflix.packModelo.Usuario;
import eus.ehu.euskoflix.packVista.VentanaUsuario;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    //Full data charging and recommendations last around 16 sec
    public static void main(String[] args) throws IOException, InterruptedException {
          ControladorVista.getInstance().iniciarAplicacion();
//        ControladorVista.getInstance().mostrarCargaDatos();
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
