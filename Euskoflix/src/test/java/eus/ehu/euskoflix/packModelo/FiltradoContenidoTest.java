package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FiltradoContenidoTest {

    @Before
    public void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(TipoFichero.small);
        CatalogoUsuarios.getInstance().login(new Usuario(2048,"","","euskoflix"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void cargarTF() {
    }

    @Test
    public void getNMasSimilares() {
    }

    @Test
    public void recomendar() {
    }

    @Test
    public void cargar() {
        System.out.println("Done");
    }
}