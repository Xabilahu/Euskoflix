package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Pelicula extends Normalizable {

    private int id;
    private String titulo;
    private ListaTags lista;
    private int tmdbId;
    private Informacion infoExtra;


    public Pelicula(int pId, String pTitulo, int pTmdbId) {
        this.id = pId;
        this.titulo = pTitulo;
        this.tmdbId = pTmdbId;
        this.lista = new ListaTags();
    }

    public void addTag(Tag pTag) {
        this.lista.addTag(pTag);
    }

    public int getNumTags() {
        return this.lista.numTags();
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
        return this.lista.tagsToStringArray();
    }

    /**
     * This method is only used in jUnit
     */
    public ListaTags getLista() {
        return lista;
    }

}
