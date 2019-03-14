package main.java.eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.Usuario;

public class ControladorVista {
	private static ControladorVista mControladorVista;
	
	private ControladorVista(){
	}
	public static ControladorVista getInstance() {
		if(mControladorVista == null) {
			mControladorVista =new ControladorVista();
		}
		return mControladorVista;
	}
	public String[][] datosUsuario(){
		CatalagoUsuarios users = CatalogoUsuarios.getInstance();
		int size = users.getNumUsuarios();
		String[][] resulUsers = new String[size][4];
		Usuario userNew;
		for(int i =1;i<=size;i++) {
			userNew = users.getUsuarioPorId(i);
			resulUsers[i][0] = userNew.getId;
			resulUsers[i][1] = userNew.getNombre;
			resulUsers[i][2] = userNew.getApellido;
			resulUsers[i][3] = userNew.getPassword;
		}
		return resulUsers;
		
		
	}
	public String[] getCabeceraUsers() {
		return new String[] {"ID","Nombre","Apellido","Password"};
	}
	public String[][] datosTags(){
		CatalogoUsuarios.getInstance();
		
	}
	public String[][] datosRatings(){
		CatalogoUsuarios.getInstance();
		
	}
	public String[][] datosPelis(){
		Cartelera.getInstance();
		
	}

}
