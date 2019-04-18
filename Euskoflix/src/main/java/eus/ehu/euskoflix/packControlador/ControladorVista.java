package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import eus.ehu.euskoflix.packModelo.*;
import eus.ehu.euskoflix.packModelo.packFiltro.Filtrado;
import eus.ehu.euskoflix.packModelo.packFiltro.TipoRecomendacion;
import eus.ehu.euskoflix.packPrincipal.windowTesting.ReproductorVideo;
import eus.ehu.euskoflix.packVista.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControladorVista {

    private static ControladorVista mControladorVista;

    /* Controlador */
    private GestionDatos gestionDatos;

    /* Vista */
    private EuskoFlixLoader euskoFlixLoader;
    private VentanaCargaDatos ventanaCargaDatos;
    private InformacionExtraView informacionExtraView;
    private VentanaLogin ventanaLogin;
    private VentanaUsuario ventanaUsuario;
    private ReproductorVideo reproductorVideo;

    private ControladorVista() {
        this.ventanaLogin = new VentanaLogin();
        this.gestionDatos = GestionDatos.getInstance();
    }

    public static ControladorVista getInstance() {
        if (mControladorVista == null) {
            mControladorVista = new ControladorVista();
        }
        return mControladorVista;
    }

    public void iniciarAplicacion() {
        this.mostrarLoader();
        this.gestionDatos.cargarDatos(TipoFichero.small);
        this.cerrarLoader();
        this.mostrarLogin();
//        this.mostrarCargaDatos();
    }

    private void mostrarLoader() {
        this.euskoFlixLoader = new EuskoFlixLoader();
        this.euskoFlixLoader.setVisible(true);
    }

    private void mostrarLogin() {
        this.ventanaLogin.anadirFocusListener(new VentanaLoginListenerUser(), new VentanaLoginListenerPass());
        this.ventanaLogin.anadirActionListener(new LoguearseListener());
        this.ventanaLogin.setVisible(true);
    }

    private void cerrarLoader() {
        this.euskoFlixLoader.setVisible(false);
        this.euskoFlixLoader.dispose();
        this.euskoFlixLoader = null;
    }

    public void mostrarCargaDatos() {

        this.ventanaCargaDatos = new VentanaCargaDatos(datosUsuario(), getCabeceraUsers(), datosPelis(), getCabeceraFilms());

        /* Listeners VentanaCargaDatos*/
        this.ventanaCargaDatos.addInfoExtraListener(new InfoExtraListener());

        this.ventanaCargaDatos.setVisible(true);
        JOptionPane.showMessageDialog(ventanaCargaDatos,
                "Doble click en una película para más información", "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String[][] datosUsuario() {
        return CatalogoUsuarios.getInstance().usuariosToArrayString();
    }

    public String[] getCabeceraUsers() {
        return new String[]{"ID", "Nombre", "Apellido", "Password"};
    }

    //Pestana Tags
    public String[][] datosTags(int id) {
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(id);
        GestionDatos.getInstance().addTags(p);
        return p.tagsToStringArray();
    }

    public String[] getCabeceraTags() {
        return new String[]{"Tag", "Apariciones"};
    }

    //Pestana Ratings
    public String[][] datosRatings(int pId) {
        LinkedHashMap<Integer, Double> ratings = MatrizValoraciones.getInstance().getValoracionesByPelicula(pId);
        String[][] resultado = new String[ratings.size()][2];
        CatalogoUsuarios cat = CatalogoUsuarios.getInstance();
        int i = 0;
        for (Map.Entry<Integer, Double> entry : ratings.entrySet()) {
            resultado[i][0] = cat.getUsuarioPorId(entry.getKey()).getNombre();
            resultado[i++][1] = entry.getValue().toString();
        }
        return resultado;
    }

    public void crearInfoExtraView(int pId) {
        informacionExtraView = new InformacionExtraView(pId);
        Object[] info = getInfoPelicula(pId);
        informacionExtraView.initComponents(pId, datosTags(pId), getCabeceraTags(), datosRatings(pId), getCabeceraRatings());
        if (info[4] != null) {
            informacionExtraView.addReproductorListener(new ReproductorListener((String) info[4]));
        } else {
            informacionExtraView.desactivarTrailer();
        }
        informacionExtraView.fillComponents(info);

        informacionExtraView.addCerrarListener(new CerrarInfoExtraListener());
        ventanaCargaDatos.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        informacionExtraView.setVisible(true);
    }

    public String[] getCabeceraRatings() {
        return new String[]{"NombreUsuario", "Rating"};
    }

    //Pestana peliculas y su cabecera
    public String[][] datosPelis() {
        Cartelera films = Cartelera.getInstance();
        int size = films.getNumPeliculas();
        String[][] resulFilms = new String[size][2];
        Pelicula filmNew;
        for (int i = 0; i < size; i++) {
            filmNew = films.getPeliculaPorId(i);
            resulFilms[i][0] = String.valueOf(filmNew.getId());
            resulFilms[i][1] = filmNew.getTitulo();
        }
        return resulFilms;
    }

    public String[] getCabeceraFilms() {
        return new String[]{"ID", "Titulo"};
    }


    /**
     * @return [titulo, director, sinopsis, poster]
     */
    public Object[] getInfoPelicula(int pId) {
        Object[] result = new Object[5];
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(pId);
        result[0] = p.getTitulo();
        result[1] = p.getDirector();
        result[2] = p.getSinopsis();
        result[3] = p.getPoster();
        result[4] = p.getTrailerUrl();
        return result;
    }

    /* Implementaciones de los listeners */

    class InfoExtraListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            JTable table = (JTable) mouseEvent.getSource();
            Point point = mouseEvent.getPoint();
            int row = table.rowAtPoint(point);
            if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                int id = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
                ventanaCargaDatos.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                crearInfoExtraView(id);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class CerrarInfoExtraListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            informacionExtraView.dispose();
        }
    }

    class VentanaLoginListenerUser implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            if (ventanaLogin.getTxtUser().getText().equals("ID de usuario")) {
                ventanaLogin.getTxtUser().setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (ventanaLogin.getTxtUser().getText().isEmpty()) {
                ventanaLogin.getTxtUser().setText("ID de usuario");
            }
        }
    }

    class VentanaLoginListenerPass implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            ventanaLogin.getTxtPass().setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (ventanaLogin.getTxtPass().getText() .isEmpty()) {
                ventanaLogin.getTxtPass().setText("Contraseña");
            }
        }
    }
    
    class LoguearseListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int username = ventanaLogin.getUsuario();
            if (username != Integer.MIN_VALUE) {
                ventanaLogin.setVisible(false);
                mostrarLoader();
                Usuario user = CatalogoUsuarios.getInstance().login(new Usuario(username, "", "", ventanaLogin.getContra()));
                if (user == null) {
                    cerrarLoader();
                    ventanaLogin.setVisible(true);
                    JOptionPane.showMessageDialog(ventanaLogin,
                            "Usuario o contraseña incorrecto.", "Error login",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    ventanaLogin.dispose();
                    Object[][] vistas = this.generarInfoPelis(MatrizValoraciones.getInstance().getPeliculasVistas(username).toIntegerArray());
                    Object[][] recomendadas = this.generarInfoPelis(Filtrado.getInstance().recomendar(TipoRecomendacion.Hibrido, 10).toIntegerArray());
                    cerrarLoader();
                    ventanaUsuario = new VentanaUsuario(user.usuarioToStringArray(), vistas,recomendadas);
                }
            } else {
                JOptionPane.showMessageDialog(ventanaLogin,
                        "Usuario o contraseña incorrecto.", "Error login",
                        JOptionPane.ERROR_MESSAGE);
            }

		}

        private Object[][] generarInfoPelis(Integer[] pPeliculasVistas) {
		    Object[][] result = new Object[pPeliculasVistas.length][2];
		    int x = 0;
		    for (Integer i : pPeliculasVistas){
		        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(i);
		        result[x][0] = p.getPoster();
		        result[x++][1] = p.getTitulo();
            }
		    return result;
        }
    }

    class ReproductorListener implements ActionListener{

        private String url;

        public ReproductorListener(String str) {
            this.url = str;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            reproductorVideo = new ReproductorVideo();
            reproductorVideo.loadURL(this.url);
        }
    }
}
