package eus.ehu.euskoflix.packModelo;

import eus.ehu.euskoflix.packModelo.packFiltro.Filtrado;
import eus.ehu.euskoflix.packModelo.packFiltro.FiltradoPersona;

import java.util.HashMap;
import java.util.Iterator;

public class CatalogoUsuarios {

    private static CatalogoUsuarios mCatalogo;
    private HashMap<Integer, Usuario> lista;
    private Usuario logged;

    private CatalogoUsuarios() {
        this.lista = new HashMap<Integer, Usuario>();
    }

    public static CatalogoUsuarios getInstance() {
        if (mCatalogo == null) {
            mCatalogo = new CatalogoUsuarios();
        }
        return mCatalogo;
    }

    public void registrar(Usuario pUsuario) {
        this.lista.put(pUsuario.getId(), pUsuario);
    }

    public void addUsuario(Usuario pUser) {
        this.lista.put(pUser.getId(), pUser);
    }

    public Usuario login(Usuario pUsuario) {
        Usuario user;
        if ((user = this.getUsuarioPorId(pUsuario.getId())) != null && user.comprobarPassword(pUsuario)) {
            this.logged = user;
            Filtrado.getInstance().cargarModelos();
        } else {
            user = null;
        }
        return user;
    }

    public Usuario getUsuarioPorId(int pId) {
        return this.lista.get(pId);
    }

    public int getNumUsuarios() {
        return this.lista.size();
    }

    public void cargarMediasDesviacionesUsuarios() {
        for (Usuario u : this.lista.values()) {
            if (MatrizValoraciones.getInstance().tieneValoracionesUsuario(u.getId())) {
                MatrizValoraciones.getInstance().cargarMediasDesv(u);
            }
        }
    }

    public Usuario getUsuarioLogueado() {
        return this.logged;
    }

    public void cargarModeloPersona(FiltradoPersona filtradoPersona) {
        this.lista.values().stream().forEach(usuario -> {
            if (usuario != null && !usuario.equals(logged)) {
                filtradoPersona.addSimilitud(logged.getId(), MatrizValoraciones.getInstance().simPersonas(logged, usuario));
            }
        });
    }

    public String[][] usuariosToArrayString() {
        String[][] resulUsers = new String[this.lista.size()][4];
        Iterator<Usuario> itr = this.lista.values().iterator();
        for (int i = 0; i < this.lista.size(); i++) {
            Usuario u = itr.next();
            resulUsers[i][0] = String.valueOf(u.getId());
            resulUsers[i][1] = u.getNombre();
            resulUsers[i][2] = u.getApellido();
            resulUsers[i][3] = u.getPassword();
        }
        return resulUsers;
    }
}
