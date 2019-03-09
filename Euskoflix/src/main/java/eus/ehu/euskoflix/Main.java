package eus.ehu.euskoflix;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        new Main().makeTMBDRequest(550);
    }

    private void makeTMBDRequest(int tmdbID) {
        HttpURLConnection con = null;
        String url = "https://api.themoviedb.org/3/movie/" + tmdbID + "?api_key=e9f7e8f9f25e5afa642449f0d4a1b4a7";
        String json = "";
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder str = new StringBuilder();
            while (in.ready()) {
                str.append(in.readLine());
            }
            in.close();
            json = str.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null)
                con.disconnect();
        }
        System.out.println(json);
    }

}
