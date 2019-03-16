package eus.ehu.euskoflix.packModelo;

import java.awt.Image;
import java.util.LinkedList;

public class Pelicula {

    private int id;
    private String titulo;
    private LinkedList<Tag> lista;
    private Informacion infoExtra;

    public Pelicula(int pId, String pTitulo, Informacion pInfo) {
        this.id = pId;
        this.titulo = pTitulo;
        this.lista = new LinkedList<>();
        this.infoExtra = pInfo;
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
    public String getSinopsis() {
    	return this.infoExtra.getSinopsis();
    }
    public Image getPoster() {
    	return this.infoExtra.getPoster();
    }

}
