package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;

/**
 * Pelicula Tester.
 *
 * @version 1.0
 * @since Mar 20, 2019
 */
public class PeliculaTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(false);
    }

    @Test
    public void testTagsToStringArray() throws Exception {
        LinkedList<Tag> expected = Cartelera.getInstance().getPeliculaPorId(1).getLista();
        String[][] resultado = Cartelera.getInstance().getPeliculaPorId(1).tagsToStringArray();
        int i = 0;
        for(Tag t : expected) {
            assertEquals(t.getNombre(), resultado[i][0]);
            assertEquals(String.valueOf(t.getCantidad()), resultado[i][1]);
        }
    }

} 