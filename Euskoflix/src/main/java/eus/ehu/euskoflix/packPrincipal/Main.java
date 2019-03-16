package eus.ehu.euskoflix.packPrincipal;

import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packModelo.MatrizValoraciones;

public class Main {

    public static void main(String[] args) {
//        GestionDatos.getInstance().cargarDatos();
        MatrizValoraciones.getInstance().cargarValoraciones();
        System.out.println(MatrizValoraciones.getInstance().getValoracion(208, 1));
    }

}
