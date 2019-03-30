package eus.ehu.euskoflix.packModelo;

import java.util.HashMap;
import java.util.HashSet;

public class ListaPeliculas {

    private HashMap<Integer, Pelicula> lista;

    public ListaPeliculas() {
        this.lista = new HashMap<>();
    }

    public void addPelicula(Pelicula pPeli) {
        int id = pPeli.getId();
        if (!this.lista.containsKey(id)) {
            this.lista.put(id, pPeli);
        }
    }

    public int getNumPeliculas() {
        return this.lista.size();
    }

    public Pelicula getPeliculaPorId(int pId) {
        if (this.lista.containsKey(pId)) {
            return this.lista.get(pId);
        }
        return null;
    }

    public void cargarMediaDesviacionesPeliculas() {
        for (Pelicula pelicula : this.lista.values()) {
            if (MatrizValoraciones.getInstance().tieneValoracionesPelicula(pelicula.getId())) {
                MatrizValoraciones.getInstance().cargarValoracionesNormalizadas(pelicula);
            }
        }
    }

    public void cargarModeloProducto(FiltradoProducto filtradoProducto) {
        HashSet<Integer> valoradas = new HashSet<>();
        valoradas.addAll(MatrizValoraciones.getInstance().getPeliculasValoradas(CatalogoUsuarios.getInstance().getUsuarioLogueado().getId()));
        valoradas.retainAll(this.lista.keySet());
        HashSet<Integer> noValoradas = new HashSet<>();
        noValoradas.addAll(this.lista.keySet());
        noValoradas.removeAll(valoradas);
        noValoradas.stream().forEach((i) -> {
            for (Integer j : valoradas) {
                filtradoProducto.addSimilitudSimetrica(MatrizValoraciones.getInstance().simPelicula(Cartelera.getInstance().getPeliculaPorIdSinMapeo(i), Cartelera.getInstance().getPeliculaPorIdSinMapeo(j)));
            }
        });
    }

    public HashSet<Integer> getPeliculasNoValoradas(Usuario pUsuario) {
        HashSet<Integer> valoradas = new HashSet<>();
        valoradas.addAll(MatrizValoraciones.getInstance().getPeliculasValoradas(pUsuario.getId()));
        valoradas.retainAll(this.lista.keySet());
        HashSet<Integer> noValoradas = new HashSet<>();
        noValoradas.addAll(this.lista.keySet());
        noValoradas.removeAll(valoradas);
        return noValoradas;
    }

}
