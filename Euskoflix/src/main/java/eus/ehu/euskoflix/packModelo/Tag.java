package eus.ehu.euskoflix.packModelo;

import java.util.Objects;

public class Tag {

    private String nombre;
    private int cantidad;

    public Tag(String pNombre) {
        this.nombre = pNombre;
    }

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag)) return false;
        Tag t = (Tag) o;
        return this.nombre.equals(t.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
