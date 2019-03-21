package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.PropertiesManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Informacion {

    private Image poster;
    private String sinopsis;
    private String director;

    public Informacion(Image pPoster, String pSinopsis, String pDirector) {
        this.poster = pPoster;
        this.sinopsis = pSinopsis;
        this.director = pDirector;
    }

    public Informacion() {
        try {
            this.poster = ImageIO.read(GestionDatos.class.getResourceAsStream(
                    PropertiesManager.getInstance().getPathToLogo()));
        } catch (IOException ignored) {

        }
        this.sinopsis = "None";
        this.director = "None";
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
