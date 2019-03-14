package eus.ehu.euskoflix.packModelo;

public class Tag {

    private int usuario;
    private int pelicula;
    private String etiqueta;

    public Tag(int pUsuario, int pPelicula, String pEtiqueta) {
        this.usuario = pUsuario;
        this.pelicula = pPelicula;
        this.etiqueta = pEtiqueta;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getPelicula() {
        return pelicula;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
