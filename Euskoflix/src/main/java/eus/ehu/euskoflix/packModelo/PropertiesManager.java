package eus.ehu.euskoflix.packModelo;

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
        System.out.println(f);
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
}
