import eus.ehu.euskoflix.packModelo.PropertiesManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest mg = MessageDigest.getInstance("SHA-256");
        mg.reset();
        mg.update("patata".getBytes());
        BigInteger b = new BigInteger(1,mg.digest());
        System.out.println(b.toString(16));
        mg.reset();
        mg.update("patata".getBytes());
        b = new BigInteger(1,mg.digest());
        System.out.println(b.toString(16));
        PropertiesManager.getInstance().getAPIKey();
    }

}
