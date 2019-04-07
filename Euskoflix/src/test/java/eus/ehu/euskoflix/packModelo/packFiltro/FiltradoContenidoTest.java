package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packDatos.BaseDatos;
import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.Usuario;
import eus.ehu.euskoflix.packModelo.packFiltro.Filtrado;
import eus.ehu.euskoflix.packModelo.packFiltro.FiltradoContenido;
import eus.ehu.euskoflix.packModelo.packFiltro.ListaEtiquetasFiltrado;
import eus.ehu.euskoflix.packModelo.packFiltro.TipoRecomendacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FiltradoContenidoTest {

    @Before
    public void setUp() throws Exception {
        BaseDatos.getBaseDatos().eliminarBaseDatos();
        GestionDatos.getInstance().cargarDatos(TipoFichero.small);
        CatalogoUsuarios.getInstance().login(new Usuario(4045,"","","euskoflix"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void cargarTF() {
        assertNull(Filtrado.getInstance().getFiltradoContenido().getTfidf());
        Filtrado.getInstance().getFiltradoContenido().cargarTF(new ListaEtiquetasFiltrado());
        assertNotNull(Filtrado.getInstance().getFiltradoContenido().getTfidf());
    }

    @Test
    public void cargar() throws Exception{

    }
}