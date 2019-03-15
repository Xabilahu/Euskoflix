package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.Pelicula;
import eus.ehu.euskoflix.packModelo.Usuario;


public class ControladorVista {
	private static ControladorVista mControladorVista;
	
	private ControladorVista(){
	}
	public static ControladorVista getInstance() {
		if(mControladorVista == null) {
			mControladorVista = new ControladorVista();
		}
		return mControladorVista;
	}
	public String[][] datosUsuario(){
		CatalogoUsuarios users = CatalogoUsuarios.getInstance();
		int size = users.getNumUsuarios();
		String[][] resulUsers = new String[size][4];
		Usuario userNew;
		for(int i =0;i<size - 1;i++) {
			userNew = users.getUsuarioPorId(i+1);
			resulUsers[i][0] = String.valueOf(userNew.getId());
			resulUsers[i][1] = userNew.getNombre();
			resulUsers[i][2] = userNew.getApellido();
			resulUsers[i][3] = userNew.getPassword();
		}
		return resulUsers;
	}
	public String[] getCabeceraUsers() {
		return new String[] {"ID","Nombre","Apellido","Password"};
	}
	//Pestana Tags
	public String[][] datosTags(){
		CatalogoUsuarios.getInstance();
		return null;
	}
	public String[] getCabeceraTags() {
		return new String[] {"Tag","Apariciones"};
	}
	//Pestana Ratings
	public String[][] datosRatings(){
		CatalogoUsuarios.getInstance();
		return null;
	}
	public String[] getCabeceraRatings() {
		return new String[] {"IdUsuario","Rating"};
	}

	//Pestana peliculas y su cabecera
	public String[][] datosPelis(){
		Cartelera films = Cartelera.getInstance();
		int size = films.getNumPeliculas();//crear
		String [][] resulFilms = new String[size][2];
		Pelicula filmNew;
		for(int i =0;i<size -1;i++) {
			filmNew = films.getPeliculaPorId(i+1);//crear
			resulFilms[i][0] = String.valueOf(filmNew.getId());//crear
			resulFilms[i][1] = filmNew.getTitulo();//crear
		}
		return null;
	}
	public String[] getCabeceraFilms(){
		return new String[] {"ID","Titulo"};
	}

}
