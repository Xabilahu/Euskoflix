package eus.ehu.euskoflix.packModelo;

public class Tag {

    private String nombre;
    private int cantidad;

    public Tag(String nombre, int cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }
}
