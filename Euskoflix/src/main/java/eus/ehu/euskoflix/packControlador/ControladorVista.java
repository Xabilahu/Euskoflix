package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packDatos.GestionDatos;
import eus.ehu.euskoflix.packDatos.TipoFichero;
import eus.ehu.euskoflix.packModelo.*;
import eus.ehu.euskoflix.packVista.EuskoFlixLoader;
import eus.ehu.euskoflix.packVista.InformacionExtraView;
import eus.ehu.euskoflix.packVista.VentanaCargaDatos;
import eus.ehu.euskoflix.packVista.VentanaLogin;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ControladorVista {
	
    private static ControladorVista mControladorVista;

    /* Controlador */
    private GestionDatos gestionDatos;
    
    /* Vista */
    private EuskoFlixLoader euskoFlixLoader;
    private VentanaCargaDatos ventanaCargaDatos;
    private InformacionExtraView informacionExtraView;
    private VentanaLogin ventanaLogin;
    
    private ControladorVista() {
    	this.ventanaLogin = new VentanaLogin();
    	this.euskoFlixLoader = new EuskoFlixLoader();
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
    	this.gestionDatos.cargarDatos(TipoFichero.test);
    	this.cerrarLoader();
    	this.mostrarCargaDatos();
    }
    
    private void mostrarLoader() {
    	this.euskoFlixLoader.setVisible(true);    	
    }
    
    public void mostrarLogin() {
    	this.ventanaLogin.anadirFocusListener(new VentanaLoginListenerUser(), new VentanaLoginListenerPass());
    	this.ventanaLogin.setVisible(true);
    }
    
    private void cerrarLoader() {
    	this.euskoFlixLoader.setVisible(false);
    	this.euskoFlixLoader.dispose();
    }
    
    private void mostrarCargaDatos() {

    	this.ventanaCargaDatos = new VentanaCargaDatos(datosUsuario(), getCabeceraUsers(), datosPelis(), getCabeceraFilms());
    	
    	/* Listeners VentanaCargaDatos*/
    	this.ventanaCargaDatos.addInfoExtraListener(new InfoExtraListener());
    	
    	this.ventanaCargaDatos.setVisible(true);
    	JOptionPane.showMessageDialog(ventanaCargaDatos,
    			"Doble click en una película para más información", "Info",
    			JOptionPane.INFORMATION_MESSAGE);
    }


    public void cargarDatos() {
        GestionDatos.getInstance().cargarDatos(TipoFichero.test);
    }

    public String[][] datosUsuario() {
        CatalogoUsuarios users = CatalogoUsuarios.getInstance();
        int size = users.getNumUsuarios();
        String[][] resulUsers = new String[size][4];
        Usuario userNew;
        //TODO: usersToArrayString
        for (int i = 0; i < size - 1; i++) {
            userNew = users.getUsuarioPorId(i + 1);
            resulUsers[i][0] = String.valueOf(userNew.getId());
            resulUsers[i][1] = userNew.getNombre();
            resulUsers[i][2] = userNew.getApellido();
            resulUsers[i][3] = userNew.getPassword();
        }
        return resulUsers;
    }

    public String[] getCabeceraUsers() {
        return new String[]{"ID", "Nombre", "Apellido", "Password"};
    }

    //Pestana Tags
    public String[][] datosTags(int id) {
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(id);
        GestionDatos.getInstance().getTags(p);
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
   	 informacionExtraView = new InformacionExtraView(ventanaCargaDatos, true, pId);
        Object[] info = getInfoPelicula(pId);
        informacionExtraView.initComponents(pId, datosTags(pId), getCabeceraTags(), datosRatings(pId), getCabeceraRatings());
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
        Object[] result = new Object[4];
        Pelicula p = Cartelera.getInstance().getPeliculaPorIdSinMapeo(pId);
        result[0] = p.getTitulo();
        result[1] = p.getDirector();
        result[2] = p.getSinopsis();
        result[3] = p.getPoster();
        return result;
    }
    
    /* Implementaciones de los listeners */
    
    class InfoExtraListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) { }

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
		public void mouseReleased(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }
		
		@Override
		public void mouseExited(MouseEvent e) {	}
	}

    class CerrarInfoExtraListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			informacionExtraView.dispose();
			
		}    	
    }
    
    class VentanaLoginListenerUser implements FocusListener{
    	@Override
		public void focusGained(FocusEvent e) {ventanaLogin.getTxtUser().setText("");}

		@Override
		public void focusLost(FocusEvent e) {	}
    }
    
    class VentanaLoginListenerPass implements FocusListener{
    	@Override
		public void focusGained(FocusEvent e) {ventanaLogin.getTxtPass().setText("");}

		@Override
		public void focusLost(FocusEvent e) {	}
    }

}
