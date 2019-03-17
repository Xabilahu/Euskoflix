package eus.ehu.euskoflix.packPrincipal;

import eus.ehu.euskoflix.packControlador.GestionDatos;
import eus.ehu.euskoflix.packVista.EuskoFlixLoader;
import eus.ehu.euskoflix.packVista.VentanaCargaDatos;

public class Main {

    public static void main(String[] args) {
        EuskoFlixLoader el = new EuskoFlixLoader();
        el.setVisible(true);
        GestionDatos.getInstance().cargarDatos();
        el.dispose();
        VentanaCargaDatos ventanaCargaDatos = new VentanaCargaDatos();
        ventanaCargaDatos.setVisible(true);
    }

}
