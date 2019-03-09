package eus.ehu.euskoflix.packModelo;

import java.util.Collection;

public class CatalogoUsuarios {

    private static CatalogoUsuarios mCatalogo;
    private Collection<Usuario> lista;

    private CatalogoUsuarios() {
        //TODO:inicializar lista cuando se decida la colección a utilizar
    }

    public static CatalogoUsuarios getInstance() {
        if (mCatalogo == null) {
            mCatalogo = new CatalogoUsuarios();
        }
        return mCatalogo;
    }

    public void registrar(Usuario pUsuario){

    }

    public void cargar(){

    }

    public Usuario login(Usuario pUsuario) {
        return null;
    }

}
