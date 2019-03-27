package eus.ehu.euskoflix.packModelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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

    public void print() {
        //TODO: hacer
        FileWriter fw =null;
        try {
            fw= new FileWriter("pelis1_per.csv");
            for (Pelicula pelicula : this.lista.values()) {
                if (pelicula != null && pelicula.getId() != 1){
                    if (pelicula.getId()==65538){
                        int i =0;
                    }
                    fw.write(pelicula.getId() + "," +String.valueOf(MatrizValoraciones.getInstance().simPelicula(Cartelera.getInstance().getPeliculaPorIdSinMapeo(1), pelicula).getPorcentaje())+"\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
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
            if (MatrizValoraciones.getInstance().tieneValoracionesPelicula(pelicula.getId())){
                MatrizValoraciones.getInstance().cargarValoracionesNormalizadas(pelicula);
               /* System.out.println("Pelicula :" + pelicula.getId());
                System.out.println("\tmedia: " + pelicula.getMedia() + "\tcuasidesv: " + pelicula.getCuasiDesv());*/
            }
        }
    }
}
