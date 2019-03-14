package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packModelo.Cartelera;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    private static PropertiesManager ourInstance = new PropertiesManager();
    private Properties properties;

    public static final String PROPERTIES_FILE_NAME = "euskoflix.properties";

    public static PropertiesManager getInstance() {
        return ourInstance;
    }

    private PropertiesManager() {
        this.properties = new Properties();
        File f = new File(Cartelera.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            InputStream in = new FileInputStream(f.getParent() + File.separator + PROPERTIES_FILE_NAME);
            this.properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEncryptionType(){
        return this.properties.getProperty("encryption.type");
    }

    public String getPathToFile(String file){
        return this.properties.getProperty("path.to." + file);
    }

    public String getAPIKey(){
        return this.properties.getProperty("api.key");
    }

    public String getMovieApiRequestURL(int id){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.properties.getProperty("api.movie.url"));
        stringBuilder.append(id);
        stringBuilder.append("?api_key=");
        stringBuilder.append(this.properties.getProperty("api.key"));
        stringBuilder.append("&language=es");
        return  stringBuilder.toString();
    }

    public String getPosterApiRequestURL(String image){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.properties.getProperty("api.image.url"));
        stringBuilder.append(this.properties.getProperty("api.image.width"));
        stringBuilder.append(image);
        return  stringBuilder.toString();
    }

    public String getCreditsApiRequestURL(int id){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.properties.getProperty("api.movie.url"));
        stringBuilder.append(id);
        stringBuilder.append("/credits?api_key=");
        stringBuilder.append(this.properties.getProperty("api.key"));
        stringBuilder.append("&language=es");
        return  stringBuilder.toString();
    }

    public String getDefaultPassword() {
        return this.properties.getProperty("default.password");
    }

    public String getPathToLogo(){
        return this.properties.getProperty("path.to.logo");
    }


}
