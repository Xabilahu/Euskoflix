package eus.ehu.euskoflix.packModelo;

import java.awt.*;

public class Informacion {

    private Image poster;
    private String sinopsis;
    private String director;

    public Informacion(Image pPoster, String pSinopsis, String pDirector) {
        this.poster = pPoster;
        this.sinopsis = pSinopsis;
        this.director = pDirector;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Sinopsis: " + sinopsis + "\n");
        str.append("Director: " + director + "\n");
        return str.toString();
    }
    public String getSinopsis() {
    	return this.sinopsis;
    }
    public Image getPoster() {
    	return this.poster;
    }

}
