package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packModelo.*;

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

	public void cargarDatos(){
		GestionDatos.getInstance().cargarDatos();
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
	public String[][] datosTags(int id){
		return GestionDatos.getInstance().getTagsByPelicula(id);
	}
	public String[] getCabeceraTags() {
		return new String[] {"Tag","Apariciones"};
	}
	//Pestana Ratings
	public String[][] datosRatings(int id){
		return GestionDatos.getInstance().getValoracionesByPelicula(id);
	}
	public String[] getCabeceraRatings() {
		return new String[] {"NombreUsuario","Rating"};
	}

	//Pestana peliculas y su cabecera
	public String[][] datosPelis(){
		Cartelera films = Cartelera.getInstance();
		int size = films.getNumPeliculas();
		String [][] resulFilms = new String[size][2];
		Pelicula filmNew;
		for(int i =0;i<size;i++) {
			filmNew = films.getPeliculaPorId(i);
			resulFilms[i][0] = String.valueOf(filmNew.getId());
			resulFilms[i][1] = filmNew.getTitulo();
		}
		return resulFilms;
	}
	public String[] getCabeceraFilms(){
		return new String[] {"ID","Titulo"};
	}

	public Pelicula getPelicula(int pId) {
		return Cartelera.getInstance().getPeliculaPorIdSinMapeo(pId);
	}

}
