package eus.ehu.euskoflix.packModelo;

import java.util.ArrayList;

public class CatalogoUsuarios {

    private static CatalogoUsuarios mCatalogo;
    private ArrayList<Usuario> lista;

    private CatalogoUsuarios() {
        this.lista = new ArrayList<Usuario>();
    }

    public static CatalogoUsuarios getInstance() {
        if (mCatalogo == null) {
            mCatalogo = new CatalogoUsuarios();
        }
        return mCatalogo;
    }

    public void registrar(Usuario pUsuario){

    }

    public void addUsuario(Usuario pUser){
        this.lista.add(pUser);
    }

    public Usuario login(Usuario pUsuario) {
        return null;
    }

}
