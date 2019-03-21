package eus.ehu.euskoflix.packDatos;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;

import java.sql.ResultSet;

import static org.junit.Assert.*;

/** 
* GestionDatos Tester. 
*
* @since Mar 21, 2019
* @version 1.0 
*/ 
public class GestionDatosTest { 

    @BeforeClass
    public static void before() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(true);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
    }

    /**
    *
    * Method: getInstance()
    *
    */
    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(GestionDatos.getInstance());
    }

    /**
    *
    * Method: getValoraciones()
    *
    */
    @Test
    public void testGetValoraciones() throws Exception {
        float[] valoraciones = GestionDatos.getInstance().getValoraciones();
        assertEquals(valoraciones.length, BaseDatos.getBaseDatos().getNumValoraciones() + 1);
        ResultSet expected = BaseDatos.getBaseDatos().getValoraciones();
        int i = 1;
        while(expected.next()) {
            assertEquals(expected.getFloat(3), valoraciones[i++], 0.001);
        }
    }

    /**
    *
    * Method: getValoracionesUsuarios()
    *
    */
    @Test
    public void testGetValoracionesUsuarios() throws Exception {
        int[] usuarios = GestionDatos.getInstance().getValoracionesUsuarios();
        assertEquals(usuarios.length, BaseDatos.getBaseDatos().getNumUsuariosQueValoran() + 1);
        ResultSet expected = BaseDatos.getBaseDatos().getNumValoracionesUsuario();
        expected.next();
        for(int i = 1; i < usuarios.length; i++) {
            if (i==1) assertEquals(usuarios[i], 1);
            else {
                assertEquals(usuarios[i-1] + expected.getInt("suma"), usuarios[i]);
                expected.next();
            }
        }
    }

    /**
    *
    * Method: getValoracionesPeliculas()
    *
    */
    @Test
    public void testGetValoracionesPeliculas() throws Exception {
        int[] peliculas = GestionDatos.getInstance().getValoracionesPeliculas();
        assertEquals(peliculas.length, BaseDatos.getBaseDatos().getNumPeliculasValoradas() + 1);
        ResultSet expected = BaseDatos.getBaseDatos().getValoraciones();
        int i = 1;
        while(expected.next()) {
            assertEquals(expected.getInt("id_pelicula"), peliculas[i++]);
        }
    }

} 
