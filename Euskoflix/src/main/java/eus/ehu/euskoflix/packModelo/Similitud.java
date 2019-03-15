package eus.ehu.euskoflix.packModelo;

public class Similitud {

    private int i;
    private int j;
    private float sim;

    public Similitud(int i, int j, float sim) {
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

    public float getSim() {
        return sim;
    }
}
