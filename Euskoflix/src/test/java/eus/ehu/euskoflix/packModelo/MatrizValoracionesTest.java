package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * MatrizValoraciones Tester.
 *
 * @version 1.0
 * @since Mar 20, 2019
 */
public class MatrizValoracionesTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(MatrizValoraciones.getInstance());
    }

    /**
     * Method: cargarValoraciones()
     */
    @Test
    public void testCargarValoraciones() throws Exception {
        assertNull(MatrizValoraciones.getInstance().getColumnas());
        assertNull(MatrizValoraciones.getInstance().getFilas());
        assertNull(MatrizValoraciones.getInstance().getValoraciones());
        GestionDatos.getInstance().cargarDatos();//Incluye una llamada al metodo MatrizValoraciones.getInstance().cargarValoraciones()
        assertEquals(MatrizValoraciones.getInstance().getFilas().length, CatalogoUsuarios.getInstance().getNumUsuarios());
        assertEquals(MatrizValoraciones.getInstance().getColumnas().length, MatrizValoraciones.getInstance().getValoraciones().length);
        assertEquals(MatrizValoraciones.getInstance().getColumnas().length, BaseDatos.getBaseDatos().getNumValoraciones() + 1);
    }

    /**
     * Method: getValoracionesByPelicula(int pId)
     */
    @Test
    public void testGetValoracionesByPelicula() throws Exception{
        GestionDatos.getInstance().cargarDatos();
        Iterator<Map.Entry<Integer,Float>> iterator = MatrizValoraciones.getInstance().getValoracionesByPelicula(1).entrySet().iterator();
        ResultSet rst = BaseDatos.getBaseDatos().getValoracionesByPelicula(1);
        while(rst.next()) {
            Map.Entry<Integer,Float> valoracion = iterator.next();
            assertEquals((Integer)rst.getInt(1), valoracion.getKey());
            assertEquals((Float)rst.getFloat(2), valoracion.getValue());
        }

    }

} 
