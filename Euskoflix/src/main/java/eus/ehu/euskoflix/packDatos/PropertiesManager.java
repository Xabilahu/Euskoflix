package eus.ehu.euskoflix.packDatos;

import eus.ehu.euskoflix.packModelo.Cartelera;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static final String PROPERTIES_FILE_NAME = "euskoflix.properties";
    private static PropertiesManager ourInstance = new PropertiesManager();
    private Properties properties;

    private PropertiesManager() {
        this.properties = new Properties();
        File f = new File(Cartelera.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            f = new File(f.getParent() + File.separator + PROPERTIES_FILE_NAME);
            InputStream in;
            if (!f.exists()) {
                in = PropertiesManager.class.getResourceAsStream("/eus/ehu/euskoflix/" + PROPERTIES_FILE_NAME);
            } else {
                in = new FileInputStream(f);
            }
            this.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesManager getInstance() {
        return ourInstance;
    }

    public String getEncryptionType() {
        return this.properties.getProperty("encryption.type");
    }

    public String getPathToFile(String file) {
        return this.properties.getProperty("path.to." + file);
    }

    public String getMovieApiRequestURL(int id) {
        return getString(id, "?api_key=");
    }

    public String getPosterApiRequestURL(String image) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.properties.getProperty("api.image.url"));
        stringBuilder.append(this.properties.getProperty("api.image.width"));
        stringBuilder.append(image);
        return stringBuilder.toString();
    }

    public String getCreditsApiRequestURL(int id) {
        return getString(id, "/credits?api_key=");
    }

    public String getTrailerApiRquestURL(int id) {
        return this.properties.getProperty("api.movie.url") + id + "/videos?api_key=" + this.properties.getProperty("api.key") + "&language=en-US";
    }

    public String getDefaultPassword() {
        return this.properties.getProperty("default.password");
    }

    public String getPathToLogo() {
        return this.properties.getProperty("path.to.logo");
    }

    public String getPathToMovieNotFound() {
        return this.properties.getProperty("path.to.movie.not.found");
    }

    public String getPathToLoginLogo() {
        return this.properties.getProperty("path.to.login.logo");
    }

    public String getPathToUserIcon() {
        return this.properties.getProperty("path.to.user.icon");
    }

    public String getPathToMovieIcon() {
        return this.properties.getProperty("path.to.movie.icon");
    }

    public String getPathToMainIcon() {
        return this.properties.getProperty("path.to.logo.icon");
    }

    public String getPathToGif() {
        return this.properties.getProperty("path.to.gif");
    }

    public String getNewUsersFileName() {
        return this.properties.getProperty("new.users");
    }

    public String getPathToSpanish() {
        return this.properties.getProperty("path.to.spanish");
    }

    public String getPathToEnglish() {
        return this.properties.getProperty("path.to.english");
    }

    public String getDefaultTrailerUrl() {
        return this.properties.getProperty("url.video");
    }

    private String getString(int id, String s) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.properties.getProperty("api.movie.url"));
        stringBuilder.append(id);
        stringBuilder.append(s);
        stringBuilder.append(this.properties.getProperty("api.key"));
        stringBuilder.append("&language=es");
        return stringBuilder.toString();
    }


}
