package eus.ehu.euskoflix.packDatos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class BaseDatosTest {

    BaseDatos baseDatos = BaseDatos.getBaseDatos();
    private Connection c;
    private Statement s;

    @Before
    public void setUp() throws Exception {
        baseDatos.reiniciarBD();
        String instruccion1 = /*"INSERT INTO usuario VALUES "
				+ "(80425, 'contra0', 'nombre0', 'apellido0'),"
				+ "(92496, 'contra1', 'nombre1', 'apellido1'),"
				+ "(8725, 'contra2', 'nombre2', 'apellido2'),"
				+ "(284, 'contra3', 'nombre3', 'apellido3'),"
				+ "(396, 'contra4', 'nombre4', 'apellido4');"

				+ */" INSERT INTO pelicula VALUES "
                + "(4624, 4823, 'Enter the Void', 'drama'),"
                + "(729, 159, 'Ghost in the Shell', 'accion'),"
                + "(97296, 1218, 'Blade Runner 2049', 'thriller'),"
                + "(628, 6987, 'Martin (hache)', 'drama'),"
                + "(118, 947, 'Brave New World (1998)', 'drama');"

                + " INSERT INTO valoracion VALUES "
                + "(80425, 729, 4.0),"
                + "(284, 4624, 4.5),"
                + "(80425, 97296, 3.0),"
                + "(396, 97296, 4.5),"
                + "(284, 97296, 3.5);"

                + " INSERT INTO etiqueta VALUES "
                + "(80425, 729, 'simple'),"
                + "(284, 4624, 'psicodelica'),"
                + "(80425, 97296, 'filosofica'),"
                + "(396, 97296, 'aburrida'),"
                + "(284, 4624, 'crazy')";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + new File(BaseDatos.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()
                    + File.separator + "data/basedatos.db");
            s = c.createStatement();

            s.executeUpdate(instruccion1);
            s.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(-1);
        }

        System.out.println("Datos de pruebas introducidos en la base de datos");
    }

    @After
    public void tearDown() throws Exception {
    }

}
