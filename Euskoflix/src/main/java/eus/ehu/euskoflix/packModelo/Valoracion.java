package eus.ehu.euskoflix.packModelo;

public class Valoracion {

    private int usuario;
    private int pelicula;
    private float valoracion;

    public Valoracion(int usuario, int pelicula, float valoracion) {
        this.usuario = usuario;
        this.pelicula = pelicula;
        this.valoracion = valoracion;
    }

    public int getUsuario() {
        return usuario;
    }

    public int getPelicula() {
        return pelicula;
    }

    public float getValoracion() {
        return valoracion;
    }

}
