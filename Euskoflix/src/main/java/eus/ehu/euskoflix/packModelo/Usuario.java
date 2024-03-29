package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packDatos.PropertiesManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.JsonObject;

public class Usuario extends Normalizable {

    private int id;
    private String nombre;
    private String apellido;
    private String password;

    public Usuario(int pId, String pNombre, String pApellido, String pPassword) {
        super();
        this.id = pId;
        this.nombre = pNombre;
        this.apellido = pApellido;
        this.password = this.encriptarPassword(pPassword);

    }

    public boolean comprobarPassword(Usuario pUser) {
        return this.password.equals(this.encriptarPassword(pUser.password));
    }

    private String encriptarPassword(String pPassword) {
        try {
            MessageDigest mg = MessageDigest.getInstance(PropertiesManager.getInstance().getEncryptionType());
            mg.reset();
            mg.update(pPassword.getBytes());
            return new BigInteger(1, mg.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error de encriptación.");
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getPassword() {
        return password;
    }
    
    public String[] usuarioToStringArray() {
        String[] res = new String[2];
        res[0] = this.nombre;
        res[1] = this.apellido;
        return res;
    }
    
}
