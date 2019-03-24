package eus.ehu.euskoflix.packModelo;

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


    public double getPorcentaje(){
        return (this.sim+1)*(50); // y = (this.sim - (-1))*100/2
    }
}
