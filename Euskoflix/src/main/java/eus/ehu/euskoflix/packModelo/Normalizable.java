package eus.ehu.euskoflix.packModelo;

public abstract class Normalizable {

    private double media;
    private double desviacionTipica;

    public Normalizable() {
        this.media = 0.0;
        this.desviacionTipica = 0.0;
    }

    public Normalizable(double media, double desviacionTipica) {
        this.media = media;
        this.desviacionTipica = desviacionTipica;
    }

    public double normalizar(double pValor){
        return (pValor-this.media)/this.desviacionTipica;
    }

    public double getMedia() {
        return media;
    }

    public double getDesviacionTipica() {
        return desviacionTipica;
    }

    public double desnormalizar(double pValor) {
        return (pValor+this.media)*this.desviacionTipica;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public void setDesviacionTipica(double desviacionTipica) {
        this.desviacionTipica = desviacionTipica;
    }
}
