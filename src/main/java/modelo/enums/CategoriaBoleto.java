package modelo.enums;

public enum CategoriaBoleto {
    /*Categorias de los boletos con el incremento del precio
        y la cantidad de lugares disponible por vagon*/

    PREMIUM(1.2),
    EJECUTIVA(1.1),
    ESTANDAR(1.0);

    private final double incrementoPrecio;

    CategoriaBoleto(double incrementoPrecio) {
        this.incrementoPrecio = incrementoPrecio;
    }

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }
}