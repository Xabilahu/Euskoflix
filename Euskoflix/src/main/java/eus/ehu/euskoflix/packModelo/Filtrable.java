package eus.ehu.euskoflix.packModelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public abstract class Filtrable {

    public static final int N = 30;
    private HashMap<Integer, TreeSet<Similitud>> matrizSimilitudes;
    private ListaPeliculasRecomendadas recomendados;

    public Filtrable() {
        this.matrizSimilitudes = new HashMap<>();
        this.recomendados = new ListaPeliculasRecomendadas();
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

    public abstract ListaPeliculasRecomendadas recomendar(int pNum);

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

    public ListaPeliculasRecomendadas getNRecomendaciones(int pNum) {
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
}
