package eus.ehu.euskoflix.packModelo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ListaPeliculas Tester.
 *
 * @version 1.0
 * @since Mar 20, 2019
 */
public class ListaPeliculasTest {

    /**
     * Method: addPelicula(Pelicula pPeli)
     */
    @Test
    public void testAddPelicula() throws Exception {
        Pelicula p = new Pelicula(2, "Prueba", 6);
        ListaPeliculas lp = new ListaPeliculas();
        lp.addPelicula(p);
        assertSame(p, lp.getPeliculaPorId(2));
        assertNull(lp.getPeliculaPorId(1));
    }

    /**
     * Method: getNumPeliculas()
     */
    @Test
    public void testGetNumPeliculas() throws Exception {
        ListaPeliculas lp = new ListaPeliculas();
        assertEquals(0, lp.getNumPeliculas());
        lp.addPelicula(new Pelicula(1, "Prueba1", 3));
        lp.addPelicula(new Pelicula(3, "Prueba2", 6));
        assertEquals(2, lp.getNumPeliculas());
    }

    /**
     * Method: getPeliculaPorId(int pId)
     */
    @Test
    public void testGetPeliculaPorId() throws Exception {
        ListaPeliculas lp = new ListaPeliculas();
        Pelicula p1 = new Pelicula(1, "Prueba 1", 56);
        Pelicula p2 = new Pelicula(3, "Prueba 2", 5);
        lp.addPelicula(p1);
        lp.addPelicula(p2);
        assertNull(lp.getPeliculaPorId(10));
        assertSame(p1, lp.getPeliculaPorId(1));
        assertSame(p2, lp.getPeliculaPorId(3));
    }


} 
