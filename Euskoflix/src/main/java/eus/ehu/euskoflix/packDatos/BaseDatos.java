package eus.ehu.euskoflix.packDatos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BaseDatos {

    private static BaseDatos mBaseDatos;

    private BaseDatos() {
    }

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
    public void iniciarBD(TipoFichero pTipo) {
        if (!comprobarExisteBD())
            crearBD(pTipo);
        else {}
    }

    /**
     * This method is only used in jUnit
     */
    //pre: -
    //post: si existia una base de datos se elimina y se crea una nueva
    //desc: metodo para eliminar la base de datos existente y crear una nueva
    public void reiniciarBD(TipoFichero pTipo) {
        File file = this.getBDFile();
        file.delete();
        crearBD(pTipo);
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
    private void crearBD(TipoFichero pTipo) {
        File data_dir = new File(this.getBDFile().getParent());
        data_dir.mkdir();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = this.getConexion();
            Statement s = c.createStatement();

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

            if (pTipo == TipoFichero.small) {
                instruccion += "CREATE TABLE pelicula_info ( " +
                               "   id_pelicula INTEGER NOT NULL, " +
                               "   director TEXT, " +
                               "   poster_path TEXT, " +
                               "   trailer_path TEXT, " +
                               "   synopsis TEXT, " +

                               "   PRIMARY KEY (id_pelicula), " +
                               "   FOREIGN KEY (id_pelicula) REFERENCES pelicula(id)); ";
            }

            s.executeUpdate(instruccion);
            s.close();
            c.close();
            anadirDatos(pTipo);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(-1);
        }
    }

    private Connection getConexion() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:sqlite:" + this.getBDFile().getPath());
    }

    private void anadirDatos(TipoFichero pTipo) {
        anadirPeliculas(pTipo);
        anadirInfoExtra(pTipo);
        anadirUsuariosYValoraciones(pTipo);
        anadirEtiquetas(pTipo);
    }

    private void anadirInfoExtra(TipoFichero pTipo) {
        if (pTipo == TipoFichero.small) {
            try {
                Connection c = this.getConexion();
                c.setAutoCommit(false);
                InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile("smallInfo"));
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    in.readLine(); // skip headers
                    PreparedStatement pst = c.prepareStatement("INSERT INTO pelicula_info VALUES (?,?,?,?,?)");
                    while (in.ready()) {
                        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine(),",");
                        pst.setInt(1, Integer.parseInt(stringTokenizer.nextToken()));
                        pst.setString(2,stringTokenizer.nextToken());
                        pst.setString(3,stringTokenizer.nextToken());
                        pst.setString(4,stringTokenizer.nextToken());
                        StringBuilder synopsis = new StringBuilder();
                        while (stringTokenizer.hasMoreTokens()) {
                            synopsis.append(stringTokenizer.nextToken());
                        }
                        pst.setString(5,synopsis.toString());
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
    }

    private void anadirPeliculas(TipoFichero pTipo) {
        addPeliculaYGenero(pTipo);
        addIds(pTipo);
    }

    private void anadirEtiquetas(TipoFichero pTipo) {
        try {
            Connection c = this.getConexion();
            c.setAutoCommit(false);
            String fileName = "";
            switch (pTipo) {
                case big:
                    fileName = "tags";
                    break;
                case test:
                    fileName = "testTags";
                    break;
                case small:
                    fileName = "smallTags";
                    break;
            }
            InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile(fileName));
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                in.readLine(); // skip headers
                PreparedStatement pst = c.prepareStatement("INSERT INTO etiqueta(id_usuario, id_pelicula, etiqueta) VALUES (?,?,?)");
                while (in.ready()) {
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

    private void anadirUsuariosYValoraciones(TipoFichero pTipo) {
        try {
            Connection c = this.getConexion();
            c.setAutoCommit(false);
            String fileName = "";
            String userFileName = "";
            switch (pTipo) {
                case big:
                    fileName = "ratings";
                    userFileName = "nombres";
                    break;
                case test:
                    fileName = "testRatings";
                    userFileName = "testNombres";
                    break;
                case small:
                    fileName = "smallRatings";
                    userFileName = "smallNombres";
                    break;
            }
            InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile(fileName));
            InputStream is1 = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile(userFileName));
            try {
                String defaultPass = PropertiesManager.getInstance().getDefaultPassword();
                BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                BufferedReader inUsuario = new BufferedReader(new InputStreamReader(is1, StandardCharsets.UTF_8));
                in.readLine(); // skip headers
                PreparedStatement pst = c.prepareStatement("INSERT INTO valoracion(id_usuario, id_pelicula, valoracion) VALUES (?,?,?)");
                PreparedStatement pstUsuarios = c.prepareStatement("INSERT INTO usuario(id,contrasena,nombre,apellido) VALUES(?,?,?,?)");
                int lastIdUsurio = -1;
                while (in.ready()) {
                    StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());
                    int idUsuario = Integer.parseInt(stringTokenizer.nextToken(","));
                    pst.setInt(1, idUsuario);
                    pst.setInt(2, Integer.parseInt(stringTokenizer.nextToken(",")));
                    pst.setFloat(3, Float.parseFloat(stringTokenizer.nextToken(",")));
                    pst.addBatch();
                    if (lastIdUsurio != idUsuario) {
                        StringTokenizer stringTokenizerU = new StringTokenizer(inUsuario.readLine());
                        pstUsuarios.setInt(1, idUsuario);
                        pstUsuarios.setString(2, defaultPass);
                        pstUsuarios.setString(3, stringTokenizerU.nextToken(","));
                        pstUsuarios.setString(4, stringTokenizerU.nextToken(","));
                        pstUsuarios.addBatch();
                        lastIdUsurio = idUsuario;
                    }
                }
                pstUsuarios.executeBatch();
                pst.executeBatch();
                c.commit();
                c.close();
                in.close();
                inUsuario.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addIds(TipoFichero pTipo) {
        try {
            Connection c = this.getConexion();
            c.setAutoCommit(false);
            String fileName = "";
            switch (pTipo) {
                case big:
                    fileName = "links";
                    break;
                case test:
                    fileName = "testLinks";
                    break;
                case small:
                    fileName = "smallLinks";
                    break;
            }
            InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile(fileName));
            BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            in.readLine(); // skip headers
            PreparedStatement pst = c.prepareStatement("UPDATE pelicula SET idTMDB=? WHERE id=?");
            while (in.ready()) {
                String line = in.readLine();
                if (line.matches("\\d+\\,\\d+\\,\\d+")) {
                    pst.setInt(2, Integer.parseInt(line.substring(0, line.indexOf(","))));
                    pst.setInt(1, Integer.parseInt(line.substring(line.lastIndexOf(",") + 1)));
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

    private void addPeliculaYGenero(TipoFichero pTipo) {
        try {
            HashMap<String, Integer> genres = new HashMap<String, Integer>();
            Connection c = this.getConexion();
            c.setAutoCommit(false);
            String fileName = "";
            switch (pTipo) {
                case big:
                    fileName = "movies";
                    break;
                case test:
                    fileName = "testMovies";
                    break;
                case small:
                    fileName = "smallMovies";
                    break;
            }
            InputStream is = BaseDatos.class.getResourceAsStream(PropertiesManager.getInstance().getPathToFile(fileName));
            BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            in.readLine(); // skip headers
            PreparedStatement peliculasPst = c.prepareStatement("INSERT INTO pelicula VALUES(?,0,?)");
            PreparedStatement generosPst = null;
            PreparedStatement peliculaGeneroPst = null;
            if (pTipo != TipoFichero.small) {
                generosPst = c.prepareStatement("INSERT INTO genero(id,nombre) VALUES (?,?)");
                peliculaGeneroPst = c.prepareStatement("INSERT INTO pelicula_genero(id_pelicula,id_genero) VALUES(?,?)");
            }
            int genreId = 1;
            while (in.ready()) {
                String line = in.readLine();
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                int id = Integer.parseInt(stringTokenizer.nextToken(","));
                // añadiendo titulo
                if (pTipo == TipoFichero.small) {
                    peliculasPst.setString(2, stringTokenizer.nextToken());
                } else {
                    peliculasPst.setString(2, line.substring(line.indexOf(",") + 1, line.lastIndexOf(",")));
                }
                peliculasPst.setInt(1, id);
                peliculasPst.addBatch();
                //añadiendo genero
                if (pTipo != TipoFichero.small) {
                    stringTokenizer = new StringTokenizer(line.substring(line.lastIndexOf(",") + 1));
                    while (stringTokenizer.hasMoreTokens()) {
                        String genre = stringTokenizer.nextToken("|");
                        if (!genres.containsKey(genre)) {
                            //añadiendo genero si no exite
                            genres.put(genre, genreId);
                            generosPst.setInt(1, genreId++);
                            generosPst.setString(2, genre);
                            generosPst.addBatch();
                        }
                        //añadiendo relacion pelicula genero
                        peliculaGeneroPst.setInt(1, id);
                        peliculaGeneroPst.setInt(2, genres.get(genre));
                        peliculaGeneroPst.addBatch();
                    }
                }
            }
            peliculasPst.executeBatch();
            if (pTipo != TipoFichero.small) {
                generosPst.executeBatch();
                peliculaGeneroPst.executeBatch();
            }
            c.commit();
            c.close();
            in.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getPeliculas() {
        return this.pedirTabla("peliculas");
    }

    public ResultSet getUsuarios() {
        return this.pedirTabla("usuarios");
    }

    public ResultSet getValoraciones() {
        return this.pedirTabla("valoraciones");
    }

    public int getNumPeliculasValoradas() {
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("select count(*) from valoracion where id_pelicula in (select distinct id_pelicula from valoracion)");
            return pst.executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getNumUsuariosQueValoran() {
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("select count(distinct id_usuario) from valoracion");
            return pst.executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getNumValoraciones() {
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT count(*) FROM valoracion");
            return pst.executeQuery().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private ResultSet pedirTabla(String query) {
        ResultSet rst = null;
        try {
            PreparedStatement pst = null;
            switch (query) {
                case "peliculas":
                    pst = this.getConexion().prepareStatement("SELECT * FROM pelicula");
                    break;
                case "valoraciones":
                    pst = this.getConexion().prepareStatement("SELECT * FROM valoracion order by id_usuario, id_pelicula asc");
                    break;
                case "usuarios":
                    pst = this.getConexion().prepareStatement("SELECT * FROM usuario");
                    break;
            }
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public ResultSet getValoracionesUsuarios() {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT DISTINCT id_usuario FROM valoracion order by id_usuario asc");
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public ResultSet getNumValoracionesUsuario() {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("select id_usuario,count(valoracion) as suma from valoracion group by id_usuario order by id_usuario;");
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public ResultSet getTagsByPelicula(int pId) {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT etiqueta, count(*) AS veces FROM etiqueta WHERE id_pelicula = ? GROUP BY etiqueta ORDER BY etiqueta ASC");
            pst.setInt(1, pId);
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public ResultSet getInfoByPelicula(int pId) {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT director,poster_path,trailer_path,synopsis FROM pelicula_info WHERE id_pelicula = ?");
            pst.setInt(1, pId);
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * This method is only used in jUnit
     */
    public ResultSet getValoracionesByPelicula(int pId) throws SQLException {
        ResultSet rst = null;
        PreparedStatement pst = this.getConexion().prepareStatement("SELECT id_usuario, valoracion FROM valoracion WHERE id_pelicula = ? ORDER BY id_usuario ASC");
        pst.setInt(1, pId);
        rst = pst.executeQuery();
        return rst;
    }

    /**
     * This method is only used in jUnit
     */
    public void eliminarBaseDatos() {
        File f = new File(new File(BaseDatos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()
                + File.separator + "data" + File.separator + "basedatos.db");
        f.delete();
    }

    public ResultSet getNt() {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT etiqueta,count(distinct id_pelicula) FROM etiqueta GROUP BY etiqueta");
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    public ResultSet getTagsPeliculas() {
        ResultSet rst = null;
        try {
            PreparedStatement pst = this.getConexion().prepareStatement("SELECT DISTINCT etiqueta, id_pelicula FROM etiqueta");
            rst = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }
    
    public ResultSet getUsuarioPorId(int pId) {
        ResultSet rst = null;
        try {
	        PreparedStatement pst = this.getConexion().prepareStatement("SELECT nombre,apellido FROM usuario WHERE id = ?");
	        pst.setInt(1, pId);
	        rst = pst.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rst;
	}

}
