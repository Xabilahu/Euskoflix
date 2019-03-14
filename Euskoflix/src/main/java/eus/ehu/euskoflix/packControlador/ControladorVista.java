package eus.ehu.euskoflix.packControlador;

import eus.ehu.euskoflix.packModelo.Cartelera;
import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
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
	public String[][] datosTags(){
		CatalogoUsuarios.getInstance();
		return null;
	}
	public String[][] datosRatings(){
		CatalogoUsuarios.getInstance();
		return null;
	}
	public String[][] datosPelis(){
		Cartelera.getInstance();
		return null;
	}

}
