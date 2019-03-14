package eus.ehu.euskoflix.packDatos;

import eus.ehu.euskoflix.packControlador.PropertiesManager;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.StringTokenizer;

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

	private File getBDFile() {
		return new File(new File(BaseDatos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()
				+ File.separator + "data/basedatos.db");
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
    	File file = this.getBDFile();
		file.delete();
    	crearBD();
    }
	
	//pre: -
	//post: devuelve true si existe la base de datos, false si no existe
	//desc: metodo para comprobar si ya existe una base de datos
	private boolean comprobarExisteBD() {
		File f = this.getBDFile();
		return f.exists();
	}
	
	//pre: -
	//post: se introducen las tablas en la base de datos
	//desc: metodo para crear la base de datos con sus tablas y claves	
	private void crearBD() {
		File data_dir = new File(this.getBDFile().getParent());
		data_dir.mkdir();
		try {
			Class.forName("org.sqlite.JDBC");
			c = this.getConexion();
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
					"    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id)); " +
					
					"CREATE TABLE genero ( " +
					"    id INTEGER NOT NULL, " +
					"    nombre TEXT NOT NULL, " +
					
					"    PRIMARY KEY (id)); " +
					
					"CREATE TABLE pelicula_genero ( " +
					"    id_genero INTEGER NOT NULL, " +
					"    id_pelicula INTEGER NOT NULL, " +
					
					"    PRIMARY KEY (id_genero, id_pelicula), " +
					"    FOREIGN KEY (id_genero) REFERENCES genero(id), " +
					"    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id)); ";

			s.executeUpdate(instruccion);
			s.close();
			c.close();
			anadirDatos();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(-1);
        }
		System.out.println("Base de datos creada");
		
	}

	private Connection getConexion() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection("jdbc:sqlite:" + this.getBDFile().getPath());
	}

	private void anadirDatos() {
		anadirUsuarios();
		anadirPeliculas();
		anadirValoraciones();
		anadirEtiquetas();
	}

	private void anadirPeliculas() {
		addIds();
		addPeliculaYGenero();
	}

	private void anadirEtiquetas() {
		try {
			c = this.getConexion();
			c.setAutoCommit(false);
			InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("tags"));
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				in.readLine(); // skip headers
				PreparedStatement pst = c.prepareStatement("INSERT INTO etiqueta(id_usuario, id_pelicula, etiqueta) VALUES (?,?,?)");
				while (in.ready()){
					StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());
					pst.setInt(1, Integer.parseInt(stringTokenizer.nextToken(",")));
					pst.setInt(2, Integer.parseInt(stringTokenizer.nextToken(",")));
					pst.setString(3, stringTokenizer.nextToken(","));
					pst.addBatch();
				}
				pst.executeBatch();
				c.commit();
				c.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void anadirValoraciones(){
		try {
			c = this.getConexion();
			c.setAutoCommit(false);
			InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("ratings"));
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				in.readLine(); // skip headers
				PreparedStatement pst = c.prepareStatement("INSERT INTO valoracion(id_usuario, id_pelicula, valoracion) VALUES (?,?,?)");
				while (in.ready()){
					StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());
					pst.setInt(1, Integer.parseInt(stringTokenizer.nextToken(",")));
					pst.setInt(2, Integer.parseInt(stringTokenizer.nextToken(",")));
					pst.setFloat(3, Float.parseFloat(stringTokenizer.nextToken(",")));
					pst.addBatch();
				}
				pst.executeBatch();
				c.commit();
				c.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void anadirUsuarios() {
		try {
			c = this.getConexion();
			c.setAutoCommit(false);
			InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("nombres"));
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				PreparedStatement pst = c.prepareStatement("INSERT INTO usuario(contrasena,nombre,apellido) VALUES(?,?,?)");
				String defaultPass = PropertiesManager.getInstance().getDefaultPassword();
				while (in.ready()){
					StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());
					pst.setString(1,defaultPass);
					pst.setString(2,stringTokenizer.nextToken(","));
					pst.setString(3,stringTokenizer.nextToken(","));
					pst.addBatch();
				}
				pst.executeBatch();
				c.commit();
				c.setAutoCommit(true);
				c.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addIds() {
		try {
			c = this.getConexion();
			c.setAutoCommit(false);
			InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("links"));
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			in.readLine(); // skip headers
			PreparedStatement pst = c.prepareStatement("INSERT INTO pelicula(id, idTMDB,titulo) VALUES (?,?,\"\")");
			while(in.ready()){
				String line = in.readLine();
				if (line.matches("\\d+\\,\\d+\\,\\d+")){
					pst.setInt(1,Integer.parseInt(line.substring(0,line.indexOf(","))));
					pst.setInt(2,Integer.parseInt(line.substring(line.lastIndexOf(",")+1)));
					pst.addBatch();
				}
			}
			pst.executeBatch();
			c.commit();
			c.close();
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addPeliculaYGenero() {
		try {
			HashMap<String,Integer> genres = new HashMap<String, Integer>();
			c = this.getConexion();
			c.setAutoCommit(false);
			InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("movies"));
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			in.readLine(); // skip headers
			PreparedStatement peliculasPst = c.prepareStatement("UPDATE pelicula set titulo=? where id=?");
			PreparedStatement generosPst = c.prepareStatement("INSERT INTO genero(id,nombre) VALUES (?,?)");
			PreparedStatement peliculaGeneroPst = c.prepareStatement("INSERT INTO pelicula_genero(id_pelicula,id_genero) VALUES(?,?)");
			int genreId = 1;
			while(in.ready()){
				String line = in.readLine();
				StringTokenizer stringTokenizer = new StringTokenizer(line);
				int id = Integer.parseInt(stringTokenizer.nextToken(","));
				// añadiendo titulo
				peliculasPst.setString(1,line.substring(line.indexOf(",")+1,line.lastIndexOf(",")));
				peliculasPst.setInt(2,id);
				peliculasPst.addBatch();
				//añadiendo genero
				stringTokenizer = new StringTokenizer(line.substring(line.lastIndexOf(",")+1));
				while(stringTokenizer.hasMoreTokens()){
					String genre = stringTokenizer.nextToken("|");
					if (!genres.containsKey(genre)){
						//añadiendo genero si no exite
						genres.put(genre,genreId);
						generosPst.setInt(1,genreId++);
						generosPst.setString(2,genre);
						generosPst.addBatch();
					}
					//añadiendo relacion pelicula genero
					peliculaGeneroPst.setInt(1,id);
					peliculaGeneroPst.setInt(2,genres.get(genre));
					peliculaGeneroPst.addBatch();
				}
			}
			peliculasPst.executeBatch();
			generosPst.executeBatch();
			peliculaGeneroPst.executeBatch();
			c.commit();
			c.close();
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//pre: el id introducido debe existir en la base de datos
	//post: devuelve el string del nombre correspondiente al id
	//desc: se introduce el id de un usuario que existe y se obtiene el string de su nombre
	public String idUsuarioANombre(int pId) {
		
		String nombre = "";
		
		try {
		    c = this.getConexion();
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
		    System.exit(-1);
		}
		    
		return nombre;
		
	}

	public ResultSet getPeliculas() {
		return this.pedirTabla("peliculas");
	}

	public ResultSet getTags() {
		return this.pedirTabla("tags");
	}

	public ResultSet getUsuarios() {
		return this.pedirTabla("usuarios");
	}

	public ResultSet getValoraciones() {
		return this.pedirTabla("valoraciones");
	}

	private ResultSet pedirTabla(String query) {
		ResultSet rst = null;
		try {
			PreparedStatement pst = this.getConexion().prepareStatement("SELECT * FROM ?");
			switch (query) {
				case "peliculas":
					pst.setString(1, "pelicula");
					break;
				case "valoraciones":
					pst.setString(1, "valoracion");
					break;
				case "usuarios":
					pst.setString(1, "usuario");
					break;
				case "tags":
					pst.setString(1, "etiqueta");
					break;
			}
			rst = pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
