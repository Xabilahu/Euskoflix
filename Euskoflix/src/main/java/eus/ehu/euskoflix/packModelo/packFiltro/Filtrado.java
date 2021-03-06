package eus.ehu.euskoflix.packModelo.packFiltro;

public class Filtrado {

    private static Filtrado ourInstance = new Filtrado();

    private FiltradoProducto filtradoProducto;
    private FiltradoPersona filtradoPersona;
    private FiltradoContenido filtradoContenido;


    private Filtrado() {
    }

    public static Filtrado getInstance() {
        return ourInstance;
    }

    public void cargarModelos() {
        this.cargarModeloPersona();
        this.cargarModeloProductos();
        this.calcularRecomendacionesContenido();
    }

    private void cargarModeloPersona() {
        this.filtradoPersona = new FiltradoPersona();
        filtradoPersona.calcularRecomendaciones();
    }

    private void cargarModeloProductos() {
        this.filtradoProducto = new FiltradoProducto();
        filtradoProducto.calcularRecomendaciones();
    }

    private void cargarModeloContenido() {
        this.filtradoContenido = new FiltradoContenido();
    }

    public ListaPeliculasValoraciones recomendar(TipoRecomendacion pTipo, int pCantidad) {
        if (!isModeloCargado(pTipo)) {
            cargarModelo(pTipo);
        }
        ListaPeliculasValoraciones lp;
        switch (pTipo) {
            case Persona:
                lp = this.filtradoPersona.recomendar(pCantidad);
                break;
            case Pelicula:
                lp = this.filtradoProducto.recomendar(pCantidad);
                break;
            case Contenido:
                lp = this.filtradoContenido.recomendar(pCantidad);
                break;
            default:
                lp = ListaPeliculasValoraciones.generarHíbrido(this.filtradoPersona.recomendar(pCantidad), this.filtradoProducto.recomendar(100), this.filtradoContenido.recomendar(100));
                break;
        }
        return lp;
    }

    private void cargarModelo(TipoRecomendacion pTipo) {
        switch (pTipo) {
            case Persona:
                cargarModeloPersona();
                break;
            case Pelicula:
                cargarModeloProductos();
                break;
            case Contenido:
                cargarModeloContenido();
                break;
        }
    }

    private boolean isModeloCargado(TipoRecomendacion pTipo) {
        boolean resultado = false;
        switch (pTipo) {
            case Persona:
                resultado = this.filtradoPersona != null;
                break;
            case Pelicula:
                resultado = this.filtradoProducto != null;
                break;
            case Contenido:
                resultado = this.filtradoContenido != null;
                break;
        }
        return resultado;
    }

    public void cargarTf(ListaEtiquetasFiltrado pTf) {
        this.cargarModeloContenido();
        this.filtradoContenido.cargarTF(pTf);
    }

    private void calcularRecomendacionesContenido() {
        this.filtradoContenido.calcularRecomendaciones();
    }

    public void vaciarFiltros() {
        this.filtradoContenido.vaciarEstructuras();
        this.filtradoProducto = new FiltradoProducto();
        this.filtradoPersona = new FiltradoPersona();
    }

    /**
     * This method is only used in jUnit
     */
    public FiltradoProducto getFiltradoProducto() {
        return filtradoProducto;
    }

    /**
     * This method is only used in jUnit
     */
    public FiltradoPersona getFiltradoPersona() {
        return filtradoPersona;
    }

    /**
     * This method is only used in jUnit
     */
    public FiltradoContenido getFiltradoContenido() {
        return filtradoContenido;
    }
}
