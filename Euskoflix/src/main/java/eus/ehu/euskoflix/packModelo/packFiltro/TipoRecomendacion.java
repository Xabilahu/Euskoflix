package eus.ehu.euskoflix.packModelo.packFiltro;

public enum TipoRecomendacion {

    Persona, Pelicula, Contenido, Hibrido;

    public static TipoRecomendacion stringToEnum(String pFiltro) {
        switch (pFiltro) {
            case "Personas":
                return Persona;
            case "Peliculas":
                return Pelicula;
            case "Contenido":
                return Contenido;
            default:
                return Hibrido;
        }
    }

}
