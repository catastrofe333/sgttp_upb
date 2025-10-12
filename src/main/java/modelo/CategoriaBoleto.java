package modelo;

public enum CategoriaBoleto {
    /*Categorias de los boletos con el incremento del precio
        y la cantidad de lugares disponible por vagon*/

    PREMIUM(1.2, 4),
    EJECUTIVA(1.1, 8),
    ESTANDAR(1.0, 22);

    private final double incrementoPrecio;
    private final int lugaresPorVagon;

    CategoriaBoleto(double incrementoPrecio, int lugaresPorVagon) {
        this.incrementoPrecio = incrementoPrecio;
        this.lugaresPorVagon = lugaresPorVagon;
    }

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    public int getLugaresPorVagon() {
        return lugaresPorVagon;
    }
}
