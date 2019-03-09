package eus.ehu.euskoflix.packModelo;

import java.util.Collection;

public class ListaTags {

    private Collection<String> lista;

    public ListaTags() {
        //TODO:inicializar lista cuando se decida la colección a utilizar
    }

    public void addTag(String pTag) {
        this.lista.add(pTag);
    }

}
