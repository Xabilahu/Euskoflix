package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import eus.ehu.euskoflix.packModelo.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * ControladorVista Tester.
 *
 * @version 1.0
 * @since Mar 20, 2019
 */
public class ControladorVistaTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(TipoFichero.big);
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(ControladorVista.getInstance());
    }

    /**
     * Method: datosUsuario()
     */
    @Test
    public void testDatosUsuario() throws Exception {
        String[][] resultado = ControladorVista.getInstance().datosUsuario();
        for (int i = 1; i < CatalogoUsuarios.getInstance().getNumUsuarios(); i++) {
            Usuario u = CatalogoUsuarios.getInstance().getUsuarioPorId(i);
            assertEquals(u.getId(), Integer.parseInt(resultado[i-1][0]));
            assertEquals(u.getNombre(), resultado[i-1][1]);
            assertEquals(u.getApellido(), resultado[i-1][2]);
            assertEquals(u.getPassword(), resultado[i-1][3]);
        }

    }

    /**
     * Method: datosRatings(int id)
     */
    @Test
    public void testDatosRatings() throws Exception {
        String[][] resultado = ControladorVista.getInstance().datosRatings(1);
        int i = 0;
        for(Map.Entry<Integer,Double> entry : MatrizValoraciones.getInstance().getValoracionesByPelicula(1).entrySet()) {
            assertEquals(resultado[i][0],CatalogoUsuarios.getInstance().getUsuarioPorId(entry.getKey()).getNombre());
            assertEquals(resultado[i++][1],entry.getValue().toString());
        }
    }

    /**
     * Method: datosPelis()
     */
    @Test
    public void testDatosPelis() throws Exception {
        String[][] resultado = ControladorVista.getInstance().datosPelis();
        for (int i = 0; i < Cartelera.getInstance().getNumPeliculas(); i++) {
            Pelicula u = Cartelera.getInstance().getPeliculaPorId(i);
            assertEquals(u.getId(), Integer.parseInt(resultado[i][0]));
            assertEquals(u.getTitulo(), resultado[i][1]);
        }
    }


} 
