package eus.ehu.euskoflix.packModelo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/** 
 * Cartelera Tester.
 *
 * @since Mar 20, 2019
 * @version 1.0
 */
public class CarteleraTest {

    @BeforeClass
    public static void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        //Cartelera.getInstance().reset();
    }

    /**
     *
     * Method: getInstance()
     *
     */
    @Test
    public void testGetInstance() throws Exception {
        assertNotNull(Cartelera.getInstance());
    }

    /**
     *
     * Method: addPelicula(Pelicula pPeli)
     *
     */
    @Test
    public void testAddPelicula() throws Exception {
        assertEquals(Cartelera.getInstance().getNumPeliculas(), 0);
        Pelicula p = new Pelicula(1, "Prueba", 2);
        Cartelera.getInstance().addPelicula(p);
        assertEquals(Cartelera.getInstance().getNumPeliculas(), 1);
    }

    /**
     * Method: getPeliculaPorId(int pId)
     *
     * Este método devuelve la peli con id interno pId
     * Las pelis, independientemente del id que tenga asociado, se mapean internamente por orden ascendente de llegada, empezando por 0
     *
     */
    @Test
    public void testGetPeliculaPorId() throws Exception {
        Pelicula p1 = new Pelicula(4, "Prueba1", 2);
        Pelicula p2 = new Pelicula(2, "Prueba2", 6);
        Cartelera.getInstance().addPelicula(p1);
        Cartelera.getInstance().addPelicula(p2);
        assertSame(Cartelera.getInstance().getPeliculaPorId(0), p1);
        assertSame(Cartelera.getInstance().getPeliculaPorId(1), p2);
    }

    /**
     *
     * Method: getPeliculaPorIdSinMapeo(int pId)
     *
     * Este método devuelve la peli el id pId asociado, independientemente del id interno que se ha utilizado para mapearlo
     *
     */
    @Test
    public void testGetPeliculaPorIdSinMapeo() throws Exception {
        Pelicula p1 = new Pelicula(4, "Prueba1", 2);
        Pelicula p2 = new Pelicula(2, "Prueba2", 6);
        Cartelera.getInstance().addPelicula(p1);
        Cartelera.getInstance().addPelicula(p2);
        assertSame(Cartelera.getInstance().getPeliculaPorIdSinMapeo(4), p1);
        assertSame(Cartelera.getInstance().getPeliculaPorIdSinMapeo(2), p2);
    }

} 
