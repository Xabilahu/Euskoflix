package eus.ehu.euskoflix.packModelo;

public abstract class Normalizable {

    private double media;
    private double cuasiDesv;

    public Normalizable() {
        this.media = 0.0;
        this.cuasiDesv = 0.0;
    }

    public Normalizable(double media, double cuasiDesv) {
        this.media = media;
        this.cuasiDesv = cuasiDesv;
    }

    public double normalizar(double pValor) throws Exception {
        double normalizado = (pValor - this.media) / this.cuasiDesv;
        if (Double.isNaN(normalizado)) {
            throw new Exception();
        }
        return normalizado;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public double getCuasiDesv() {
        return cuasiDesv;
    }

    public void setCuasiDesv(double desviacionTipica) {
        this.cuasiDesv = desviacionTipica;
    }

    public double desnormalizar(double pValor) {
        return (pValor * this.cuasiDesv) + this.media;
    }
}
