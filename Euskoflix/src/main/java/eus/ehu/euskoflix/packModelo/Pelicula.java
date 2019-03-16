package eus.ehu.euskoflix.packModelo;

import java.awt.Image;
import java.util.LinkedList;

public class Pelicula {

    private int id;
    private String titulo;
    private LinkedList<Tag> lista;
    private int tmdbId;

    public Pelicula(int pId, String pTitulo, int pTmdbId) {
        this.id = pId;
        this.titulo = pTitulo;
        this.lista = new LinkedList<>();
        this.tmdbId = pTmdbId;
    }

    public void addTag(Tag pTag) {
        this.lista.add(pTag);
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
}
