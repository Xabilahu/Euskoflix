package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class Pelicula extends Normalizable {

    private int id;
    private String titulo;
    private LinkedList<Tag> lista;
    private int tmdbId;
    private Informacion infoExtra;


    public Pelicula(int pId, String pTitulo, int pTmdbId) {
        this.id = pId;
        this.titulo = pTitulo;
        this.lista = new LinkedList<>();
        this.tmdbId = pTmdbId;
        this.lista = new LinkedList<>();
    }

    public void addTag(Tag pTag) {
        this.lista.add(pTag);
    }

    public int getNumTags() {
        return this.lista.size();
    }

    public void print() {
        System.out.println("ID: " + id + " Título: " + titulo);
    }

    public int getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    private void fillInfoExtra() {
        if (infoExtra == null) {
            this.infoExtra = GestionDatos.getInstance().getInfoExtra(this);
        }
    }

    public String getDirector() {
        this.fillInfoExtra();
        return this.infoExtra.getDirector();
    }

    public String getSinopsis() {
        this.fillInfoExtra();
        return this.infoExtra.getSinopsis();
    }

    public Image getPoster() {
        this.fillInfoExtra();
        return this.infoExtra.getPoster();
    }

    public String[][] tagsToStringArray() {
        String[][] resultado = new String[this.getNumTags()][2];
        Iterator<Tag> itr = this.lista.iterator();
        int i = 0;
        while (itr.hasNext()) {
            Tag t = itr.next();
            resultado[i][0] = t.getNombre();
            resultado[i][1] = String.valueOf(t.getCantidad());
            i++;
        }
        return resultado;
    }

    /**
     * This method is only used in jUnit
     */
    public LinkedList<Tag> getLista() {
        return lista;
    }
}
