package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FiltradoContenidoTest {

    @Before
    public void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(TipoFichero.small);
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
        Filtrado.getInstance().cargarIDF();
    }
}