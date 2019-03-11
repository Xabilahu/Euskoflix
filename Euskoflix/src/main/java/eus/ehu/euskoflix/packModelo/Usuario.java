package eus.ehu.euskoflix.packModelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String password;
    private ListaPeliculas pelisValoradas;

    public Usuario(String pNombre, String pApellido, String pPassword) {
        this.nombre = pNombre;
        this.apellido = pApellido;
        this.password = this.encriptarPassword(pPassword);
        this.pelisValoradas = new ListaPeliculas();
    }

    public boolean comprobarPassword(String pPassword){
        return this.password.equals(this.encriptarPassword(pPassword));
    }

    private String encriptarPassword(String pPassword) {
        try {
            MessageDigest mg = MessageDigest.getInstance(PropertiesManager.getInstance().getEncryptionType());
            mg.reset();
            mg.update(pPassword.getBytes());
            return new BigInteger(1, mg.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error de encriptación");
            return null;
        }
    }
}
