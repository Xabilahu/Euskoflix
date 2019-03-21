package eus.ehu.euskoflix.packDatos;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.sql.SQLException;

import static org.junit.Assert.*;

/** 
* BaseDatos Tester. 
* 
* @author <Authors name> 
* @since <pre>Mar 21, 2019</pre> 
* @version 1.0 
*/ 
public class BaseDatosTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getBaseDatos()
    *
    */
    @Test
    public void testGetBaseDatos() throws Exception {
        assertNotNull(BaseDatos.getBaseDatos());
    }

    /**
    *
    * Method: iniciarBD()
    *
    */
    @Test
    public void testIniciarBD() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        try {
            BaseDatos.getBaseDatos().getValoracionesByPelicula(118);
            fail();
        } catch (SQLException e) {
            //Test passed
        }
        BaseDatos.getBaseDatos().iniciarBD(true);
        try {
            BaseDatos.getBaseDatos().getValoracionesByPelicula(118);
        } catch (SQLException e) {
            fail();
        }
    }

    /**
    *
    * Method: reiniciarBD()
    *
    */
    @Test
    public void testReiniciarBD() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getPeliculas()
    *
    */
    @Test
    public void testGetPeliculas() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getUsuarios()
    *
    */
    @Test
    public void testGetUsuarios() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getValoraciones()
    *
    */
    @Test
    public void testGetValoraciones() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getNumPeliculasValoradas()
    *
    */
    @Test
    public void testGetNumPeliculasValoradas() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getNumUsuariosQueValoran()
    *
    */
    @Test
    public void testGetNumUsuariosQueValoran() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getNumValoraciones()
    *
    */
    @Test
    public void testGetNumValoraciones() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getValoracionesUsuarios()
    *
    */
    @Test
    public void testGetValoracionesUsuarios() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getNumValoracionesUsuario()
    *
    */
    @Test
    public void testGetNumValoracionesUsuario() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getTagsByPelicula(int pId)
    *
    */
    @Test
    public void testGetTagsByPelicula() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getValoracionesByPelicula(int pId)
    *
    */
    @Test
    public void testGetValoracionesByPelicula() throws Exception {
    //TODO: Test goes here...
    }


    /**
    *
    * Method: getBDFile()
    *
    */
    @Test
    public void testGetBDFile() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("getBDFile");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: comprobarExisteBD()
    *
    */
    @Test
    public void testComprobarExisteBD() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("comprobarExisteBD");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: crearBD()
    *
    */
    @Test
    public void testCrearBD() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("crearBD");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: getConexion()
    *
    */
    @Test
    public void testGetConexion() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("getConexion");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: anadirDatos()
    *
    */
    @Test
    public void testAnadirDatos() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("anadirDatos");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: anadirPeliculas()
    *
    */
    @Test
    public void testAnadirPeliculas() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("anadirPeliculas");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: anadirEtiquetas()
    *
    */
    @Test
    public void testAnadirEtiquetas() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("anadirEtiquetas");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: anadirValoraciones()
    *
    */
    @Test
    public void testAnadirValoraciones() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("anadirValoraciones");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: anadirUsuarios()
    *
    */
    @Test
    public void testAnadirUsuarios() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("anadirUsuarios");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: addIds()
    *
    */
    @Test
    public void testAddIds() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("addIds");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: addPeliculaYGenero()
    *
    */
    @Test
    public void testAddPeliculaYGenero() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("addPeliculaYGenero");
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

    /**
    *
    * Method: pedirTabla(String query)
    *
    */
    @Test
    public void testPedirTabla() throws Exception {
    //TODO: Test goes here...
    /*
    try {
       Method method = BaseDatos.getClass().getMethod("pedirTabla", String.class);
       method.setAccessible(true);
       method.invoke(<Object>, <Parameters>);
    } catch(NoSuchMethodException e) {
    } catch(IllegalAccessException e) {
    } catch(InvocationTargetException e) {
    }
    */
    }

} 
