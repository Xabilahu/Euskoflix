package eus.ehu.euskoflix.packPrincipal;

import eus.ehu.euskoflix.packControlador.ControladorVista;

public class Main {

    public static void main(String[] args) {
        ControladorVista controlador = ControladorVista.getInstance();
        controlador.iniciarAplicacion();
    }

}
