package eus.ehu.euskoflix.packModelo;

import java.util.HashMap;

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

    }

    public void addUsuario(Usuario pUser) {
        this.lista.put(pUser.getId(), pUser);
    }

    public Usuario login(Usuario pUsuario) {
        Usuario user = null;
        if ((user = this.getUsuarioPorId(pUsuario.getId())) != null && user.comprobarPassword(pUsuario)) {
            this.logged = user;
            Filtrado.getInstance().cargarModelos();
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

    public void print() {
       /* boolean primero = true;
        StringBuilder sb = new StringBuilder("<html><table>");
        sb.append("<th><td>usuario 1</td><td>Usuario 2</td><td>Similitud</td></th>");
        for (Usuario u1 : this.lista) {
            if (u1 != null) {
                for (Usuario u2 : this.lista) {
                    if (u2 != null && u1 != u2) {
                        sb.append("<tr><td>");
                        sb.append(u1.getId());
                        sb.append("</td><td>");
                        sb.append(u2.getId());
                        sb.append("</td><td>");
                        sb.append(MatrizValoraciones.getInstance().simPersonas(u1,u2).getSim());
                        sb.append("</td></tr>");
                    }
                }
            }
        }
        sb.append("</table></html>");
        JOptionPane.showMessageDialog(null,sb.toString());*/
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
}
