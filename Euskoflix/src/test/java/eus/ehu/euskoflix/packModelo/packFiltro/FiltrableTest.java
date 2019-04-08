package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.Usuario;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.Assert.*;

/** 
* Filtrable Tester. 
*
* @since Apr 6, 2019
* @version 1.0 
*/ 
public class FiltrableTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getNMasSimilares(int pId)
    *
    */
    @Test
    public void testGetNMasSimilares() throws Exception {
        Filtrable f = new FiltradoPersona();
        int limite = Filtrable.getN() + 1;
        for (int i = 0; i < limite; i++) {
            f.addSimilitud(1, new Similitud(1, i, ((double)i)/limite));
        }
        assertEquals(f.getMatrizSimilitudes().get(1).size(), limite);
        Similitud[] sim = f.getNMasSimilares(1);
        assertEquals(sim.length, Filtrable.getN());
        for (int i = 0; i < sim.length - 1; i++) {
            assertTrue(Double.compare(sim[i].getSim(), sim[i + 1].getSim()) > 0);
        }
    }

    /**
    *
    * Method: addSimilitudSimetrica(Similitud pSimilitud)
    *
    */
    @Test
    public void testAddSimilitudSimetrica() throws Exception {
        Filtrable f = new FiltradoPersona();
        assertNull(f.getMatrizSimilitudes().get(1));
        assertNull(f.getMatrizSimilitudes().get(2));
        f.addSimilitudSimetrica(new Similitud(1,2,4.5));
        assertTrue(f.getMatrizSimilitudes().containsKey(1));
        assertTrue(f.getMatrizSimilitudes().containsKey(2));
        assertSame(f.getMatrizSimilitudes().get(1).first(), f.getMatrizSimilitudes().get(2).first());
    }

    /**
    *
    * Method: addSimilitud(int pID, Similitud pSimilitud)
    *
    */
    @Test
    public void testAddSimilitud() throws Exception {
        Filtrable f = new FiltradoPersona();
        assertNull(f.getMatrizSimilitudes().get(1));
        f.addSimilitud(1,new Similitud(1,2,4.5));
        assertTrue(f.getMatrizSimilitudes().containsKey(1));
        assertEquals(f.getMatrizSimilitudes().get(1).size(), 1);
        f.addSimilitud(1, new Similitud(1,2,3.5));
        assertEquals(f.getMatrizSimilitudes().get(1).size(), 2);
        assertTrue(Double.compare(f.getMatrizSimilitudes().get(1).first().getSim(), f.getMatrizSimilitudes().get(1).last().getSim()) > 0);
    }

    /**
    *
    * Method: addRecomendacion(int pPelicula, double pValoracion)
    *
    */
    @Test
    public void testAddRecomendacion() throws Exception {
        Filtrable f = new FiltradoPersona();
        TreeSet<Map.Entry<Integer,Double>> lpr = f.getRecomendados().getRecomendaciones();
        assertEquals(lpr.size(), 0);
        f.addRecomendacion(1, 3.4);
        assertEquals(lpr.size(), 1);
        f.addRecomendacion(2, 3.7);
        assertEquals(lpr.size(), 2);
        assertTrue(Double.compare(lpr.first().getValue(), lpr.last().getValue()) > 0);
        //TODO comprobar añadir misma peli
    }

    /**
    *
    * Method: getNRecomendaciones(int pNum)
    *
    */
    @Test
    public void testGetNRecomendaciones() throws Exception {
        Filtrable f = new FiltradoPersona();
        TreeSet<Map.Entry<Integer,Double>> lpr = f.getRecomendados().getRecomendaciones();
        for (int i = 0; i < 16; i++) {
            f.addRecomendacion(i, i / 16.0);
        }
        TreeSet<Map.Entry<Integer,Double>> rec = f.getRecomendados().getNRecomendaciones(10).getRecomendaciones();
        Iterator<Map.Entry<Integer,Double>> itr = lpr.iterator();
        rec.forEach(entry -> assertEquals(entry.getValue(), itr.next().getValue(), 0.0));
    }

    /**
    *
    * Method: generarValoracionRecomendada(boolean filtradoPersona, Integer noValorada, Similitud[] similitudes)
    *
    */
    @Test
    public void testGenerarValoracionRecomendada() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(TipoFichero.small);
        CatalogoUsuarios.getInstance().login(new Usuario(1, "", "", "euskoflix"));
        System.out.println("-------Filtro Persona-------");
        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Persona, 100).toString());
        System.out.println("-------Filtro Pelicula-------");
        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Pelicula, 100).toString());
        System.out.println("-------Filtro Contenido-------");
        System.out.println(Filtrado.getInstance().recomendar(TipoRecomendacion.Contenido, 100).toString());
    }

} 
