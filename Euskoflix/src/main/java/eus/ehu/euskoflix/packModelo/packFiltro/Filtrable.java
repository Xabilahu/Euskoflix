package eus.ehu.euskoflix.packModelo.packFiltro;

import eus.ehu.euskoflix.packModelo.CatalogoUsuarios;
import eus.ehu.euskoflix.packModelo.MatrizValoraciones;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class Filtrable {

    private static final int N = 30;
    private HashMap<Integer, TreeSet<Similitud>> matrizSimilitudes;
    private ListaPeliculasValoraciones recomendados;

    public Filtrable() {
        this.matrizSimilitudes = new HashMap<>();
        this.recomendados = new ListaPeliculasValoraciones();
    }

    /**
     * This method is only used in jUnit
     */
    public static int getN() {
        return N;
    }

    public Similitud[] getNMasSimilares(int pId) {
        Similitud[] similitudArray;
        TreeSet<Similitud> similitudes = this.matrizSimilitudes.get(pId);
        similitudArray = new Similitud[(similitudes.size() < N) ? similitudes.size() : N];
        Iterator itr = similitudes.iterator();
        for (int i = 0; i < similitudArray.length; i++) {
            similitudArray[i] = (Similitud) itr.next();
        }
        return similitudArray;
    }

    public Similitud[] getSimilares(int pId){
        TreeSet<Similitud> similitudTreeSet = this.matrizSimilitudes.get(pId);
        Similitud[]  similitudes = new Similitud[similitudTreeSet.size()];
        Iterator itr = similitudTreeSet.iterator();
        for (int i = 0; i < similitudes.length; i++) {
            similitudes[i] = (Similitud) itr.next();
        }
        return similitudes;
    }

    public abstract ListaPeliculasValoraciones recomendar(int pNum);

    public void addSimilitudSimetrica(Similitud pSimilitud) {
        this.addSimilitud(pSimilitud.getI(), pSimilitud);
        this.addSimilitud(pSimilitud.getJ(), pSimilitud);
    }

    public void addSimilitud(int pID, Similitud pSimilitud) {
        if (!this.matrizSimilitudes.containsKey(pID)) {
            this.matrizSimilitudes.put(pID, new TreeSet<Similitud>((o1, o2) -> Double.compare(o1.getSim(), o2.getSim()) * -1) {{
                add(pSimilitud);
            }});
        } else {
            this.matrizSimilitudes.get(pID).add(pSimilitud);
        }
    }

    public void addRecomendacion(int pPelicula, double pValoracion) {
        this.recomendados.add(pPelicula, pValoracion);
    }

    public abstract void calcularRecomendaciones();

    public ListaPeliculasValoraciones getNRecomendaciones(int pNum) {
        return this.recomendados.getNRecomendaciones(pNum);
    }

    public void generarValoracionRecomendada(boolean filtradoPersona, Integer noValorada, Similitud[] similitudes) {
        double numerador = 0.0, denominador = 0.0;
        double x;
        for (Similitud similitud : similitudes) {
            try {
                if (filtradoPersona) {
                    x = MatrizValoraciones.getInstance().getValoracion(similitud.getJ(), noValorada);
                } else {
                    x = MatrizValoraciones.getInstance().getValoracion(CatalogoUsuarios.getInstance().getUsuarioLogueado().getId(), similitud.getJ());
                }
                numerador += Math.abs(x * similitud.getSim());
                denominador += Math.abs(similitud.getSim());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        this.addRecomendacion(noValorada,
//                CatalogoUsuarios.getInstance().getUsuarioLogueado().desnormalizar(numerador/denominador)
                numerador / denominador
        );
    }

    /**
     * This method is only used in jUnit
     */
    public HashMap<Integer, TreeSet<Similitud>> getMatrizSimilitudes() {
        return matrizSimilitudes;
    }

    /**
     * This method is only used in jUnit
     */
    public ListaPeliculasValoraciones getRecomendados() {
        return recomendados;
    }

    public void vaciarEstructuras() {
        this.recomendados = new ListaPeliculasValoraciones();
        this.matrizSimilitudes = new HashMap<>();
    }
}
