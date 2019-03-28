package eus.ehu.euskoflix.packModelo;

public class FiltradoProducto extends Filtrable {

    public FiltradoProducto() {
        //TODO: encapsularlo en Cartelera y cambiar su ListaPeliculas a un HashMap
        Cartelera.getInstance().cargarModeloProductos(this);
       /* Cartelera c = Cartelera.getInstance();
        MatrizValoraciones m = MatrizValoraciones.getInstance();
        for (int i = 1; i < c.getNumPeliculas(); i++) {
            Pelicula p = c.getPeliculaPorId(i);
            for (int j = i + 1; j < c.getNumPeliculas(); j++) {
                super.addSimilitudSimetrica(m.simPelicula(p, c.getPeliculaPorId(j)));
            }
            System.out.println("Done : pelicula" + p.getId());
        }*/
    }



    @Override
    public ListaPeliculas recomendar(int pId, int pNum) {
        return null;
    }
}
