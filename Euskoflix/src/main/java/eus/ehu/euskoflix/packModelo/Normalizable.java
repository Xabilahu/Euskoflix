package eus.ehu.euskoflix.packModelo;

public abstract class Normalizable {

    private float media;
    private float desviacionTipica;

    public Normalizable(){
        this.media = 0;
        this.desviacionTipica = 0;
    }

    private Normalizable(float pMedia,float pDesviacionTipica){
        this.media = pMedia;
        this.desviacionTipica = pDesviacionTipica;
    }


    public float normalizar(float pValor){
        return (pValor-this.media)/this.desviacionTipica;
    }


    protected float desnormalizar(float pValor) {
        return (pValor+this.media)*this.desviacionTipica;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public void setDesviacionTipica(float desviacionTipica) {
        this.desviacionTipica = desviacionTipica;
    }
}
