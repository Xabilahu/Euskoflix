package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * MatrizValoraciones Tester.
 *
 * @version 1.0
 * @since Mar 20, 2019
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MatrizValoracionesTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
//        BaseDatos.getBaseDatos().reiniciarBD(false);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
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
        /*BaseDatos.getBaseDatos().eliminarBaseDatos();
        assertNull(MatrizValoraciones.getInstance().getColumnas());
        assertNull(MatrizValoraciones.getInstance().getFilas());
        assertNull(MatrizValoraciones.getInstance().getValoraciones());
        GestionDatos.getInstance().cargarDatos(false);//Incluye una llamada al metodo MatrizValoraciones.getInstance().cargarValoraciones()
        assertEquals(MatrizValoraciones.getInstance().getFilas().length, CatalogoUsuarios.getInstance().getNumUsuarios());
        assertEquals(MatrizValoraciones.getInstance().getColumnas().length, MatrizValoraciones.getInstance().getValoraciones().length);
        assertEquals(MatrizValoraciones.getInstance().getColumnas().length, BaseDatos.getBaseDatos().getNumValoraciones() + 1);*/
    }

    /**
     * Method: getValoracionesByPelicula(int pId)
     */
    @Test
    public void testGetValoracionesByPelicula() throws Exception{
        GestionDatos.getInstance().cargarDatos(false);
        Iterator<Map.Entry<Integer,Double>> iterator = MatrizValoraciones.getInstance().getValoracionesByPelicula(1).entrySet().iterator();
        try {
            ResultSet rst = BaseDatos.getBaseDatos().getValoracionesByPelicula(1);
            while(rst.next()) {
                Map.Entry<Integer,Double> valoracion = iterator.next();
                assertEquals((Integer)rst.getInt(1), valoracion.getKey());
                assertEquals((Float)rst.getFloat(2), valoracion.getValue());
            }
        } catch (SQLException e) {
            fail();
        }

    }
    @Test
    public void testSimPersonas() {
        GestionDatos.getInstance().cargarDatos(false);
        try {
            FileWriter fw = new FileWriter(new File(System.getProperty("user.dir") + File.separator + "valoracionesPelis.csv"));
            fw.write("id,similarity\n");
            for(int i = 2; i < Cartelera.getInstance().getNumPeliculas(); i++) {
                fw.write(i + "," +
                        MatrizValoraciones.getInstance().simPelicula(Cartelera.getInstance().getPeliculaPorId(1),Cartelera.getInstance().getPeliculaPorId(i)).getSim()
                        + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testSimPeliculas(){
        Cartelera.getInstance().print();
    }

} 
