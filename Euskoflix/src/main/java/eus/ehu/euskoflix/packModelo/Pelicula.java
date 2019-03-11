package eus.ehu.euskoflix.packModelo;

public class Pelicula {

    private int id;
    private String titulo;
    private ListaTags lista;
    private Informacion infoExtra;

    public Pelicula(int pId, String pTitulo, Informacion pInfo) {
        this.id = pId;
        this.titulo = pTitulo;
        this.lista = new ListaTags();
        this.infoExtra = pInfo;
    }

    public void addTag(String pTag) {
        this.lista.addTag(pTag);
    }

    public void print() {
        System.out.println("ID: " + id + " Título: " + titulo);
    }

}
