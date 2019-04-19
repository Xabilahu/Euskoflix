package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packVista.InformacionExtraView;

import java.awt.*;

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

    public void fillInfoExtra(Informacion pInfo) {
        this.infoExtra = pInfo;
    }

    public String getDirector() {
        return this.infoExtra.getDirector();
    }

    public String getSinopsis() {
        return this.infoExtra.getSinopsis();
    }

    public Image getPoster() {
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

    public String getTrailerUrl() {
        return this.infoExtra.getTrailerUrl();
    }
}
