import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import eus.ehu.euskoflix.packControlador.PropertiesManager;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        MessageDigest mg = MessageDigest.getInstance("SHA-256");
        mg.reset();
        mg.update("euskoflix".getBytes());
        BigInteger b = new BigInteger(1,mg.digest());
        System.out.println(b.toString(16));
        mg.reset();
        mg.update("euskoflix".getBytes());
        b = new BigInteger(1,mg.digest());
        System.out.println(b.toString(16));
        PropertiesManager.getInstance().getAPIKey();
        //getRandomNames();


    }

    private static void getRandomNames() throws IOException {
        int cont = 0;
        File f = new File("nombres.csv");
        while (cont <7120){
            URL mov = null;
            try {
                mov = new URL("https://uinames.com/api/?amount=500&region=spain");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) mov.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder str = new StringBuilder();
            while (in.ready()) {
                try {
                    str.append(in.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            in.close();
            con.disconnect();
            String json = str.toString();
            JsonParser parser = new JsonParser();
            JsonArray nombres = parser.parse(json).getAsJsonArray();
            BufferedWriter out = new BufferedWriter(new FileWriter(f,true));
            for (JsonElement js : nombres){
                JsonObject j = js.getAsJsonObject();
                if (!j.get("name").getAsString().equals("") && !j.get("surname").getAsString().equals("")){
                    out.write(j.get("name").getAsString() + "," + j.get("surname").getAsString());
                    out.newLine();
                    cont++;
                }
            }
            out.close();
        }
    }

}
