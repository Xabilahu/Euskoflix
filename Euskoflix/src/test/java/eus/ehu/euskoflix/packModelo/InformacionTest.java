package eus.ehu.euskoflix.packModelo;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

/** 
 * Informacion Tester.
 *
 * @since Mar 20, 2019
 * @version 1.0
 */
public class InformacionTest {

    private static Informacion toyStory;

    @BeforeClass
    public static void setUp() throws Exception {
        toyStory = new Informacion();
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getSinopsis()
    *
    */
    @Test
    public void testGetSinopsis() throws Exception {
        assertEquals("Los juguetes de Andy, un niño de 6 años, temen que haya llegado su hora y que un nuevo regalo de cumpleaños les sustituya en el corazón de su dueño. Woody, un vaquero que ha sido hasta ahora el juguete favorito de Andy, trata de tranquilizarlos hasta que aparece Buzz Lightyear, un héroe espacial dotado de todo tipo de avances tecnológicos. Woody es relegado a un segundo plano. Su constante rivalidad se transformará en una gran amistad cuando ambos se pierden en la ciudad sin saber cómo volver a casa.", "");
    }

    /**
    *
    * Method: getPoster()
    *
    */
    @Test
    public void testGetPoster() throws Exception {

    }

    /**
    *
    * Method: getDirector()
    *
    */
    @Test
    public void testGetDirector() throws Exception {
    //TODO: Test goes here...
    }

    /**
     *
     * Method: setDirector(String director)
     *
     */
    @Test
    public void testSetDirector() throws Exception {
    //TODO: Test goes here...
    }


} 
