package eus.ehu.euskoflix.packControlador;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseDatos {

	private static BaseDatos mBaseDatos;
	private Connection c;
	private Statement s;
	
	private BaseDatos() {}
	
	public static BaseDatos getBaseDatos() {
		if (mBaseDatos == null)
			mBaseDatos = new BaseDatos();
		return mBaseDatos;
		
	}
	
	//pre: -
	//post: si la base da datos no existe, se crea una nueva
	//desc: metodo para inicializar la base de datos del sistema
	public void iniciarBD() {
		if (!comprobarExisteBD())
			crearBD();		
	}
	
	//pre: -
	//post: si existia una base de datos se elimina y se crea una nueva
	//desc: metodo para eliminar la base de datos existente y crear una nueva
	public void reiniciarBD() {
    	File file = new File("data/basedatos.db");
    	file.delete();
    	crearBD();
    }
	
	//pre: -
	//post: devuelve true si existe la base de datos, false si no existe
	//desc: metodo para comprobar si ya existe una base de datos
	private boolean comprobarExisteBD() {
		File f = new File("data/basedatos.db");
		return f.exists();
	}
	
	//pre: -
	//post: se introducen las tablas en la base de datos
	//desc: metodo para crear la base de datos con sus tablas y claves	
	private void crearBD() {
		File data_dir = new File("data");
		data_dir.mkdir();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:data/basedatos.db");
			s = c.createStatement();
			
			String instruccion = "CREATE TABLE usuario ( " +
					"    id INTEGER NOT NULL, " +
					"    contrasena TEXT NOT NULL, " +
					"    nombre TEXT NOT NULL, " +
					"    apellido TEXT NOT NULL, " +

					"    PRIMARY KEY(id)); " +

					"CREATE TABLE pelicula ( " +
					"    id INTEGER NOT NULL, " +
					"    idTMDB INTEGER NOT NULL, " +
					"    titulo TEXT NOT NULL, " +
					"    genero TEXT, " +

					"    PRIMARY KEY (id));" +
					
					"CREATE TABLE valoracion ( " +
					"    id_usuario INTEGER NOT NULL, " +
					"    id_pelicula INTEGER NOT NULL, " +
					"    valoracion REAL NOT NULL, " +
					
					"    PRIMARY KEY (id_usuario, id_pelicula), " +
					"    FOREIGN KEY (id_usuario) REFERENCES usuario(id), " +
					"    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id)); " +
					
					"CREATE TABLE etiqueta ( " +
					"    id_usuario INTEGER NOT NULL, " +
					"    id_pelicula INTEGER NOT NULL, " +
					"    etiqueta TEXT NOT NULL, " +
					
					"    PRIMARY KEY (id_usuario, id_pelicula, etiqueta), " +
					"    FOREIGN KEY (id_usuario) REFERENCES usuario(id), " +
					"    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id)); " ;
			
			s.executeUpdate(instruccion);
			s.close();
			c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
		System.out.println("Base de datos creada");
		
	}
	
	
	//pre: el id introducido debe existir en la base de datos
	//post: devuelve el string del nombre correspondiente al id
	//desc: se introduce el id de un usuario que existe y se obtiene el string de su nombre
	public String idUsuarioANombre(int pId) {
		
		String nombre = "";
		
		try {
		    Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:data/basedatos.db");
		    c.setAutoCommit(false);
	
		    s = c.createStatement();
		    ResultSet rs = s.executeQuery("SELECT nombre FROM usuario WHERE id=" + pId + ";");
	
		    if (rs.next())
		        nombre = rs.getString("nombre");
	
		    rs.close();
		    s.close();
		    c.close();
	
		} catch (Exception e) {
		    System.err.println(e.getClass().getName() + ": " + e.getMessage());
		    System.exit(0);
		}
		    
		return nombre;
		
	}
	
}
