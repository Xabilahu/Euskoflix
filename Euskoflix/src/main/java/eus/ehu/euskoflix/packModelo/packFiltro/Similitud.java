package eus.ehu.euskoflix.packModelo.packFiltro;

public class Similitud {

    private int i;
    private int j;
    private double sim;

    public Similitud(int i, int j, double sim) {
        this.i = i;
        this.j = j;
        this.sim = sim;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getSim() {
        return sim;
    }

    public double getPorcentaje() {
        return 1 - 2 * Math.acos(Math.abs(sim)) / Math.PI;
    }
}
